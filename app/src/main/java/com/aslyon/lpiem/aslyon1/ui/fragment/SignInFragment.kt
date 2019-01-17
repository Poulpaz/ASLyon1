package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.kodein.di.generic.instance
import org.kodein.di.newInstance
import timber.log.Timber

class SignInFragment : BaseFragment() {

    companion object {
        const val TAG = "SIGNINFRAGMENT"
        fun newInstance(): SignInFragment = SignInFragment()
    }

    private val viewModel: ProfileViewModel by instance(arg = this)

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

        viewModel.errorEditTextSignIn.subscribe(
                {
                    Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
                }, { Timber.e(it) }
        )
    }


    private fun onSignInStateSuccess() {
        val frg = parentFragment?.parentFragment?.childFragmentManager?.findFragmentById(R.id.content_profile)
        if (frg is ProfileFragmentInterface) {
            frg.setActiveFragment()
            progress_bar_signin.visibility = View.GONE
            b_signin_fragment.isEnabled = true
            displayDisconnectProfileButton(true)
            Toast.makeText(context, getString(R.string.tv_signin_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSignInStateError(error: NetworkEvent.Error) {
        progress_bar_signin.visibility = View.GONE
        b_signin_fragment.isEnabled = true
        Toast.makeText(context, getString(R.string.error_login), Toast.LENGTH_SHORT).show()
    }

    private fun onSignInStateInProgress() {
        progress_bar_signin.visibility = View.VISIBLE
        b_signin_fragment.isEnabled = false
    }

    private fun login() {
        val email = et_email_signin_fragment.text.toString()
        val password = et_password_signin_fragment.text.toString()
        viewModel.login(email, password)
    }

}