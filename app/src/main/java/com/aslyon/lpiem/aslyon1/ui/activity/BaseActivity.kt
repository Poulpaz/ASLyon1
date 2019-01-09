package com.aslyon.lpiem.aslyon1.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.aslyon.lpiem.aslyon1.utils.RxLifecycleDelegate
import io.reactivex.Observable
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

open class BaseActivity : AppCompatActivity(), KodeinAware {

    private val rxDelegate = RxLifecycleDelegate()
    override val kodein by closestKodein()

    override fun onPause() {
        super.onPause()
        rxDelegate.onActivityPause()
    }

    override fun onStop() {
        super.onStop()
        rxDelegate.onActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxDelegate.onActivityDestroy()
    }

    protected fun lifecycle(event: RxLifecycleDelegate.ActivityEvent): Observable<RxLifecycleDelegate.ActivityEvent> {
        return rxDelegate.lifecycle(event)
    }
}