package com.aslyon.lpiem.aslyon1.dependencyinjection

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.aslyon.lpiem.aslyon1.BuildConfig
import com.aslyon.lpiem.aslyon1.datasource.AsLyonService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime

private object Tag {
    const val TAG_PROD_BASE_URL = "base_url"
}

val networkModule = Kodein.Module("Network") {

    val tagLoggingInterceptor = "loggingInterceptor"
    val tagGsonConverterFactory = "gsonConverterFactory"
    val tagRxJavaCallAdapterFactory = "rxJavaCallAdapterFactory"

    constant(Tag.TAG_PROD_BASE_URL) with "https://aslyon1api.herokuapp.com/api/"

    bind<Interceptor>(tagLoggingInterceptor) with singleton {
        val hli = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            hli.level = HttpLoggingInterceptor.Level.BODY
        } else {
            hli.level = HttpLoggingInterceptor.Level.NONE
        }
        hli
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
                .addInterceptor(instance(tagLoggingInterceptor))
                .build()
    }

    bind<Gson>() with singleton {
        GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer<LocalDateTime> { json, _, _ ->
            return@JsonDeserializer ZonedDateTime.parse(json.asJsonPrimitive.asString).toLocalDateTime()
        })
                .create()
    }

    bind<Converter.Factory>(tagGsonConverterFactory) with singleton { GsonConverterFactory.create(instance()) }

    bind<CallAdapter.Factory>(tagRxJavaCallAdapterFactory) with singleton { RxJava2CallAdapterFactory.create() }

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<AsLyonService>() with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(instance<String>(Tag.TAG_PROD_BASE_URL))
                .client(instance())
                .addConverterFactory(instance(tagGsonConverterFactory))
                .addCallAdapterFactory(instance(tagRxJavaCallAdapterFactory))
                .build()
                .create(AsLyonService::class.java)
    }

    bind<ConnectivityManager>() with provider {
        instance<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}