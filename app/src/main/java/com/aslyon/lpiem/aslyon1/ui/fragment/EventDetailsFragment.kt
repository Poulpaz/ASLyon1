package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.viewModel.EventDetailsViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_event_details.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsFragment : BaseFragment() {

    private lateinit var viewModel: EventDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisplayHomeAsUpEnabled(true)
        setDisplayBotomBarNavigation(false)
        arguments?.let {
            val idEvent = EventDetailsFragmentArgs.fromBundle(it).event
            viewModel = kodein.direct.instance(arg = M(this, idEvent))
        }

        viewModel.event
                .subscribe(
                        {
                            displayEvent(it)
                        },
                        {Timber.e(it)}
                )
    }

    private fun displayEvent(event: Event) {
        tv_title_event_details.text = event.title
        tv_place_event_details.text = event.place
        tv_price_event_details.text = event.price

        //Display Chip
        chipGroup_date_fragment_event_details.addView(getChip(getDateToString(event.date)))
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