package com.aslyon.lpiem.aslyon1.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListEventAdapter
import com.aslyon.lpiem.aslyon1.adapter.ListOfferAdapter
import com.aslyon.lpiem.aslyon1.ui.activity.AddOfferActivity
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsEventActivity
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsOfferActivity
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
        /*val ft = fragmentManager!!.beginTransaction()
        ft.detach(this).attach(this).commit()*/
        val adapter = ListOfferAdapter()
        val mLayoutManager = LinearLayoutManager(this.context)
        rv_offer_fragment.setLayoutManager(mLayoutManager)
        rv_offer_fragment.setItemAnimator(DefaultItemAnimator())
        rv_offer_fragment.adapter = adapter

        fab_offer_fragment.setOnClickListener{
            AddOfferActivity.start(activity as MainActivity)
        }
        swiperefrsh_fragment_offer.setOnRefreshListener {viewModel.getListOffer()}

        viewModel.connectedUser.subscribe(
                {
                    if(it.toNullable()?.isAdmin == 1){
                        fab_offer_fragment.show()
                    }
                    else{
                        fab_offer_fragment.hide()
                    }
                },
                { Timber.e(it) }
        )

        viewModel.offerList
                .subscribe(
                        {
                            if (it.isNullOrEmpty()) {
                                tv_offer_fragment.visibility = View.VISIBLE
                                swiperefrsh_fragment_offer.visibility = View.GONE
                                swiperefrsh_fragment_offer.isRefreshing = false

                            } else {
                                tv_offer_fragment.visibility = View.GONE
                                adapter.submitList(it)
                                swiperefrsh_fragment_offer.visibility = View.VISIBLE
                                swiperefrsh_fragment_offer.isRefreshing = false
                            }
                        },
                        { Timber.e(it) }
                )

        adapter.indexClickPublisher
                .subscribe(
                        {
                            DetailsOfferActivity.start(activity as MainActivity, it)
                        },
                        { Timber.e(it) }
                )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListOffer()
    }

    }

