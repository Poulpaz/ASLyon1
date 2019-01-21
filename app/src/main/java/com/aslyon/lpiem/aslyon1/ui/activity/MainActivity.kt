package com.aslyon.lpiem.aslyon1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.aslyon.lpiem.aslyon1.ui.fragment.DisconnectUserInterface
import com.aslyon.lpiem.aslyon1.utils.or
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import com.facebook.AccessToken
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.internal.operators.maybe.MaybeToPublisher.instance
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.direct
import org.kodein.di.generic.M
import org.kodein.di.generic.instance
import com.google.firebase.iid.InstanceIdResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : BaseActivity() {

    companion object {
        fun start(fromActivity: AppCompatActivity) {
            fromActivity.startActivity(
                    Intent(fromActivity, MainActivity::class.java)
            )
        }
    }

    private var disconnectProfileButtonMenu: MenuItem? = null

    private lateinit var currentController: NavController
    private lateinit var navControllerHome: NavController
    private lateinit var navControllerProfile: NavController
    private lateinit var navControllerTournament: NavController
    private lateinit var navControllerShop: NavController

    private lateinit var homeWrapper: FrameLayout
    private lateinit var profileWrapper: FrameLayout
    private lateinit var tournamentWrapper: FrameLayout
    private lateinit var shopWrapper: FrameLayout
    private var lastimeBackPressed = 0L

    private lateinit var mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val userRepository: UserRepository by instance()

        mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var returnValue = false

            when (item.itemId) {
                R.id.navigation_home -> {
                    currentController = navControllerHome

                    homeWrapper.visibility = View.VISIBLE
                    profileWrapper.visibility = View.INVISIBLE
                    tournamentWrapper.visibility = View.INVISIBLE
                    shopWrapper.visibility = View.INVISIBLE
                    app_bar.visibility = View.VISIBLE
                    displayDisconnectProfileButton(false)
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
                    if (!TextUtils.isEmpty(userRepository.token)) displayDisconnectProfileButton(true) else displayDisconnectProfileButton(false)
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
                    displayDisconnectProfileButton(false)
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
                    displayDisconnectProfileButton(false)
                    supportActionBar?.setTitle(R.string.title_shop)

                    returnValue = true
                }
            }
            return@OnNavigationItemSelectedListener returnValue
        }

        FirebaseMessaging.getInstance().subscribeToTopic("aslyon")
                .addOnCompleteListener { task ->
                }

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseApp.initializeApp(this)
        initView()


        currentController = navControllerHome

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initView() {
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
        if (supportFragmentManager.backStackEntryCount == 0 &&
                lastimeBackPressed < System.currentTimeMillis() - 3000) {
            lastimeBackPressed = System.currentTimeMillis()
        } else{
            super.onBackPressed()
        }
    }

    fun displayDisconnectProfileButton(value: Boolean) {
        disconnectProfileButtonMenu?.isVisible = value
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        disconnectProfileButtonMenu = menu?.findItem(R.id.b_disconnect_profile)
        displayDisconnectProfileButton(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.b_disconnect_profile -> {
                Log.d("test", "test")
                val container = supportFragmentManager.findFragmentById(R.id.content_profile)
                val frg = container?.childFragmentManager?.findFragmentById(R.id.container_profile_fragment)
                if (frg is DisconnectUserInterface) {
                    frg.disconnectUser()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}