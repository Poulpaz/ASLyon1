package com.aslyon.lpiem.aslyon1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Tournament
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_tournament.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ListTournamentAdapter : ListAdapter<Tournament, ListTournamentAdapter.TournamentViewHolder>(DiffCardCallback()) {

    val indexClickPublisher: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament, parent, false)
        return TournamentViewHolder(view, indexClickPublisher)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TournamentViewHolder(itemView: View, private val indexClickPublisher: PublishSubject<Int>) : RecyclerView.ViewHolder(itemView) {

        fun bind(tournament: Tournament) {
            itemView.tv_title_item_tournament.text = tournament.title
            itemView.tv_date_item_tournament.text = getDateToString(tournament.date)
            itemView.tv_number_team_item_tournament.text = tournament.nbTeam.toString() + " équipe(s) de " + tournament.nbPlayersTeam.toString() + " joueur(s)"
            itemView.tv_price_item_tournament.text = tournament.price+" euro(s)"
            bindPositionClick(tournament.idTournament)
        }

        private fun bindPositionClick(idTournament: Int) {
            itemView.clicks()
                    .takeUntil(RxView.detaches(itemView))
                    .filter { adapterPosition != RecyclerView.NO_POSITION }
                    .subscribe {
                        indexClickPublisher.onNext(idTournament)
                    }
        }
    }

    private fun getDateToString(date: Date?): String {
        val df: DateFormat = SimpleDateFormat("dd/MM/yyyy' à 'HH:mm", Locale.FRANCE)
        return df.format(date)
    }

    class DiffCardCallback : DiffUtil.ItemCallback<Tournament>() {
        override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
            return oldItem.idTournament == newItem.idTournament
        }

        override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
            return oldItem.idTournament == newItem.idTournament
        }
    }

}