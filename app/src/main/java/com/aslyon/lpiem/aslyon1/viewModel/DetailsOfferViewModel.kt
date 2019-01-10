package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class DetailsOfferViewModel(dataRepository: DataRepository, idOffer: Int) : BaseViewModel() {
    val offer: BehaviorSubject<Offer> = BehaviorSubject.create()



    init {
        dataRepository.loadOffer(idOffer)
                .subscribe(
                        {
                            offer.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory(private val repository: DataRepository, private val idOffer: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailsOfferViewModel(repository, idOffer) as T
        }
    }
}