package com.aslyon.lpiem.aslyon1.datasource

import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.datasource.response.LoginResponse
import com.aslyon.lpiem.aslyon1.model.Event
import com.aslyon.lpiem.aslyon1.model.Offer
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AsLyonService {

    @POST("login")
    fun login(@Body loginData: LoginData): Observable<LoginResponse>

    //region home
    @GET("events")
    fun getEvents(): Flowable<List<Event>>

    @GET("offers")
    fun getOffers(): Flowable<List<Offer>>

    //region FireBase
    @POST("updateFireBaseToken")
    fun updateFireBaseToken(@Header("Authorization") token: String?, @Body newToken: String): Observable<Unit>

}