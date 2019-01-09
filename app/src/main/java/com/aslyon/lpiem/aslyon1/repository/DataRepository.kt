package com.aslyon.lpiem.aslyon1.repository

import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.model.Event
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class DataRepository(private val service: AsLyonService) {

    val eventList: BehaviorSubject<List<Event>> = BehaviorSubject.create()

    fun fetchEvent(): Flowable<List<Event>> {
        val obs = service.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()

        obs.subscribe(
                {
                    eventList.onNext(it)
                },
                { Timber.e(it)}
        )
        return obs
    }

    fun loadEvent(idEvent : Int) : Observable<Event> {
        return service.getEvent(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }
}