package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import com.gojuno.koptional.Optional
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class EventViewModel(private val dataRepository: DataRepository, private val userRepository: UserRepository) : BaseViewModel() {

    val eventList: BehaviorSubject<List<Event>?> = BehaviorSubject.create()
    val connectedUser: BehaviorSubject<Optional<User>> = userRepository.connectedUser

    init {
        getListEvent()
    }

    fun getListEvent(){

        dataRepository.fetchEvent()
                .subscribe(
                        {
                            eventList.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory constructor(private val dataRepository: DataRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventViewModel(dataRepository, userRepository) as T
        }
    }
}