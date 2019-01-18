package com.aslyon.lpiem.aslyon1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Offer

import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_offer.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ListOfferAdapter : ListAdapter<Offer, ListOfferAdapter.OfferViewHolder>(DiffCardCallback()) {

    val indexClickPublisher: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view, indexClickPublisher)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class OfferViewHolder(itemView: View, private val indexClickPublisher: PublishSubject<Int>) : RecyclerView.ViewHolder(itemView) {


        fun bind(offer: Offer) {
            itemView.tv_title_item_offer.text = offer.title
            itemView.tv_date_item_offer.text = "Du "+ getDateToString(offer.startDate)+" au " +getDateToString(offer.endDate)
            itemView.tv_number_team_item_offer.text=offer.nbParticipants+" places disponible(s)"
            itemView.tv_price_item_offer.text = offer.price+" €"
            bindPositionClick(offer.idOffer)
        }

        private fun bindPositionClick(idOffer: Int) {
            itemView.setOnClickListener {
                indexClickPublisher.onNext(idOffer)
            }
        }
        private fun getDateToString(date: Date?): String {
            val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
            return df.format(date)
        }
    }


    class DiffCardCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.idOffer == newItem.idOffer
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.idOffer == newItem.idOffer
        }

    }







}