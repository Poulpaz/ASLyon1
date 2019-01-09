package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.viewModel.AuthentificationViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.kodein.di.generic.instance
import timber.log.Timber

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

        b_signin_fragment.setOnClickListener {
            login()
        }

        viewModel.loginState.subscribe(
                {
                    when (it) {
                        NetworkEvent.None -> {
                            // Nothing
                        }
                        NetworkEvent.InProgress -> {
                            onSignInStateInProgress()
                        }
                        is NetworkEvent.Error -> {
                            onSignInStateError(it)
                        }
                        is NetworkEvent.Success -> {
                            onSignInStateSuccess()
                        }
                    }
                }, { Timber.e(it) }
        )
    }

    private fun onSignInStateSuccess() {
        val fragment = ProfileFragment()

        fragmentManager?.beginTransaction()
                ?.detach(fragment)
                ?.attach(fragment)
                ?.commit()
    }

    private fun onSignInStateError(error: NetworkEvent.Error) {

    }

    private fun onSignInStateInProgress() {

    }

    private fun login() {
        val email = et_email_signin_fragment.text.toString()
        val password = et_password_signin_fragment.text.toString()
        viewModel.login(email, password)
    }

}