package com.aslyon.lpiem.aslyon1.repository

import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.aslyon.lpiem.aslyon1.datasource.NetworkEvent
import com.aslyon.lpiem.aslyon1.datasource.request.EventData
import com.aslyon.lpiem.aslyon1.datasource.request.OfferData
import com.aslyon.lpiem.aslyon1.datasource.request.SignUpData
import com.aslyon.lpiem.aslyon1.datasource.request.TournamentData
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.model.ItemsItem
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.model.Tournament
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.text.SimpleDateFormat
import java.util.*

class DataRepository(private val service: AsLyonService) {

    val eventList: BehaviorSubject<List<Event>> = BehaviorSubject.create()
    val tournamentList: BehaviorSubject<List<Tournament>> = BehaviorSubject.create()
    val offerList: BehaviorSubject<List<Offer>> = BehaviorSubject.create()
    val actuList: BehaviorSubject<List<ItemsItem>> = BehaviorSubject.create()

    fun fetchEvent(): Flowable<List<Event>> {
        return service.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { list ->
                    list.map {
                        Event(it.idEvent, it.title, getStringToDate(it.date), it.place, it.price,it.description)
                    }
                }
                .share()
    }

    fun loadEvent(idEvent : Int) : Observable<Event> {
        return service.getEvent(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { Event(it.idEvent, it.title, getStringToDate(it.date), it.place, it.price,it.description) }
                .share()
    }

    fun addEvent(title: String, date: Date, place:String, price: String,description: String): Observable<NetworkEvent> {
        val eventData = EventData(title, getDateToString(date),place, price, description)

        return service.addevent(eventData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
    }


    fun fetchTournament(): Flowable<List<Tournament>> {
        return service.getTournaments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { list ->
                    list.map {
                        Tournament(it.idTournament, it.title,it.nbTeam,it.nbPlayersTeam,getStringToDate(it.date), it.place,it.description, it.price)
                    }
                }
                .share()
    }

    fun loadTournament(idTournament : Int) : Observable<Tournament> {
        return service.getTournament(idTournament)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { Tournament(it.idTournament, it.title,it.nbTeam,it.nbPlayersTeam,getStringToDate(it.date), it.place,it.description, it.price) }
                .share()
    }

    fun addTournament(title: String, nbTeam: String, nbPlayersTeam: String, date: Date, place:String,description: String, price: String): Observable<NetworkEvent> {
        val tournamentData = TournamentData(title, nbTeam,nbPlayersTeam, getDateToString(date),place, description, price)

        return service.addtournament(tournamentData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
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

    fun fetchActus(): Flowable<List<ItemsItem>> {
        return service.getRSSActus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .share()
    }

    fun addOffer(title: String, dateOffer: Date, nbParticipants:String, discount: String,description: String): Observable<NetworkEvent> {
        val offerData = OfferData(title, getDateToString(dateOffer),nbParticipants, discount, description)

        return service.addoffer(offerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<NetworkEvent> { NetworkEvent.Success }
                .onErrorReturn { NetworkEvent.Error(it) }
                .startWith(NetworkEvent.InProgress)
                .share()
    }

    private fun getDateToString(date : Date): String{
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return sdf.format(date)
    }

    private fun getStringToDate(date : String): Date{
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.parse(date)
    }
}