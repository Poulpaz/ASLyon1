package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.adapter.ProfileViewPagerAdapter
import com.aslyon.lpiem.aslyon1.viewModel.AuthentificationViewModel
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment() {

    companion object {
        const val TAG = "PROFILEFRAGMENT"
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayHomeAsUpEnabled(false)
        setDisplayBotomBarNavigation(true)

        setActiveFragment()
    }

    private fun setActiveFragment() {
        val fragment = if(viewModel.connectedUser()) HomeFragment() else AuthentificationFragment()

        fragmentManager?.beginTransaction()
                ?.replace(R.id.container_profile_fragment, fragment, fragment::class.java.name)
                ?.addToBackStack(null)
                ?.commit()
    }
}