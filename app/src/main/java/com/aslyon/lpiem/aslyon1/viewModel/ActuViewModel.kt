package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.ItemsItem
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class ActuViewModel(dataRepository: DataRepository) : BaseViewModel() {
    val actuList: BehaviorSubject<List<ItemsItem>?> = BehaviorSubject.create()

    init {
        dataRepository.fetchActus()
                .subscribe(
                        {
                            actuList.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory (private val repository: DataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActuViewModel(repository) as T
        }
    }
}