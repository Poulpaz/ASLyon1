package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.viewModel.DetailsEventViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_event_details.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailsEventActivity : BaseActivity() {

    private lateinit var viewModel: DetailsEventViewModel

    companion object {
        const val ExtraEventId = "ExtraEventId"
        fun start(activity: AppCompatActivity, eventId: Int) = activity.startActivity(Intent(activity, DetailsEventActivity::class.java).apply {
            putExtra(ExtraEventId, eventId)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val eventId = intent.getIntExtra(ExtraEventId, -1)
        viewModel = kodein.direct.instance(arg = M(this, eventId))

        viewModel.event
                .subscribe(
                        {
                            displayEvent(it)
                        },
                        { Timber.e(it)}
                )
    }

    private fun displayEvent(event: Event) {
        tv_title_event_details.text = event.title
        tv_place_event_details.text = event.place
        tv_price_event_details.text = event.price

        //Display Chip
        chipGroup_date_fragment_event_details.addView(getChip(getDateToString(event.date)))

        tv_description_event_details.text = event.description
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