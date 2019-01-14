package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class EventViewModel(private val dataRepository: DataRepository) : BaseViewModel() {

    val eventList: BehaviorSubject<List<Event>?> = BehaviorSubject.create()

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

    class Factory constructor(private val repository: DataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventViewModel(repository) as T
        }
    }
}