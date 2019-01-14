package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class AddOfferViewModel (private val dataRepository: DataRepository): BaseViewModel(){

    val errorEmptyText: PublishSubject<String> = PublishSubject.create()
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    fun addoffer(title: String, dateOffer: String, teams: String,discount: String, link:String,description: String) {
        if (validateOffer(title, dateOffer, teams ,discount, link, description)) {
            dataRepository.addOffer(title, dateOffer, teams, discount, link, description)
                    .subscribe(
                            { registerState.onNext(it) },
                            { Timber.e(it) }
                    )
            //repository.updateToken()
        }
    }
    private fun validateOffer(title: String, dateOffer: String?, discount: String, description: String, teams:String?,link: String?) : Boolean {
        return validateText(title) && validateText(dateOffer) && validateText(discount) && validateText(description)&& validateText(teams) && validateText(link)
    }
    private fun validateText(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEmptyText.onNext("Vous devez remplir tous les champs")
            return false
        }
        return true
    }
    class Factory(private val dataRepository: DataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AddOfferViewModel(dataRepository) as T
        }
    }
}