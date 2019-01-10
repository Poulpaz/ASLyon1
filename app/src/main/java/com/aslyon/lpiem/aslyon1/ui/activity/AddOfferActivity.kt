package com.aslyon.lpiem.aslyon1.ui.activity

import android.app.DatePickerDialog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R

import kotlinx.android.synthetic.main.activity_add_offer.*

import java.text.SimpleDateFormat
import java.util.*



class AddOfferActivity : BaseActivity(){
    var formate = SimpleDateFormat("dd/MM/yyyy",Locale.US)

    var offerDate: Date? = null
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


