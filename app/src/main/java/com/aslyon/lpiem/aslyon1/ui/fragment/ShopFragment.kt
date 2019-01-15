package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ProfileViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : BaseFragment() {

    companion object {
        const val TAG = "SHOPFRAGMENT"
        fun newInstance(): ShopFragment = ShopFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

        setupViewPager()

    }

    private fun setupViewPager() {
        val adapter = ProfileViewPagerAdapter(childFragmentManager)

        adapter.addFragment(SalesFragment.newInstance(), getString(R.string.ti_sales_shop_fragment))
        adapter.addFragment(SalesLyonFragment.newInstance(),getString(R.string.ti_offer_shop_lyon_fragment))
        adapter.addFragment(OfferFragment.newInstance(), getString(R.string.ti_offer_shop_fragment))

        vp_shop_fragment.adapter = adapter
        tl_shop_fragment.setupWithViewPager(vp_shop_fragment)
    }

}