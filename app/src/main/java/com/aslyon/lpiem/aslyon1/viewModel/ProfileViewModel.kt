package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.repository.UserRepository

class ProfileViewModel(val repository: UserRepository): BaseViewModel() {

    fun connectedUser(): Boolean {
        val token = repository.token
        return !TextUtils.isEmpty(token)
    }

    class Factory constructor(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(repository) as T
        }
    }
}