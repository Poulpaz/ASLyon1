package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.utils.or
import com.facebook.AccessToken
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun start(fromActivity: AppCompatActivity) {
            fromActivity.startActivity(
                    Intent(fromActivity, MainActivity::class.java)
            )
        }
    }

    private lateinit var currentController: NavController
    private lateinit var navControllerHome: NavController
    private lateinit var navControllerProfile: NavController
    private lateinit var navControllerTournament: NavController
    private lateinit var navControllerShop: NavController

    private lateinit var homeWrapper: FrameLayout
    private lateinit var profileWrapper: FrameLayout
    private lateinit var tournamentWrapper: FrameLayout
    private lateinit var shopWrapper: FrameLayout

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var returnValue = false

        when (item.itemId) {
            R.id.navigation_home -> {
                currentController = navControllerHome

                homeWrapper.visibility = View.VISIBLE
                profileWrapper.visibility = View.INVISIBLE
                tournamentWrapper.visibility = View.INVISIBLE
                shopWrapper.visibility = View.INVISIBLE
                app_bar.visibility = View.VISIBLE
                supportActionBar?.setTitle(R.string.title_home)

                returnValue = true
            }
            R.id.navigation_profile -> {

                currentController = navControllerProfile

                homeWrapper.visibility = View.INVISIBLE
                profileWrapper.visibility = View.VISIBLE
                tournamentWrapper.visibility = View.INVISIBLE
                shopWrapper.visibility = View.INVISIBLE
                app_bar.visibility = View.VISIBLE
                supportActionBar?.setTitle(R.string.title_profile)

                returnValue = true
            }
            R.id.navigation_tournament -> {

                currentController = navControllerProfile

                homeWrapper.visibility = View.INVISIBLE
                profileWrapper.visibility = View.INVISIBLE
                tournamentWrapper.visibility = View.VISIBLE
                shopWrapper.visibility = View.INVISIBLE
                app_bar.visibility = View.VISIBLE
                supportActionBar?.setTitle(R.string.title_tournament)

                returnValue = true
            }
            R.id.navigation_shop -> {

                currentController = navControllerProfile

                homeWrapper.visibility = View.INVISIBLE
                profileWrapper.visibility = View.INVISIBLE
                tournamentWrapper.visibility = View.INVISIBLE
                shopWrapper.visibility = View.VISIBLE
                app_bar.visibility = View.VISIBLE
                supportActionBar?.setTitle(R.string.title_shop)

                returnValue = true
            }
        }
        return@OnNavigationItemSelectedListener returnValue
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initView()

        currentController = navControllerHome

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initView(){
        navControllerHome = (supportFragmentManager
                .findFragmentById(R.id.content_home) as NavHostFragment)
                .navController

        navControllerProfile = (supportFragmentManager
                .findFragmentById(R.id.content_profile) as NavHostFragment)
                .navController

        navControllerTournament = (supportFragmentManager
                .findFragmentById(R.id.content_tournament) as NavHostFragment)
                .navController

        navControllerShop = (supportFragmentManager
                .findFragmentById(R.id.content_shop) as NavHostFragment)
                .navController

        homeWrapper = content_home_wrapper
        profileWrapper = content_profile_wrapper
        tournamentWrapper = content_tournament_wrapper
        shopWrapper = content_shop_wrapper
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        currentController.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        currentController.navigateUp()
        return true
    }

    override fun onBackPressed() {
        currentController
                .let { if (it.popBackStack().not()) finish() }
                .or { finish ()}
    }
}