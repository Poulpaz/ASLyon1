package com.aslyon.lpiem.aslyon1.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.utils.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SignUpFragment : BaseFragment(){

    var birthdayDate: Date? = null

    companion object {
        const val TAG = "SIGNINFRAGMENT"
        fun newInstance(): SignUpFragment = SignUpFragment()
        val codeBirthdayPicker = 100
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChipDatePicker()

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