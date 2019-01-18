package com.aslyon.lpiem.aslyon1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Event
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ListEventAdapter : ListAdapter<Event, ListEventAdapter.EventViewHolder>(DiffCardCallback()) {

    val indexClickPublisher: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view, indexClickPublisher)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(itemView: View, private val indexClickPublisher: PublishSubject<Int>) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: Event) {
            itemView.tv_title_item_event.text = event.title
            itemView.tv_date_item_event.text = getDateToString(event.date)
            itemView.tv_place_item_event.text = event.place
            itemView.tv_price_item_event.text = event.price+" €"
            bindPositionClick(event.idEvent)
        }

        private fun bindPositionClick(idEvent: Int) {
            itemView.clicks()
                    .takeUntil(RxView.detaches(itemView))
                    .filter { adapterPosition != RecyclerView.NO_POSITION }
                    .subscribe { indexClickPublisher.onNext(idEvent) }
        }
        private fun getDateToString(date: Date?): String {
            val df: DateFormat = SimpleDateFormat("dd/MM/yyyy' à 'HH:mm", Locale.FRANCE)
            return df.format(date)
        }

    }
    class DiffCardCallback : DiffUtil.ItemCallback<Event>() {

        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.idEvent == newItem.idEvent
        }
        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.idEvent == newItem.idEvent
        }

    }
}