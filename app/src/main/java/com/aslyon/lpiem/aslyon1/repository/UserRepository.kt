package com.aslyon.lpiem.aslyon1.repository

import android.content.SharedPreferences
import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.manager.KeystoreManager
import com.aslyon.lpiem.aslyon1.model.User
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class UserRepository(private val service: AsLyonService,
                     private val keystoreManager: KeystoreManager,
                     private val sharedPref: SharedPreferences) {

    val connectedUser: BehaviorSubject<User> = BehaviorSubject.create()

    private val tokenKey = "TOKEN"
    private val tokenAlias = "TOKEN"
    private val pushToken = "PUSHTOKEN"

    var token: String? = null
        get() {
            if (field == null) {
                field = loadToken()
            }
            return if (!field.isNullOrBlank()) {
                "Bearer $field"
            } else {
                null
            }
        }
        set(value) {
            field = value
            saveToken(field)
        }
    //region login

    fun login(email: String, password: String): Observable<NetworkEvent> {
        val loginData = LoginData(email, password)

        val obs = service.login(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    connectedUser.onNext(it.user)
                    token = it.token
                }
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
        return obs
    }

    //region token

    private fun saveToken(token: String?) {
        val editor = sharedPref.edit()
        editor.putString(tokenKey, keystoreManager.encryptString(tokenAlias, token))
        editor.apply()
    }

    private fun loadToken(): String? {
        val passwordEncrypt = sharedPref.getString(tokenKey, null)
        return if (passwordEncrypt != null) {
            keystoreManager.decryptString(tokenAlias, passwordEncrypt)
        } else {
            null
        }
    }

    private fun deleteToken() {
        token = null
        keystoreManager.deleteAlias(tokenAlias)
    }

    fun updateToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            val newPushToken = it.token
            val oldPushToken = sharedPref.getString(pushToken, null)

            if (newPushToken != null) {
                val editor = sharedPref.edit()
                editor.putString(pushToken, newPushToken)
                editor.apply()

                if (oldPushToken.equals(newPushToken, false)) {
                    service.updateFireBaseToken(token, newPushToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({}, {
                                Timber.e(it)
                            })
                } else {
                    service.updateFireBaseToken(token, newPushToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({}, {
                                Timber.e(it)
                            })
                }
            }
        }
    }

}