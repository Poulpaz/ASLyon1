package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Tournament
import com.aslyon.lpiem.aslyon1.viewModel.TournamentDetailsViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_event_details.*
import kotlinx.android.synthetic.main.fragment_tournament_details.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TournamentDetailsFragment : BaseFragment() {

    private lateinit var viewModel: TournamentDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tournament_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisplayHomeAsUpEnabled(true)
        setDisplayBotomBarNavigation(false)
        arguments?.let {
            val idTournament = TournamentDetailsFragmentArgs.fromBundle(it).tournament
            viewModel = kodein.direct.instance(arg = M(this, idTournament))
        }

        viewModel.tournament
                .subscribe(
                        {
                            displayTournament(it)
                        },
                        { Timber.e(it)}
                )
    }

    private fun displayTournament(tournament: Tournament) {
        tv_title_tournament_details.text = tournament.title
        //tv_place_tournament_details.text = tournament.place
        tv_price_tournament_details.text = tournament.price.toString()

        //Display Chip
        chipGroup_date_fragment_event_details.addView(getChip(getDateToString(tournament.date)))
    }

    private fun getChip(textChip : String?): Chip {
        val chip = Chip(context)
        chip.chipBackgroundColor = ContextCompat.getColorStateList(chip.context, R.color.colorAccent)
        chip.isClickable = false
        chip.text = textChip
        chip.setTextAppearance(R.style.ChipTextStyle)
        return chip
    }

    private fun getDateToString(date: Date?): String {
        val df: DateFormat = SimpleDateFormat("dd/MM/yyyy' à 'HH:mm", Locale.FRANCE)
        return df.format(date)
    }
}