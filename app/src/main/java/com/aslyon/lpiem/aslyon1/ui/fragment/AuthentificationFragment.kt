package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ProfileViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_authentification.*

class AuthentificationFragment: BaseFragment() {

    companion object {
        const val TAG = "AUTHENTIFICATIONFRAGMENT"
        fun newInstance(): AuthentificationFragment = AuthentificationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authentification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

        setupViewPager()

    }

    private fun setupViewPager() {
        val adapter = ProfileViewPagerAdapter(childFragmentManager)
        adapter.addFragment(SignInFragment.newInstance(), getString(R.string.ti_signin_profile_fragment))
        adapter.addFragment(SignUpFragment.newInstance(), getString(R.string.ti_signup_profile_fragment))
        vp_sign_authentification_fragment.adapter = adapter
        tl_sign_authentification_fragment.setupWithViewPager(vp_sign_authentification_fragment)
    }

    override fun onResume() {
        super.onResume()
        displayDisconnectProfileButton(false)
    }

}