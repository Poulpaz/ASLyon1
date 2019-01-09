package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListEventAdapter
import com.aslyon.lpiem.aslyon1.viewModel.EventViewModel
import kotlinx.android.synthetic.main.fragment_event.*
import org.kodein.di.generic.instance
import timber.log.Timber

class ActuFragment : BaseFragment() {
    companion object {
        const val TAG = "ACTUFRAGMENT"
        fun newInstance(): ActuFragment = ActuFragment()
    }
/*
    private val viewModel: EventViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListEventAdapter()
        val mLayoutManager = GridLayoutManager(this.context, 2)
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
    }*/
}