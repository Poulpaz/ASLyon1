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
import com.aslyon.lpiem.aslyon1.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_tournament.*
import timber.log.Timber

class TournamentFragment : BaseFragment() {

    companion object {
        const val TAG = "TOURNAMENTFRAGMENT"
        fun newInstance(): TournamentFragment = TournamentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tournament, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

        val adapter = ListTournamentAdapter()
        rv_tournament_fragment.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        rv_tournament_fragment.setItemAnimator(DefaultItemAnimator())
        rv_tournament_fragment.layoutManager = layoutManager

        fab_tournament_fragment.setOnClickListener {
            AddTournamentActivity.start(activity as MainActivity)
        }
    }

}