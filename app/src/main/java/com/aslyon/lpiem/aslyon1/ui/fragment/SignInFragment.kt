package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.viewModel.AuthentificationViewModel
import org.kodein.di.generic.instance

class SignInFragment : BaseFragment() {

    companion object {
        const val TAG = "SIGNINFRAGMENT"
        fun newInstance(): SignInFragment = SignInFragment()
    }

    private val viewModel: AuthentificationViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}