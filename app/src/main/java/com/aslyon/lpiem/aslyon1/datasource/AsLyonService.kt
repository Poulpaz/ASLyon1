package com.aslyon.lpiem.aslyon1.datasource

import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.datasource.request.SignUpData
import com.aslyon.lpiem.aslyon1.datasource.response.BaseResponse
import com.aslyon.lpiem.aslyon1.datasource.response.LoginResponse
import com.aslyon.lpiem.aslyon1.datasource.response.TokenData
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.model.Offer
import com.aslyon.lpiem.aslyon1.model.Tournament
import com.aslyon.lpiem.aslyon1.model.User
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
    fun getEvents(): Flowable<List<Event>>

    @GET("event/{idEvent}")
    fun getEvent(@Path("idEvent") idEvent : Int) : Observable<Event>

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

    //region FireBase
    @PUT("changeToken")
    fun updateFireBaseToken(@Header("token") token: TokenData): Observable<Unit>

}