package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListEventAdapter
import com.aslyon.lpiem.aslyon1.adapter.ListSubscribersEventAdapter
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.viewModel.DetailsEventViewModel
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.activity_subscribers_event.*
import kotlinx.android.synthetic.main.fragment_event.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import timber.log.Timber

class ListSubscribersEventActvity : BaseActivity() {

    private lateinit var viewModel: DetailsEventViewModel

    companion object {
        const val ExtraEventId = "ExtraEventId"
        fun start(activity: AppCompatActivity, eventId: Int) = activity.startActivity(Intent(activity, ListSubscribersEventActvity::class.java).apply {
            putExtra(ExtraEventId, eventId)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribers_event)
        setSupportActionBar(toolbarSubscribersEvent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getIntExtra(DetailsEventActivity.ExtraEventId, -1)
        viewModel = kodein.direct.instance(arg = M(this, eventId))

        val adapter = ListSubscribersEventAdapter()
        val mLayoutManager = LinearLayoutManager(this)
        rv_activity_subscribers_event.setLayoutManager(mLayoutManager)
        rv_activity_subscribers_event.setItemAnimator(DefaultItemAnimator())
        rv_activity_subscribers_event.adapter = adapter

        viewModel.listSubscribersEvent.subscribe(
                {
                    if (it.isNullOrEmpty()) {
                        rv_activity_subscribers_event.visibility = View.GONE
                        tv_no_subscribers_event.visibility = View.VISIBLE
                    } else {
                        adapter.submitList(it)
                        rv_activity_subscribers_event.visibility = View.VISIBLE
                        tv_no_subscribers_event.visibility = View.GONE
                    }
                },
                { Timber.e(it) }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListSubscribersEvent()
    }

}