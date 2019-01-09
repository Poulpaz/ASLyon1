package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class OfferFragmentViewModel(private val dataRepository: DataRepository): BaseViewModel(){

    val offerList: BehaviorSubject<List<Offer>?> = BehaviorSubject.create()

    init {
        dataRepository.fetchOffers()
                .subscribe(
                        {
                            offerList.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory constructor(private val dataRepository : DataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OfferFragmentViewModel(dataRepository) as T
        }
    }
}