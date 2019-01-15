package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class AddEventViewModel (private val dataRepository: DataRepository): BaseViewModel() {

    val errorEmptyText: PublishSubject<String> = PublishSubject.create()
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    fun addevent(title: String,date: Date,place: String, price: String, description: String){

        if (validateEvent(title,date,place,price,description)){

            dataRepository.addEvent(title,date,place,price,description)
                    .subscribe(
                            {registerState.onNext(it)},
                            { Timber.e(it)}

                    )
        }

    }


    private fun validateEvent(title: String?,date: Date?,place: String?, price: String?, description: String?) : Boolean {
        return validateText(title) && validateDate(date)&& validateText(place) && validateText(price) && validateText(description)
    }

    private fun validateDate(date: Date?): Boolean {

        return date!= null
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
            return AddEventViewModel(dataRepository) as T
        }
    }
}