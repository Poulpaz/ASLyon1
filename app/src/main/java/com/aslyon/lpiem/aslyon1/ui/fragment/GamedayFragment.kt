package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ProfileViewPagerAdapter

class GamedayFragment : BaseFragment() {

    fun newInstance(): GamedayFragment= GamedayFragment()

    companion object {
        const val TAG= "GAMEDAYFRAGMENT"
        fun newInstance() : GamedayFragment = GamedayFragment()
    }
    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_gameday,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)}
}