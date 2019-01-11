package com.aslyon.lpiem.aslyon1.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.utils.DatePickerFragment
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.fragment_authentification.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SignUpFragment : BaseFragment() {

    var birthdayDate: Date? = null

    companion object {
        const val TAG = "SIGNINFRAGMENT"
        fun newInstance(): SignUpFragment = SignUpFragment()
        val codeBirthdayPicker = 100
    }

    private val viewModel: ProfileViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChipDatePicker()

        b_signup_fragment.setOnClickListener {
            val lastname = et_lastname_signup_fragment.text.toString()
            val firstname = et_firstname_signup_fragment.text.toString()
            val email = et_email_signup_fragment.text.toString()
            val password = et_password_signup_fragment.text.toString()
            val confirmPassword = et_confirm_password_signup_fragment.text.toString()
            val phoneNumber = et_phone_signup_fragment.text.toString()

            viewModel.signup(lastname, firstname, birthdayDate, email, password, confirmPassword, phoneNumber)
        }

        viewModel.registerState.subscribe(
                {
                    when (it) {
                        NetworkEvent.None -> {
                            // Nothing
                        }
                        NetworkEvent.InProgress -> {
                            onSignUpStateInProgress()
                        }
                        is NetworkEvent.Error -> {
                            onSignUpStateError(it)
                        }
                        is NetworkEvent.Success -> {
                            onSignUpStateSuccess()
                        }
                    }
                }, { Timber.e(it) }
        )
    }

    private fun onSignUpStateSuccess() {
        parentFragment?.parentFragment?.childFragmentManager?.findFragmentById(R.id.container_profile_fragment)?.vp_sign_authentification_fragment?.currentItem = 0
        b_signup_fragment.isEnabled = true
    }

    private fun onSignUpStateError(it: NetworkEvent.Error) {
        b_signup_fragment.isEnabled = true
    }

    private fun onSignUpStateInProgress() {
        b_signup_fragment.isEnabled = false
    }

    private fun initChipDatePicker() {
        chip_birthday_signup_fragment.setOnClickListener {
            val date = DatePickerFragment()
            date.setTargetFragment(this, codeBirthdayPicker)
            date.show(fragmentManager, tag)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == codeBirthdayPicker) {
            val calendar = data?.extras?.get("args") as Calendar
            birthdayDate = calendar.time
            chip_birthday_signup_fragment.text = getDateToString(calendar.time)
        }
    }

    private fun getDateToString(date: Date?): String {
        val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return if (date != null) df.format(date) else getString(R.string.tv_pick_birthday)
    }

}