package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddEventViewModel (private val dataRepository: DataRepository): BaseViewModel() {

    val errorEditIntAddEvent: PublishSubject<Int> = PublishSubject.create()
    val errorEditTextAddEvent: PublishSubject<String> = PublishSubject.create()
    val saveEventState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    fun addevent(title: String,date: Date?, hour: Date?, place: String, price: String, description: String){

        if (validateEvent(title,date,hour,place,price,description)){

            dataRepository.addEvent(title,getDateHour(date!!, hour!!),place,price,description)
                    .subscribe(
                            {saveEventState.onNext(it)},
                            { Timber.e(it)}

                    )
        }

    }

    private fun getDateHour(date: Date, hour: Date): Date {
        date.hours = hour.hours
        date.minutes = hour.minutes

        val sdfDateHour = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val concatDateHour =  sdfDateHour.format(date)

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.parse(concatDateHour)
    }


    private fun validateEvent(title: String?,date: Date?,hour :Date? ,place: String?, price: String?, description: String?) : Boolean {
        return validateTitle(title) && validateDate(date)&& validateHour(hour) && validatePlace(place) && validatePrice(price) && validateDescription(description)
    }

    private fun validateDate(date: Date?): Boolean {
        if (date == null) {
            errorEditIntAddEvent.onNext(R.string.error_selected_date)
            return false
        }
        return true
    }
    private fun validateHour(date: Date?): Boolean {
        if (date == null) {
            errorEditIntAddEvent.onNext(R.string.error_selected_date)
            return false
        }
        return true
    }

    private fun validateTitle(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEditIntAddEvent.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }
    private fun validateDescription(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEditIntAddEvent.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }

    private fun validatePlace(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEditIntAddEvent.onNext(R.string.error_empty_edit_text)
            return false
        }
        return true
    }

    private fun validatePrice(text: String?): Boolean {
        if (TextUtils.isEmpty(text)) {
            errorEditIntAddEvent.onNext(R.string.error_empty_edit_text)
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