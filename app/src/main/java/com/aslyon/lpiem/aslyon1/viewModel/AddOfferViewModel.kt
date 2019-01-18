package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class AddOfferViewModel (private val dataRepository: DataRepository): BaseViewModel(){

    val errorEmptyText: PublishSubject<String> = PublishSubject.create()
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)
    val errorEditTextAddOffer: PublishSubject<Int> = PublishSubject.create()
    fun addoffer(title: String, startDate: Date?,endDate: Date?, nbParticipants: String, discount: String, description: String) {
        if (validateOffer(title, startDate, endDate, nbParticipants ,discount, description)) {
            dataRepository.addOffer(title, startDate!!,endDate!!, nbParticipants, discount,description)
                    .subscribe(
                            { registerState.onNext(it) },
                            { Timber.e(it) }
                    )
        }
    }
    private fun validateOffer(title: String?, startDate: Date?,endDate: Date?, discount: String?, description: String?, nbParticipants:String?) : Boolean {
        return validateTitle(title) && validateDate(startDate!!)&& validateDateEnd(endDate!!)&& validateParticipants(nbParticipants) && validatePrice(discount) && validateDescription(description)
    }

    private fun validateDate(date: Date?): Boolean {
        if (date == null) {
            errorEditTextAddOffer.onNext(R.string.error_selected_date)
            return false
        }
        return true
    }
    private fun validateDateEnd(date: Date?): Boolean {
        if (date == null) {
            errorEditTextAddOffer.onNext(R.string.error_selected_date)
            return false
        }
        return true
    }

    private fun validateTitle(email: String?): Boolean {
        if (TextUtils.isEmpty(email)) {
            errorEditTextAddOffer.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }
    private fun validateParticipants(email: String?): Boolean {
        if (TextUtils.isEmpty(email)) {
            errorEditTextAddOffer.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }
    private fun validatePrice(email: String?): Boolean {
        if (TextUtils.isEmpty(email)) {
            errorEditTextAddOffer.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }

    private fun validateDescription(text: String?): Boolean {
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