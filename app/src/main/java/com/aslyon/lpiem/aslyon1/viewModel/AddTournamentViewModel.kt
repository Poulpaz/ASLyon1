package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.ui.activity.BaseActivity
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddTournamentViewModel (private val dataRepository: DataRepository): BaseViewModel() {

    val errorEmptyText: PublishSubject<String> = PublishSubject.create()
    val registerState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)

    fun addtournament(title: String, nbTeam: String, nbPlayersTeam: String, date: Date, hour:Date, place:String,description: String, price: String)
        {

        if (validateTournament(title,nbTeam,nbPlayersTeam,date,hour,place,description,price)){

            dataRepository.addTournament(title,nbTeam,nbPlayersTeam,getDateHour(date, hour),place,description,price)
                    .subscribe(
                            {registerState.onNext(it)},
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

    private fun validateTournament(title: String?, nbTeam: String?, nbPlayersTeam: String?, date: Date?, hour: Date?, place:String?,description: String?, price: String?) : Boolean {
        return validateText(title) && validateText(nbTeam) && validateText(nbPlayersTeam) && validateDate(date)&& validateDate(hour)&& validateText(place) && validateText(price) && validateText(description)
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
            return AddTournamentViewModel(dataRepository) as T
        }
    }

}