package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.gojuno.koptional.Optional
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.w3c.dom.Text
import timber.log.Timber
import java.util.*

class ProfileViewModel(private val repository: UserRepository): BaseViewModel() {

    //region register
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region login
    val loginState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region error
    val errorEmptyEmail: PublishSubject<String> = PublishSubject.create()
    val errorEmptyText: PublishSubject<String> = PublishSubject.create()
    val errorEmptyPassword: PublishSubject<String> = PublishSubject.create()
    val connectedUser: BehaviorSubject<Optional<User>>
        get() {
            return repository.connectedUser
        }


    fun connectedUser(): Boolean {
        val token = repository.token
        return !TextUtils.isEmpty(token)
    }

    fun signup(lastname: String, firstname: String, dateOfBirth: Date?, email: String, password: String, confirmPassword: String, phoneNumber: String) {
        if (validateSignup(lastname, firstname, dateOfBirth, email, password, confirmPassword, phoneNumber)) {
            repository.signUp(lastname, firstname, dateOfBirth!!, email, password, phoneNumber)
                    .subscribe(
                            { registerState.onNext(it) },
                            { Timber.e(it) }
                    )
            //repository.updateToken()
        }
    }

    fun login(email: String, password: String) {
        if (validateLogin(email, password)) {
            repository.login(email, password)
                    .subscribe(
                            { loginState.onNext(it) },
                            { Timber.e(it) }
                    )
            //repository.updateToken()
        }
    }

    fun logout(){
        repository.logout()
    }

    private fun validateSignup(lastname: String, firstname: String, dateOfBirth: Date?, email: String, password: String, confirmPassword: String, phoneNumber: String): Boolean {
        return validateText(lastname) && validateText(firstname) && validateDate(dateOfBirth) && validateEmail(email) && validatePassword(password, confirmPassword) && validateText(phoneNumber)
    }

    private fun validateLogin(email: String?, password: String?): Boolean {
        return validateEmail(email) && validateText(password)
    }


    private fun validateEmail(email: String?): Boolean {
        if (TextUtils.isEmpty(email)) {
            errorEmptyEmail.onNext("L'email est vide")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmptyEmail.onNext("L'email est invalide")
            return false
        }
        return true
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            errorEmptyText.onNext("Vous devez remplir tous les champs")
            return false
        }
        else if(!password.equals(confirmPassword)){
            errorEmptyText.onNext("Les mots de passe ne correspondent pas")
            return false
        }
        return true
    }

    private fun validateDate(date: Date?): Boolean {
        if (date == null) {
            errorEmptyText.onNext("Vous devez sélectionner une date")
            return false
        }
        return true
    }

    private fun validateText(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEmptyText.onNext("Vous devez remplir tous les champs")
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