package com.aslyon.lpiem.aslyon1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.response.SubscribersEventResponse
import com.aslyon.lpiem.aslyon1.model.Offer
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_offer.view.*
import kotlinx.android.synthetic.main.item_subscriber_event.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ListSubscribersEventAdapter : ListAdapter<SubscribersEventResponse, ListSubscribersEventAdapter.SubscribersEventViewHolder>(DiffCardCallback()) {

    val indexClickPublisher: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribersEventViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subscriber_event, parent, false)
        return SubscribersEventViewHolder(view, indexClickPublisher)
    }

    override fun onBindViewHolder(holder: SubscribersEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class SubscribersEventViewHolder(itemView: View, private val indexClickPublisher: PublishSubject<Int>) : RecyclerView.ViewHolder(itemView) {


        fun bind(subscriber: SubscribersEventResponse) {
            itemView.tv_firstname_item_subscriber_event.text = subscriber.firstname
            itemView.tv_lastname_item_subscriber_event.text = subscriber.lastname
            bindPositionClick(subscriber.idUser)
        }

        private fun bindPositionClick(idSubscriber: Int) {
            itemView.clicks()
                    .takeUntil(RxView.detaches(itemView))
                    .filter { adapterPosition != RecyclerView.NO_POSITION }
                    .subscribe { indexClickPublisher.onNext(idSubscriber) }
        }
    }


    class DiffCardCallback : DiffUtil.ItemCallback<SubscribersEventResponse>() {
        override fun areItemsTheSame(oldItem: SubscribersEventResponse, newItem: SubscribersEventResponse): Boolean {
            return oldItem.idUser == newItem.idUser
        }

        override fun areContentsTheSame(oldItem: SubscribersEventResponse, newItem: SubscribersEventResponse): Boolean {
            return oldItem.idUser == newItem.idUser
        }

    }
}