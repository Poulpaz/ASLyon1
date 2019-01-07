package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ProfileViewPagerAdapter

class SalesFragment : BaseFragment() {

    fun newInstance(): SalesFragment= SalesFragment()

    companion object {
        const val TAG= "SALESFRAGMENT"
        fun newInstance() : SalesFragment = SalesFragment()
    }
    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_sales,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)}
}





