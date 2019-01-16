package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
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

        viewModel.isSubscribeEvent
                .subscribe(
                        {
                            if(it == 0){
                                b_subscribe_fragment_event_details.text = getString(R.string.b_subscribe_event)
                                b_subscribe_fragment_event_details.isEnabled = true
                            }
                            else if(it == 1){
                                b_subscribe_fragment_event_details.text = getString(R.string.b_logout_event)
                                b_subscribe_fragment_event_details.isEnabled = true
                            }
                            else{
                                b_subscribe_fragment_event_details.isEnabled = false
                            }
                        },
                        { Timber.e(it)}
                )

        viewModel.event
                .subscribe(
                        {
                            displayEvent(it)
                        },
                        { Timber.e(it)}
                )

        viewModel.subscribeEventState.subscribe(
                {
                    when (it) {
                        NetworkEvent.None -> {
                            // Nothing
                        }
                        NetworkEvent.InProgress -> {
                            onSubscribeEventStateInProgress()
                        }
                        is NetworkEvent.Error -> {
                            onSubscribeEventStateError(it)
                        }
                        is NetworkEvent.Success -> {
                            onSubscribeEventStateSuccess()
                        }
                    }
                }, { Timber.e(it) }
        )

        b_subscribe_fragment_event_details.setOnClickListener {
            if(viewModel.isSubscribeEvent.value == 0){
                subscribeEvent()
            }
            else if(viewModel.isSubscribeEvent.value == 1){
                unsubscribeEvent()
            }
        }
    }

    private fun onSubscribeEventStateSuccess() {
        b_subscribe_fragment_event_details.isEnabled = true
        viewModel.isSubscribeEvent()
    }

    private fun onSubscribeEventStateError(error: NetworkEvent.Error) {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        b_subscribe_fragment_event_details.isEnabled = true
    }

    private fun onSubscribeEventStateInProgress() {
        b_subscribe_fragment_event_details.isEnabled = false
    }

    private fun unsubscribeEvent() {
        viewModel.unsubscribeEvent()
    }

    private fun subscribeEvent() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(R.string.tv_dialog_susbscribe_title_event)
                .setMessage(R.string.tv_dialog_susbscribe_message_event)
                .setNegativeButton(R.string.tv_dialog_susbscribe_cancel, { dialoginterface, i -> })
                .setPositiveButton(R.string.tv_dialog_subscribe_validate) { dialoginterface, i ->
                    viewModel.subscribeEvent()
                }.show()
    }

    private fun displayEvent(event: Event) {
        tv_title_actu_details.text = event.title
        tv_author_actu_details.text = event.place
        tv_price_event_details.text = event.price+" €"
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