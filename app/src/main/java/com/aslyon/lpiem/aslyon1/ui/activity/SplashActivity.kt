package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hiding title bar of SplashActivity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //Making the activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        }, 3000)

    }


}