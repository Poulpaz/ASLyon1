package com.aslyon.lpiem.aslyon1.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent

import com.aslyon.lpiem.aslyon1.viewModel.AddTournamentViewModel
import kotlinx.android.synthetic.main.activity_add_tournament.*
import kotlinx.android.synthetic.main.activity_add_tournament.*
import kotlinx.android.synthetic.main.activity_event_details.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddTournamentActivity: BaseActivity() {

    private val viewModel: AddTournamentViewModel by instance(arg = this)
    private var addTournamentButtonMenu : MenuItem? = null
    var formate = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    var timeFormat = SimpleDateFormat("HH:mm ", Locale.FRANCE)

    lateinit var tournamentDate: Date
    lateinit var tournamentHour: Date

    companion object {
        fun start(fromActivity: AppCompatActivity) {
            fromActivity.startActivity(Intent(fromActivity, AddTournamentActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tournament)
        setSupportActionBar(toolbar_add_tournament)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initChipDatePicker()
        initChipHourPicker()
        setSupportActionBar(toolbar_add_tournament)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_validate_new_item -> {

                val title = et_title_activity_add_tournament.text.toString()
                val nbTeam = et_nb_team_activity_add_tournament.text.toString()
                val nbPlayersTeams = et_nb_players_team_activity_add_tournament.text.toString()
                val place = et_place_activity_add_tournament.text.toString()
                val price = et_price_activity_add_tournament.text.toString()
                val description = et_description_activity_add_tournament.text.toString()

                viewModel.addtournament(title, nbTeam, nbPlayersTeams, tournamentDate,tournamentHour, place, description, price)
                viewModel.registerState.subscribe(
                        {
                            when (it) {
                                NetworkEvent.None -> {
                                    // Nothing
                                }
                                NetworkEvent.InProgress -> {
                                    onTournamentStateInProgress()

                                }
                                is NetworkEvent.Error -> {
                                    onTournamentStateError(it)
                                }
                                is NetworkEvent.Success -> {



                                    onTournamentStateSuccess()
                                }
                            }
                        }, { Timber.e(it) }
                )

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onTournamentStateSuccess() {
        Toast.makeText(this@AddTournamentActivity, getString(R.string.tv_add_tournament_success), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun onTournamentStateError(it: NetworkEvent.Error) {
        Toast.makeText(this@AddTournamentActivity, getString(R.string.tv_add_tournament_error), Toast.LENGTH_SHORT).show()
    }

    private fun onTournamentStateInProgress() {
        progress_bar_add_tournament.visibility = View.VISIBLE
        addTournamentButtonMenu?.isVisible = false



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_new_item, menu)
        addTournamentButtonMenu = menu?.findItem(R.id.button_validate_new_item)

        return super.onCreateOptionsMenu(menu)
    }
    val now = Calendar.getInstance()

    private fun initChipDatePicker() {
        chip_activity_add_tournament.setOnClickListener {

            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val date = formate.format(selectedDate.time)
                tournamentDate = selectedDate.time
                chip_activity_add_tournament.text = date
            },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()


        }
    }
    private fun initChipHourPicker() {
        chip_hour_tournament_activity_add_tournament.setOnClickListener {


            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                tournamentHour = selectedTime.time
                tournamentHour.toString()
                chip_hour_tournament_activity_add_tournament.text = timeFormat.format(selectedTime.time)
            },

                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)

            timePicker.show()

        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true


    }
}