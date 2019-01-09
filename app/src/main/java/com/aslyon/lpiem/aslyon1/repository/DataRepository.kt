package com.aslyon.lpiem.aslyon1.repository

import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.model.Offer
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class DataRepository(private val service: AsLyonService) {

    val eventList: BehaviorSubject<List<Event>> = BehaviorSubject.create()
    val offerList: BehaviorSubject<List<Event>> = BehaviorSubject.create()

    fun fetchEvent(): Flowable<List<Event>> {
        return service.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }

    fun fetchOffers(): Flowable<List<Offer>> {
        return service.getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }


}