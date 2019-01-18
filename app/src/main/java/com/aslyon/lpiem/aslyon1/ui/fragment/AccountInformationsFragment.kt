package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.fragment_account_jnformations.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AccountInformationsFragment : BaseFragment(), DisconnectUserInterface {


    companion object {
        const val TAG = "ACCOUNTINFORMATIONSFRAGMENT"
        fun newInstance(): AccountInformationsFragment = AccountInformationsFragment()
    }

    private val viewModel: ProfileViewModel by instance(arg = this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_jnformations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.connectedUser
                .subscribe({
                    onConnectedUserChange(it.toNullable())
                }, { Timber.e(it) })
        viewModel.loadConnectedUser()

        viewModel.errorEditTextNotification.subscribe(
                {
                    Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
                }, { Timber.e(it) }
        )

        viewModel.notificationState.subscribe(
                {
                    when (it) {
                        NetworkEvent.None -> {
                            // Nothing
                        }
                        NetworkEvent.InProgress -> {
                            onNotificationStateInProgress()
                        }
                        is NetworkEvent.Error -> {
                            onNotificationStateError(it)
                        }
                        is NetworkEvent.Success -> {
                            onNotificationStateSuccess()
                        }
                    }
                }, { Timber.e(it) }
        )

        b_send_notification_profile_fragment.setOnClickListener {
            val title = et_title_notification_profile_fragment.text.toString()
            val description = et_text_notification_profile_fragment.text.toString()
            viewModel.sendNotification(title, description)
        }
    }

    private fun onNotificationStateSuccess() {
        Toast.makeText(context, getString(R.string.tv_notification_sended), Toast.LENGTH_SHORT).show()
        progress_bar_notification.visibility = View.GONE
        b_send_notification_profile_fragment.isEnabled = true
    }

    private fun onNotificationStateError(error: NetworkEvent.Error) {
        progress_bar_notification.visibility = View.GONE
        b_send_notification_profile_fragment.isEnabled = true
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    private fun onNotificationStateInProgress() {
        progress_bar_notification.visibility = View.VISIBLE
        b_send_notification_profile_fragment.isEnabled = false
    }

    private fun onConnectedUserChange(user: User?) {
        user?.let {
            tv_name_fragment_account_informations.text = it.lastname + " " + it.firstname
            tv_birthdate_fragment_account_informations.text = getDateToString(it.dateOfBirth)
            tv_mail_fragment_account_informations.text = it.email
            tv_phone_fragment_account_informations.text = it.phoneNumber
            if (it.isAdmin == 1) {
                tv_admin_fragment_account_informations.visibility = View.VISIBLE
                group_send_notification_profile_fragment.visibility = View.VISIBLE
            } else {
                tv_admin_fragment_account_informations.visibility = View.GONE
                group_send_notification_profile_fragment.visibility = View.GONE
            }
        }

    }


    private fun getDateToString(date: Date?): String {
        val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        return df.format(date)
    }

    override fun disconnectUser() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.tv_title_dialog_logout)
                .setMessage(R.string.tv_message_dialog_logout)
                .setNegativeButton(R.string.b_cancel_dialog_logout, { dialoginterface, i -> })
                .setPositiveButton(R.string.b_validate_dialog_logout) { dialoginterface, i ->
                    viewModel.logout()
                    val frg = parentFragment?.childFragmentManager?.findFragmentById(R.id.content_profile)
                    if (frg is ProfileFragmentInterface) {
                        frg.setActiveFragment()
                    }
                }.show()
    }
}

interface DisconnectUserInterface {
    fun disconnectUser()
}