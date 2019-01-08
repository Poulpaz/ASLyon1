package com.aslyon.lpiem.aslyon1.datasource

import com.aslyon.lpiem.aslyon1.datasource.request.LoginData
import com.aslyon.lpiem.aslyon1.datasource.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AsLyonService {

    @POST("login")
    fun login(@Body loginData: LoginData): Observable<LoginResponse>

    //region FireBase
    @POST("updateFireBaseToken")
    fun updateFireBaseToken(@Header("Authorization") token: String?, @Body newToken: String): Observable<Unit>

}