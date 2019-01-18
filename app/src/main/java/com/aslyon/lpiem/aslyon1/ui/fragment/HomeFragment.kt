package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.HomeViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    companion object {
        const val TAG = "HOMEFRAGMENT"
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

    }

    private fun setupViewPager() {
        val adapter = HomeViewPagerAdapter(childFragmentManager)
        adapter.addFragment(EventFragment.newInstance(), getString(R.string.ti_events_home_fragment))
        adapter.addFragment(ActuFragment.newInstance(), getString(R.string.ti_actus_home_fragment))
        vp_events_actus_home_fragment.adapter = adapter
        tl_events_actus_home_fragment.setupWithViewPager(vp_events_actus_home_fragment)
    }
}