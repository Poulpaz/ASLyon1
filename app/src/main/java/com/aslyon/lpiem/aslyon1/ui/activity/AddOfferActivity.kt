package com.aslyon.lpiem.aslyon1.ui.activity

import android.app.DatePickerDialog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.viewModel.AddOfferViewModel


import kotlinx.android.synthetic.main.activity_add_offer.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat

import java.text.SimpleDateFormat
import java.util.*



class AddOfferActivity : BaseActivity(){

    private val viewModel: AddOfferViewModel by instance(arg = this)
    var formate = SimpleDateFormat("dd/MM/yyyy",Locale.US)

    var offerDate: Date? = null
    companion object {
        const val ExtraOfferId = "ExtraOfferId"
        fun start(fromActivity: AppCompatActivity) {
            fromActivity.startActivity(Intent(fromActivity, AddOfferActivity::class.java))

        }
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_offer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

      //  val offerId = intent.getIntExtra(AddOfferActivity.ExtraOfferId, -1)
        initChipDatePicker()
        setSupportActionBar(toolbar)
        setDisplayHomeAsUpEnabled(this,true)





    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.button_validate_new_item -> {
                val title=et_title_activity_add_offer.text.toString()
                val reduction=et_discount_price_activity_add_offer.text.toString()+" "+
                              et_discount_percentage_activity_add_offer.text.toString()
                val description=et_description_activity_add_offer.text.toString()
                val teams =et_participants_activity_add_offer.text.toString()
                val link=et_link_activity_add_offer.text.toString()
                viewModel.addoffer(title, getDateToString(offerDate),teams,reduction,link, description)

                viewModel.registerState.subscribe(
                        {
                            when (it) {
                                NetworkEvent.None -> {
                                    // Nothing
                                }
                                NetworkEvent.InProgress -> {
                                    //onSignUpStateInProgress()
                                    Toast.makeText(this@AddOfferActivity, "Its toast!", Toast.LENGTH_SHORT).show()
                                }
                                is NetworkEvent.Error -> {
                                    Toast.makeText(this@AddOfferActivity, "Erreur!", Toast.LENGTH_SHORT).show()
                                 //   onSignUpStateError(it)
                                }
                                is NetworkEvent.Success -> {
                                    Toast.makeText(this@AddOfferActivity, "Fonctionne!", Toast.LENGTH_SHORT).show()
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

    private fun initChipDatePicker() {
        chip_date_offer_activity_add_offer.setOnClickListener {

            val now = Calendar.getInstance()
         val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
             val selectedDate = Calendar.getInstance()
             selectedDate.set(Calendar.YEAR,year)
             selectedDate.set(Calendar.MONTH,month)
             selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

             val date = formate.format(selectedDate.time)
             offerDate = selectedDate.time
             chip_date_offer_activity_add_offer.text = date
         },
                 now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
         datePicker.show()




        }
    }
    private fun getDateToString(date: Date?): String {
        val df: DateFormat = SimpleDateFormat("dd/MM/yyyy' à 'HH:mm", Locale.FRANCE)
        return df.format(date)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    }


