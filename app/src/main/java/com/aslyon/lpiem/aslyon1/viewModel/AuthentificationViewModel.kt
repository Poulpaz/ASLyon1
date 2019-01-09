package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class AuthentificationViewModel(private val repository: UserRepository): BaseViewModel() {

    //region register
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region login
    val loginState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    //region error
    val errorEmptyEmail: PublishSubject<String> = PublishSubject.create()
    val errorEmptyPassword: PublishSubject<String> = PublishSubject.create()

    fun login(email: String, password: String) {
        if (validateLogin(email, password)) {
            repository.login(email, password)
                    .subscribe(
                            { loginState.onNext(it) },
                            { Timber.e(it) }
                    )
            repository.updateToken()
        }
    }

    private fun validateLogin(email: String?, password: String?): Boolean {
        return validateEmail(email) && validatePassword(password)
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

    private fun validatePassword(password: String?): Boolean {
        if (TextUtils.isEmpty(password)) {
            errorEmptyPassword.onNext("Le mot de passe est vide")
            return false
        }
        return true
    }

    class Factory constructor(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AuthentificationViewModel(repository) as T
        }
    }
}