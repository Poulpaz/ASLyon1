package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class ProfileViewModel(private val repository: UserRepository): BaseViewModel() {

    //region register
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region login
    val loginState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region notification
    val notificationState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region error
    val errorEditTextSignUp: PublishSubject<Int> = PublishSubject.create()
    val errorEditTextSignIn: PublishSubject<Int> = PublishSubject.create()
    val errorEditTextNotification: PublishSubject<Int> = PublishSubject.create()

    val connectedUser: BehaviorSubject<Optional<User>>
        get() {
            return repository.connectedUser
        }

    fun loadConnectedUser(){
        repository.loadUser().subscribe(
                { connectedUser.onNext(it.toOptional()) },
                { Timber.e(it) }
        )
    }


    fun connectedUser(): Boolean {
        val token = repository.token
        return !TextUtils.isEmpty(token)
    }

    fun sendNotification(title : String, description : String){
        if(validateNotificationText(title, description)){
            repository.sendNotification(title, description).subscribe(
                    { notificationState.onNext(it) },
                    { Timber.e(it) }
            )
        }
    }

    private fun validateNotificationText(title: String, description: String): Boolean {
        if(title.isNullOrEmpty() || description.isNullOrEmpty()){
            errorEditTextNotification.onNext(R.string.error_empty_edit_text)
            return false
        }else{ return true }
    }

    fun signup(lastname: String, firstname: String, dateOfBirth: Date?, email: String, password: String, confirmPassword: String, phoneNumber: String) {
        if (validateSignup(lastname, firstname, dateOfBirth, email, password, confirmPassword, phoneNumber)) {
            repository.signUp(lastname, firstname, dateOfBirth!!, email, password, phoneNumber)
                    .subscribe(
                            { registerState.onNext(it) },
                            { Timber.e(it) }
                    )
        }

    }

    fun login(email: String, password: String) {
        if (validateLogin(email, password)) {
            repository.login(email, password)
                    .subscribe(
                            { loginState.onNext(it) },
                            { Timber.e(it) }
                    )
        }
    }

    fun logout(){
        repository.logout()
    }

    private fun validateSignup(lastname: String, firstname: String, dateOfBirth: Date?, email: String, password: String, confirmPassword: String, phoneNumber: String): Boolean {
        return validateText(lastname) && validateText(firstname) && validateDate(dateOfBirth) && validateEmail(email) && validatePassword(password, confirmPassword) && validateText(phoneNumber)
    }

    private fun validateLogin(email: String?, password: String?): Boolean {
        return validateEmailLogIn(email) && validateTextLogIn(password)
    }


    private fun validateEmail(email: String?): Boolean {
        if (TextUtils.isEmpty(email)) {
            errorEditTextSignUp.onNext(R.string.error_empty_edit_text)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEditTextSignUp.onNext(R.string.error_invalidate_email)
            return false
        }
        return true
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            errorEditTextSignUp.onNext(R.string.error_empty_edit_text)
            return false
        }
        else if(!password.equals(confirmPassword)){
            errorEditTextSignUp.onNext(R.string.error_same_password)
            return false
        }
        return true
    }

    private fun validateDate(date: Date?): Boolean {
        if (date == null) {
            errorEditTextSignUp.onNext(R.string.error_selected_date)
            return false
        }
        return true
    }

    private fun validateText(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEditTextSignUp.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }

    private fun validateTextLogIn(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEditTextSignIn.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }

    private fun validateEmailLogIn(email: String?): Boolean {
        if (TextUtils.isEmpty(email)) {
            errorEditTextSignIn.onNext(R.string.error_empty_edit_text)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEditTextSignIn.onNext(R.string.error_invalidate_email)
            return false
        }
        return true
    }


    class Factory constructor(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(repository) as T
        }
    }
}