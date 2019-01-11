package com.aslyon.lpiem.aslyon1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.fragment_account_jnformations.*
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
    }

    private fun onConnectedUserChange(user: User?) {
        user?.let {
            tv_name_fragment_account_informations.text = it.lastname+" "+it.firstname
            tv_birthdate_fragment_account_informations.text = getDateToString(it.dateOfBirth)
            tv_mail_fragment_account_informations.text = it.email

            tv_phone_fragment_account_informations.text = it.phoneNumber  }

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

    override fun onResume() {
        super.onResume()
        displayDisconnectProfileButton(true)
    }
}

interface DisconnectUserInterface {
    fun disconnectUser()
}