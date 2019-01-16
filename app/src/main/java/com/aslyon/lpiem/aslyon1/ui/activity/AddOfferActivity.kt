package com.aslyon.lpiem.aslyon1.ui.activity

import android.app.DatePickerDialog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.ui.fragment.OfferFragment
import com.aslyon.lpiem.aslyon1.viewModel.AddOfferViewModel


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
    var formate = SimpleDateFormat("dd/MM/yyyy",Locale.US)

   lateinit var offerDate: Date
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
        setSupportActionBar(toolbar)
        setDisplayHomeAsUpEnabled(this,true)





    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.button_validate_new_item -> {
                val title=et_title_activity_add_offer.text.toString()

                val description=et_description_activity_add_offer.text.toString()
                val nbParticipants =et_participants_activity_add_offer.text.toString()

                val reduction=et_discount_price_activity_add_offer.text.toString()

                viewModel.addoffer(title, offerDate,nbParticipants,reduction, description)

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



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    }


