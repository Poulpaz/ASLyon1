package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class EventDetailsViewModel(dataRepository: DataRepository, idEvent: Int) : BaseViewModel() {
    val event: BehaviorSubject<Event> = BehaviorSubject.create()

    init {
        dataRepository.loadEvent(idEvent)
                .subscribe(
                        {
                            event.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory(private val repository: DataRepository, private val idEvent: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EventDetailsViewModel(repository, idEvent) as T
        }
    }
}