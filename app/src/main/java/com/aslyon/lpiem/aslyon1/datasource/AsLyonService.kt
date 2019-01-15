package com.aslyon.lpiem.aslyon1.datasource

import com.aslyon.lpiem.aslyon1.datasource.request.EventData
import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.datasource.request.OfferData
import com.aslyon.lpiem.aslyon1.datasource.request.SignUpData
import com.aslyon.lpiem.aslyon1.datasource.response.BaseResponse
import com.aslyon.lpiem.aslyon1.datasource.response.EventResponse
import com.aslyon.lpiem.aslyon1.datasource.response.LoginResponse
import com.aslyon.lpiem.aslyon1.model.*
import com.aslyon.lpiem.aslyon1.datasource.response.TokenData
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


    //region tournament
    @GET("tournaments")
    fun getTournaments(): Flowable<List<Tournament>>
    @GET("tournament/{idTournament}")
    fun getTournament(@Path("idTournament") idTournament : Int) : Observable<Tournament>


    //region shop
    @GET("offers")
    fun getOffers(): Flowable<List<Offer>>
    @GET("offer/{idOffer}")
    fun getOffer(@Path("idOffer") idOffer : Int) : Observable<Offer>
    @POST("newOffer")
    fun addoffer( @Body offer: OfferData): Observable<BaseResponse>


    //region FireBase
    @PUT("changeToken")
    fun updateFireBaseToken(@Header("token") token: TokenData): Observable<Unit>

    //region actu
    @GET("xml")
    fun getRSSActus(): Flowable<List<ItemsItem>>
}