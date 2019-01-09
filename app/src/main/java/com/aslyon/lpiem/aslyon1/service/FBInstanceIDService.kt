package com.emlyon.makersstories.service

import com.aslyon.lpiem.aslyon1.App
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.google.firebase.iid.FirebaseInstanceIdService
import org.kodein.di.direct
import org.kodein.di.generic.instance

/**
 * Author:        oronot
 * Creation date: 24/05/2018
 */
class FBInstanceIDService : FirebaseInstanceIdService() {

    private lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        val kodein = (application as App).kodein
        userRepository = kodein.direct.instance()
    }

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        userRepository.updateToken()
    }
}