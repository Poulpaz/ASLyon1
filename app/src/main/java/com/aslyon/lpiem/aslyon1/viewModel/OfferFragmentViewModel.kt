package com.aslyon.lpiem.aslyon1.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import com.gojuno.koptional.Optional
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class OfferFragmentViewModel(private val dataRepository: DataRepository, private val userRepository: UserRepository): BaseViewModel(){

    val offerList: BehaviorSubject<List<Offer>?> = BehaviorSubject.create()
    val connectedUser: BehaviorSubject<Optional<User>> = userRepository.connectedUser

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



    class Factory constructor(private val dataRepository : DataRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OfferFragmentViewModel(dataRepository, userRepository) as T
        }
    }
}