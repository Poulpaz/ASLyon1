package com.aslyon.lpiem.aslyon1.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.viewModel.AuthentificationViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module("ViewModelModule") {

    bind<AuthentificationViewModel.Factory>() with provider { AuthentificationViewModel.Factory(instance()) }
    bind<AuthentificationViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<AuthentificationViewModel.Factory>())
                .get(AuthentificationViewModel::class.java)
    }

}