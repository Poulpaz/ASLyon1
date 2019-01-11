package com.aslyon.lpiem.aslyon1.repository

import android.content.SharedPreferences
import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.datasource.request.SignUpData
import com.aslyon.lpiem.aslyon1.datasource.response.TokenData
import com.aslyon.lpiem.aslyon1.manager.KeystoreManager
import com.aslyon.lpiem.aslyon1.model.User
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.*
import java.text.SimpleDateFormat


class UserRepository(private val service: AsLyonService,
                     private val keystoreManager: KeystoreManager,
                     private val sharedPref: SharedPreferences) {

    val connectedUser: BehaviorSubject<Optional<User>> = BehaviorSubject.createDefault(None)

    private val tokenKey = "TOKEN"
    private val tokenAlias = "TOKEN"
    private val pushToken = "PUSHTOKEN"

    var token: String? = null
        get() {
            if (field == null) {
                field = loadToken()
            }
            return if (!field.isNullOrBlank()) {
                field
            } else {
                null
            }
        }
        set(value) {
            field = value
            saveToken(field)
        }

    //region signup
    fun signUp(lastname: String, firstname: String, dateOfBirth: Date, email: String, password: String, phoneNumber: String): Observable<NetworkEvent> {
        var newToken: String? = null
        /*FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
             newToken = it.token
        }*/

        val registerData = SignUpData(lastname, firstname, dateOfBirth.toString(), email, password, phoneNumber)

        return service.signup(token, registerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
    }

    //region login
    fun login(email: String, password: String): Observable<NetworkEvent> {
        val loginData = LoginData(email, password)

        val obs = service.login(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    val user = User(it.id, it.lastname, it.firstname, it.dateOfBirth, it.email,it.password, it.phoneNumber)
                    connectedUser.onNext(user.toOptional())
                    token = it.token
                }

                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
        return obs
    }

    fun logout() {
        deleteToken()
        connectedUser.onNext(None)
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

                val tokenData = TokenData(oldPushToken, newPushToken)
                service.updateFireBaseToken(tokenData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({}, {
                            Timber.e(it)
                        })
            }
        }
    }

}