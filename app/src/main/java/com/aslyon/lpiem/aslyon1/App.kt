package com.aslyon.lpiem.aslyon1

import android.app.Application
import android.content.Context
import com.aslyon.lpiem.aslyon1.dependencyinjection.networkModule
import com.aslyon.lpiem.aslyon1.dependencyinjection.repoModule
import com.aslyon.lpiem.aslyon1.dependencyinjection.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        bind<Application>() with singleton { this@App}
        bind<Context>() with singleton { instance<Application>() }

        import(networkModule)
        import(repoModule)
        import(viewModelModule)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}