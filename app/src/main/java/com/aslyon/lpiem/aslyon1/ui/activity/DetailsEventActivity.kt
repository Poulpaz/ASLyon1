package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.ui.fragment.ProfileFragmentInterface
import com.aslyon.lpiem.aslyon1.viewModel.DetailsEventViewModel
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
        setSupportActionBar(toolbarDetailsEvent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getIntExtra(ExtraEventId, -1)
        viewModel = kodein.direct.instance(arg = M(this, eventId))

        viewModel.event
                .subscribe(
                        {
                            displayEvent(it)
                        },
                        { Timber.e(it)}
                )

        b_subscribe_fragment_event_details.setOnClickListener {
            subscribeEvent()
        }
    }

    private fun subscribeEvent() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(R.string.tv_title_dialog_logout)
                .setMessage(R.string.tv_message_dialog_logout)
                .setNegativeButton(R.string.b_cancel_dialog_logout, { dialoginterface, i -> })
                .setPositiveButton(R.string.b_validate_dialog_logout) { dialoginterface, i ->

                }.show()
    }

    private fun displayEvent(event: Event) {
        tv_title_actu_details.text = event.title
        tv_author_actu_details.text = event.place
        tv_price_event_details.text = event.price
        tv_date_actu_details.text = getDateToString(event.date)

        tv_content_actu_details.text = event.description
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