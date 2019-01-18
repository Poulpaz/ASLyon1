package com.aslyon.lpiem.aslyon1.repository

import android.content.SharedPreferences
import android.util.Log
import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.datasource.request.NotificationData
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
    private var tokenSignUp: String? = null

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

    init {
        initToken()
    }

    //region signup
    fun signUp(lastname: String, firstname: String, dateOfBirth: Date, email: String, password: String, phoneNumber: String): Observable<NetworkEvent> {
        val registerData = SignUpData(lastname, firstname, getDateToString(dateOfBirth), email, password, phoneNumber)

        return service.signup(tokenSignUp, registerData)
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
                    val user = User(it.id, it.lastname, it.firstname, getStringToDate(it.dateOfBirth), it.email,it.password, it.phoneNumber, it.isAdmin)
                    connectedUser.onNext(user.toOptional())
                    token = it.token
                }

                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
        return obs
    }

    fun sendNotification(title: String, description: String): Observable<NetworkEvent> {
        val notificationData = NotificationData(title, description)

        return service.sendNotification(notificationData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
    }

    //region load User

    fun loadUser(): Observable<User> {
        return  service.getConnectedUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { User(it.id, it.lastname, it.firstname, getStringToDate(it.dateOfBirth), it.email,it.password, it.phoneNumber, it.isAdmin) }
                .doOnNext {
                    connectedUser.onNext(it.toOptional())
                }
                .share()
    }


    private fun getDateToString(date : Date): String{
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
        return sdf.format(date)
    }

    private fun getStringToDate(date : String): Date{
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
        return sdf.parse(date)
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
            return null
        }
    }

    fun initToken(){
        FirebaseInstanceId.getInstance().instanceId
                .addOnSuccessListener { result ->
                    tokenSignUp = result.token
                }
    }

    private fun deleteToken() {
        token = null
        keystoreManager.deleteAlias(tokenAlias)
    }

}