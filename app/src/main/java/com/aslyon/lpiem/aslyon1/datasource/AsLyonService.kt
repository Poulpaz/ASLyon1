package com.aslyon.lpiem.aslyon1.datasource

import com.aslyon.lpiem.aslyon1.datasource.request.*
import com.aslyon.lpiem.aslyon1.datasource.response.*
import com.aslyon.lpiem.aslyon1.model.*
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*

interface AsLyonService {

    //region profile
    @POST("login")
    fun login(@Body loginData: LoginData): Observable<LoginResponse>

    @POST("signup")
    fun signup(@Header("token") token: String?, @Body user: SignUpData): Observable<BaseResponse>

    @GET("connectedUser")
    fun getConnectedUser(@Header("token") token: String?): Observable<LoginResponse>

    //region home
    @GET("events")
    fun getEvents(): Flowable<List<EventResponse>>

    @GET("event/{idEvent}")
    fun getEvent(@Path("idEvent") idEvent : Int) : Observable<EventResponse>

    @POST("newEvent")
    fun addevent(@Body event:EventData): Observable<BaseResponse>

    @POST("newSubscribe_event")
    fun subscribeUserEvent(@Body subscribeEvent:SubscribeEventData): Observable<BaseResponse>

    @DELETE("removeSubscribe_event/{idUser}/{idEvent}")
    fun unsubscribeUserEvent(@Path("idUser") idUser : Int, @Path("idEvent") idEvent : Int): Observable<BaseResponse>

    @POST("isSubscribeEvent")
    fun isSubscribeEvent(@Body subscribeEvent:SubscribeEventData): Observable<IsSubscribeEventResponse>

    @GET("subscribe_event/{idEvent}")
    fun getListSubscribersEvent(@Path("idEvent") idEvent : Int): Observable<List<SubscribersEventResponse>>


    //region tournament
    @GET("tournaments")
    fun getTournaments(): Flowable<List<TournamentResponse>>

    @GET("tournament/{idTournament}")
    fun getTournament(@Path("idTournament") idTournament : Int) : Observable<TournamentResponse>

    @POST("newTournament")
    fun addtournament(@Body tournament: TournamentData): Observable<BaseResponse>

    //region shop
    @GET("offers")
    fun getOffers(): Flowable<List<OfferResponse>>

    @GET("offer/{idOffer}")
    fun getOffer(@Path("idOffer") idOffer : Int) : Observable<OfferResponse>

    @POST("newOffer")
    fun addoffer( @Body offer: OfferData): Observable<BaseResponse>

    //region actu
    @GET("xml")
    fun getRSSActus(): Flowable<List<ItemsItem>>

    @POST("sendNotification")
    fun sendNotification(@Body notification: NotificationData): Observable<BaseResponse>
}