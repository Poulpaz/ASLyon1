package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.viewModel.DetailsOfferViewModel
import com.google.android.material.chip.Chip

import kotlinx.android.synthetic.main.activity_offer_details.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailsOfferActivity : BaseActivity() {

    private lateinit var viewModel: DetailsOfferViewModel

    companion object {
        const val ExtraOfferId = "ExtraOfferId"
        fun start(activity: AppCompatActivity, offerId: Int) = activity.startActivity(Intent(activity, DetailsOfferActivity::class.java).apply {
            putExtra(ExtraOfferId, offerId)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_details)
        setSupportActionBar(toolbarDetailsoffer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val offerId = intent.getIntExtra(DetailsOfferActivity.ExtraOfferId, -1)
        viewModel = kodein.direct.instance(arg = M(this, offerId))

        viewModel.offer
                .subscribe(
                        {
                            displayOffer(it)
                        },
                        { Timber.e(it)}
                )
    }

    private fun displayOffer(offer: Offer) {
        tv_title_offer_details.text = offer.title
        tv_place_offer_details.text = offer.teams
        tv_price_offer_details.text = offer.price
        tv_date_offer_details.text = getDateToString(offer.date)
        tv_description_offer_details.text = offer.description


        b_link_fragment_offer_details.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
            startActivity(i)
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