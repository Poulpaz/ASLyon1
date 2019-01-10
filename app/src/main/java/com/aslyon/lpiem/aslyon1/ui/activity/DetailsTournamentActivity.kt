package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Tournament
import com.aslyon.lpiem.aslyon1.viewModel.DetailsTournamentViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.activity_tournament_details.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailsTournamentActivity : BaseActivity() {
    private lateinit var viewModel: DetailsTournamentViewModel

    companion object {
        const val ExtraTournamentId = "ExtraTournamentId"
        fun start(activity: AppCompatActivity, tournamentId: Int) = activity.startActivity(Intent(activity, DetailsTournamentActivity::class.java).apply {
            putExtra(ExtraTournamentId, tournamentId)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tournament_details)

        val tournamentId = intent.getIntExtra(DetailsTournamentActivity.ExtraTournamentId, -1)
        viewModel = kodein.direct.instance(arg = M(this, tournamentId))

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
        tv_place_tournament_details.text = tournament.place
        tv_price_tournament_details.text = tournament.price
        tv_team_tournament_details.text = tournament.nbTeam.toString() + " équipes de " + tournament.nbPlayersTeam.toString() + " joueurs"

        //Display Chip
        chipGroup_date_fragment_tournament_details.addView(getChip(getDateToString(tournament.date)))

        tv_description_tournament_details.text = tournament.description
    }

    private fun getChip(textChip : String?): Chip {
        val chip = Chip(this)
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