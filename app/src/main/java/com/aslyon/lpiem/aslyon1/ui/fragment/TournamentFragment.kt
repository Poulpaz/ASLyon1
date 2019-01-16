package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ListTournamentAdapter
import com.aslyon.lpiem.aslyon1.ui.activity.AddTournamentActivity
import com.aslyon.lpiem.aslyon1.ui.activity.DetailsTournamentActivity
import com.aslyon.lpiem.aslyon1.ui.activity.MainActivity
import com.aslyon.lpiem.aslyon1.viewModel.TournamentViewModel
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_tournament.*
import org.kodein.di.generic.instance
import timber.log.Timber

class TournamentFragment : BaseFragment() {

    companion object {
        const val TAG = "TOURNAMENTFRAGMENT"
        fun newInstance(): TournamentFragment = TournamentFragment()
    }

    private val viewModel: TournamentViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tournament, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

        val adapter = ListTournamentAdapter()
        val mLayoutManager = LinearLayoutManager(context)
        rv_tournament_fragment.setLayoutManager(mLayoutManager)
        rv_tournament_fragment.setItemAnimator(DefaultItemAnimator())
        rv_tournament_fragment.adapter = adapter

        viewModel.connectedUser.subscribe(
                {
                    if(it.toNullable()?.isAdmin == 1){
                        fab_tournament_fragment.show()
                    }
                    else{
                        fab_tournament_fragment.hide()
                    }
                },
                { Timber.e(it) }
        )

        viewModel.tournamentList
                .subscribe(
                        {
                            adapter.submitList(it)
                            swiperefrsh_fragment_tournament.isRefreshing=false
                        },
                        { Timber.e(it) }
                )

        adapter.indexClickPublisher
                .subscribe(
                        {
                            DetailsTournamentActivity.start(activity as MainActivity, it)
                        },
                        { Timber.e(it) }
                )


        fab_tournament_fragment.setOnClickListener {
            AddTournamentActivity.start(activity as MainActivity)
        }

        swiperefrsh_fragment_tournament.setOnRefreshListener {viewModel.getListTournament()}
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListTournament()
    }

}