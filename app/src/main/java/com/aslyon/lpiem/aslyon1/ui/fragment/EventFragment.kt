package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.R.attr.action
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListEventAdapter
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsEventActivity
import com.aslyon.lpiem.aslyon1.ui.activity.MainActivity
import com.aslyon.lpiem.aslyon1.viewModel.EventViewModel
import kotlinx.android.synthetic.main.fragment_event.*
import org.kodein.di.generic.instance
import timber.log.Timber

class EventFragment : BaseFragment() {
    companion object {
        const val TAG = "EVENTFRAGMENT"
        fun newInstance(): EventFragment = EventFragment()
    }

    private val viewModel: EventViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListEventAdapter()
        val mLayoutManager = LinearLayoutManager(context)
        rv_event_fragment.setLayoutManager(mLayoutManager)
        rv_event_fragment.setItemAnimator(DefaultItemAnimator())
        rv_event_fragment.adapter = adapter

        viewModel.eventList
                .subscribe(
                    {
                        adapter.submitList(it)
                    },
                    { Timber.e(it) }
                )

        adapter.indexClickPublisher
                .subscribe(
                        {
                            DetailsEventActivity.start(activity as MainActivity, it)
                        },
                        { Timber.e(it) }
                )
    }
}