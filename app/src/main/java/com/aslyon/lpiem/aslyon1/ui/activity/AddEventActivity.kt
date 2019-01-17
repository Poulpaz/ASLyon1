package com.aslyon.lpiem.aslyon1.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.viewModel.AddEventViewModel
import kotlinx.android.synthetic.main.activity_add_event.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*




class AddEventActivity : BaseActivity(){

    private val viewModel : AddEventViewModel by instance(arg=this)
    var formate = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    var timeFormat = SimpleDateFormat("HH:mm ", Locale.FRANCE)

    lateinit var eventDate: Date
    lateinit var eventHour: Date


    companion object {
        fun start(fromActivity: AppCompatActivity) {
            fromActivity.startActivity(Intent(fromActivity, AddEventActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initChipDatePicker()
        initChipHourPicker()
        setSupportActionBar(toolbarAddEvent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.button_validate_new_item -> {

                val title=et_title_activity_add_event.text.toString()
                val place =et_place_activity_add_event.text.toString()
                val price =et_discount_price_activity_add_event.text.toString()
                val description=et_description_activity_add_event.text.toString()

                viewModel.addevent(title, eventDate,eventHour ,place,price, description)

                viewModel.saveEventState.subscribe(
                        {
                            when (it) {
                                NetworkEvent.None -> {
                                    // Nothing
                                }
                                NetworkEvent.InProgress -> {
                                    //onSignUpStateInProgress()

                                }
                                is NetworkEvent.Error -> {
                                    Toast.makeText(this@AddEventActivity, "Erreur!", Toast.LENGTH_SHORT).show()
                                    //   onSignUpStateError(it)
                                }
                                is NetworkEvent.Success -> {
                                    Toast.makeText(this@AddEventActivity, "Votre évenement à été ajouté !", Toast.LENGTH_SHORT).show()
                                    finish()


                                    //   onSignUpStateSuccess()
                                }
                            }
                        }, { Timber.e(it) }
                )

            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_new_item, menu)
        return super.onCreateOptionsMenu(menu)
    }
    val now = Calendar.getInstance()

    private fun initChipDatePicker() {
        chip_date_event_activity_add_event.setOnClickListener {


            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val date = formate.format(selectedDate.time)
                eventDate = selectedDate.time
                chip_date_event_activity_add_event.text = date
            },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()


        }
    }

        private fun initChipHourPicker() {
            chip_hour_event_activity_add_event.setOnClickListener {


                val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { time, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    eventHour=selectedTime.time
                    eventHour.toString()
                    chip_hour_event_activity_add_event.text = timeFormat.format(selectedTime.time)
                },

                        now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)

                timePicker.show()




            }
    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}