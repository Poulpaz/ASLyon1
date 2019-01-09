package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.viewModel.AuthentificationViewModel
import org.kodein.di.generic.instance

class AccountInformationsFragment : BaseFragment() {

    companion object {
        const val TAG = "ACCOUNTINFORMATIONSFRAGMENT"
        fun newInstance(): AccountInformationsFragment = AccountInformationsFragment()
    }

    private val viewModel: AuthentificationViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_jnformations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}