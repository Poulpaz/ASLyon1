package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.R
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import com.gojuno.koptional.None
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class DetailsEventViewModel(private val userRepository: UserRepository, private val dataRepository: DataRepository, private val idEvent: Int) : BaseViewModel() {

    val event: BehaviorSubject<Event> = BehaviorSubject.create()
    val errorSubscribeEvent: BehaviorSubject<Int> = BehaviorSubject.create()
    val subscribeEventState: BehaviorSubject<NetworkEvent> = BehaviorSubject.createDefault(NetworkEvent.None)
    val isSubscribeEvent: BehaviorSubject<Int> = BehaviorSubject.create()

    init {
        dataRepository.loadEvent(idEvent)
                .subscribe(
                        {
                            event.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)

        isSubscribeEvent()
    }

    fun isSubscribeEvent(){
        val user = userRepository.connectedUser.value?.toNullable()
        if (user != null){
            dataRepository.isSubscribeEvent(user.id, idEvent).subscribe(
                    {
                        isSubscribeEvent.onNext(it.subscribe)
                    },
                    { Timber.e(it) }
            )
        }
        else {
            isSubscribeEvent.onNext(0)
        }
    }

    fun subscribeEvent(){
        val user = userRepository.connectedUser.value?.toNullable()
        if (user != null){
            dataRepository.subscribeEvent(user.id, idEvent).subscribe(
                    {
                        subscribeEventState.onNext(it)
                    },
                    { Timber.e(it) }
            )
        }
        else{
            errorSubscribeEvent.onNext(R.string.error_subscribe_event_connected)
        }
    }

    fun unsubscribeEvent(){
        val user = userRepository.connectedUser.value?.toNullable()
        if (user != null){
            dataRepository.unsubscribeEvent(user.id, idEvent).subscribe(
                    {
                        subscribeEventState.onNext(it)
                    },
                    { Timber.e(it) }
            )
        }
        else{
            errorSubscribeEvent.onNext(R.string.error_subscribe_event_connected)
        }
    }

    class Factory(private val userRepository: UserRepository, private val dataRepository: DataRepository, private val idEvent: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailsEventViewModel(userRepository, dataRepository, idEvent) as T
        }
    }
}