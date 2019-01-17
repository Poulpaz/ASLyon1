package com.aslyon.lpiem.aslyon1.ui.activity

import android.app.DatePickerDialog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.ui.fragment.OfferFragment
import com.aslyon.lpiem.aslyon1.viewModel.AddOfferViewModel
import kotlinx.android.synthetic.main.activity_add_event.*


import kotlinx.android.synthetic.main.activity_add_offer.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*



class AddOfferActivity : BaseActivity(){

    private val viewModel: AddOfferViewModel by instance(arg = this)
    var formate = SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE)
    private var addOfferButtonMenu : MenuItem? = null

     var offerStartDate : Date?=null
     var offerEndDate    : Date?=null

    companion object {
        fun start(fromActivity: AppCompatActivity) {
            fromActivity.startActivity(Intent(fromActivity, AddOfferActivity::class.java))

        }
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_offer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initChipDatePicker()
        initChipDateEndPicker()
        setSupportActionBar(toolbar)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.button_validate_new_item -> {
                val title=et_title_activity_add_offer.text.toString()

                val description=et_description_activity_add_offer.text.toString()
                val nbParticipants =et_participants_activity_add_offer.text.toString()

                val reduction=et_discount_price_activity_add_offer.text.toString()

                viewModel.addoffer(title,offerStartDate,offerEndDate,nbParticipants,reduction, description)

                viewModel.registerState.subscribe(
                        {
                            when (it) {
                                NetworkEvent.None -> {
                                    // Nothing
                                }
                                NetworkEvent.InProgress -> {
                                    onOfferStateInProgress()

                                }
                                is NetworkEvent.Error -> {
                                    onOfferStateError(it)
                                }
                                is NetworkEvent.Success -> {



                                    onOfferStateSuccess()
                                }
                            }
                        }, { Timber.e(it) }
                )

                viewModel.errorEditTextAddOffer.subscribe(
                        {
                            Toast.makeText(this@AddOfferActivity, getString(it), Toast.LENGTH_SHORT).show()
                        }, { Timber.e(it) }
                )

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onOfferStateSuccess() {
        Toast.makeText(this@AddOfferActivity, getString(R.string.tv_add_offer_success), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun onOfferStateError(it: NetworkEvent.Error) {
        Toast.makeText(this@AddOfferActivity, getString(R.string.tv_add_offer_error), Toast.LENGTH_SHORT).show()
    }

    private fun onOfferStateInProgress() {
        progress_bar_add_offer.visibility = View.VISIBLE
        addOfferButtonMenu?.isVisible = false

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_add_new_item, menu)
        addOfferButtonMenu = menu?.findItem(R.id.button_validate_new_item)

        return super.onCreateOptionsMenu(menu)
    }

    private fun initChipDatePicker() {
        chip_date_offer_activity_add_offer.setOnClickListener {

            val now = Calendar.getInstance()
         val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
             val selectedDate = Calendar.getInstance()
             selectedDate.set(Calendar.YEAR,year)
             selectedDate.set(Calendar.MONTH,month)
             selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

             val date = formate.format(selectedDate.time)
             offerStartDate = selectedDate.time
             chip_date_offer_activity_add_offer.text = date
         },
                 now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
         datePicker.show()




        }
    }

    private fun initChipDateEndPicker() {
        chip_date_end_offer_activity_add_offer.setOnClickListener {

            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                val date = formate.format(selectedDate.time)
                offerEndDate = selectedDate.time
                chip_date_end_offer_activity_add_offer.text = date
            },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()




        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    }


