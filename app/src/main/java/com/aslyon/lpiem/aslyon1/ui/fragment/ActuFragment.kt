package com.aslyon.lpiem.aslyon1.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListActuAdapter
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsActuActivity
import com.aslyon.lpiem.aslyon1.ui.activity.MainActivity
import com.aslyon.lpiem.aslyon1.viewModel.ActuViewModel
import kotlinx.android.synthetic.main.fragment_actu.*
import org.kodein.di.generic.instance
import timber.log.Timber

class ActuFragment : BaseFragment() {
    companion object {
        const val TAG = "ACTUFRAGMENT"
        fun newInstance(): ActuFragment = ActuFragment()
    }

    private val viewModel: ActuViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListActuAdapter()
        val mLayoutManager = LinearLayoutManager(context)
        rv_actu_fragment.setLayoutManager(mLayoutManager)
        rv_actu_fragment.setItemAnimator(DefaultItemAnimator())
        rv_actu_fragment.adapter = adapter

        viewModel.actuList
                .subscribe(
                        {
                            adapter.submitList(it)
                        },
                        { Timber.e(it) }
                )

        adapter.indexClickPublisher
                .subscribe(
                        {
                            DetailsActuActivity.start(activity as MainActivity, it)
                        },
                        { Timber.e(it) }
                )
    }
}