package com.aslyon.lpiem.aslyon1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.ItemsItem
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_actu.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ListActuAdapter : ListAdapter<ItemsItem, ListActuAdapter.ActuViewHolder>(DiffCardCallback()) {

    val indexClickPublisher: PublishSubject<String> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListActuAdapter.ActuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actu, parent, false)
        return ActuViewHolder(view, indexClickPublisher)
    }

    override fun onBindViewHolder(holder: ListActuAdapter.ActuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ActuViewHolder(itemView: View, private val indexClickPublisher: PublishSubject<String>) : RecyclerView.ViewHolder(itemView) {

        fun bind(itemsItem: ItemsItem) {
            itemView.tv_title_item_actu.text = itemsItem.title
            itemView.tv_pubDate_item_actu.text = itemsItem.pubDate
            itemView.tv_author_item_actu.text = itemsItem.author
            bindPositionClick(itemsItem.link)
        }

        private fun bindPositionClick(guid: String) {
            itemView.clicks()
                    .takeUntil(RxView.detaches(itemView))
                    .filter { adapterPosition != RecyclerView.NO_POSITION }
                    .subscribe { indexClickPublisher.onNext(guid) }
        }
    }

    private fun getStringToDate(date : String): String {
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
        val date = sdf.parse(date)

        val sdf2 = SimpleDateFormat("dd/MM/yyyy à HH:mm")
        return sdf2.format(date)
    }

    class DiffCardCallback : DiffUtil.ItemCallback<ItemsItem>() {
        override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem.link == newItem.link
        }
    }
}