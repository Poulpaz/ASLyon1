package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment(), ProfileFragmentInterface {

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

    override fun setActiveFragment() {
        val fragment = if (viewModel.connectedUser()) AccountInformationsFragment.newInstance() else AuthentificationFragment.newInstance()
        if (fragment == AccountInformationsFragment()) displayDisconnectProfileButton(true) else displayDisconnectProfileButton(false)
        childFragmentManager?.popBackStack()
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentManager?.beginTransaction()
                ?.replace(R.id.container_profile_fragment, fragment)
                ?.addToBackStack(null)
                ?.commit()
    }
}

interface ProfileFragmentInterface {
    fun setActiveFragment()
}