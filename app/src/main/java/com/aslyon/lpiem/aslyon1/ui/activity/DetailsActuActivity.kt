package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R
import kotlinx.android.synthetic.main.activity_actu_details.*
import kotlinx.android.synthetic.main.activity_add_offer.*

class DetailsActuActivity : BaseActivity() {

    companion object {
        const val ExtraActuUrl = "ExtraActuUrl"
        fun start(activity: AppCompatActivity, actuUrl: String) = activity.startActivity(Intent(activity, DetailsActuActivity::class.java).apply {
            putExtra(ExtraActuUrl, actuUrl)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actu_details)
        setSupportActionBar(toolbarDetailsActu)
        setDisplayHomeAsUpEnabled(this,true)

        val actuUrl = intent.getStringExtra(DetailsActuActivity.ExtraActuUrl)

        if (actuUrl.isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.error_display_actu), Toast.LENGTH_SHORT).show()
        } else {
            wv_activity_actu_details.loadUrl(actuUrl)
        }
    }
}