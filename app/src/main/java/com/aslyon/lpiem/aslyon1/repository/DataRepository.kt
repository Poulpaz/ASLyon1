package com.aslyon.lpiem.aslyon1.repository

import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.model.Tournament
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class DataRepository(private val service: AsLyonService) {

    val eventList: BehaviorSubject<List<Event>> = BehaviorSubject.create()
    val tournamentList: BehaviorSubject<List<Tournament>> = BehaviorSubject.create()
    val offerList: BehaviorSubject<List<Offer>> = BehaviorSubject.create()

    fun fetchEvent(): Flowable<List<Event>> {
        return service.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }

    fun loadEvent(idEvent : Int) : Observable<Event> {
        return service.getEvent(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }

    fun fetchTournament(): Flowable<List<Tournament>> {
        return service.getTournaments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }

    fun loadTournament(idTournament : Int) : Observable<Tournament> {
        return service.getTournament(idTournament)
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

    fun loadOffer(idOffer : Int) : Observable<Offer> {
        return service.getOffer(idOffer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }
}