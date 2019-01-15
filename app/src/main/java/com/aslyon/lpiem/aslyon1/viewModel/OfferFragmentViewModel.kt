package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class OfferFragmentViewModel(private val dataRepository: DataRepository): BaseViewModel(){

    val offerList: BehaviorSubject<List<Offer>?> = BehaviorSubject.create()

    init {
        getListOffer()
    }

    fun getListOffer(){
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