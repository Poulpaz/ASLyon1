package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.ItemsItem
import com.aslyon.lpiem.aslyon1.repository.RSSReader
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class ActuViewModel(rssReader: RSSReader) : BaseViewModel() {

    val actuList: BehaviorSubject<List<ItemsItem>?> = BehaviorSubject.create()

    init {
        rssReader.fetchActu()
                .subscribe(
                        {
                            actuList.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory constructor(private val rssReader: RSSReader) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActuViewModel(rssReader) as T
        }
    }
}