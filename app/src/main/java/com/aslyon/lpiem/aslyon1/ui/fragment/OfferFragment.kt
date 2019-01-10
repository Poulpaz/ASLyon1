package com.aslyon.lpiem.aslyon1.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListEventAdapter
import com.aslyon.lpiem.aslyon1.adapter.ListOfferAdapter
import com.aslyon.lpiem.aslyon1.ui.activity.AddOfferActivity
import com.aslyon.lpiem.aslyon1.ui.activity.MainActivity
import com.aslyon.lpiem.aslyon1.viewModel.EventViewModel
import com.aslyon.lpiem.aslyon1.viewModel.OfferFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_offer.*
import org.kodein.di.generic.instance
import timber.log.Timber

class OfferFragment : BaseFragment() {


    private val viewModel: OfferFragmentViewModel by instance(arg = this)

    companion object {
        const val TAG = "OFFERFRAGMENT"
        fun newInstance(): OfferFragment = OfferFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_offer, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

        val adapter = ListOfferAdapter()
        val mLayoutManager = LinearLayoutManager(this.context)
        rv_offer_fragment.setLayoutManager(mLayoutManager)
        rv_offer_fragment.setItemAnimator(DefaultItemAnimator())
        rv_offer_fragment.adapter = adapter

        fab_offer_fragment.setOnClickListener{

            AddOfferActivity.start(activity as MainActivity)

        }

        viewModel.offerList
                .subscribe(
                        {
                            adapter.submitList(it)
                        },
                        { Timber.e(it) }
                )

    }

}