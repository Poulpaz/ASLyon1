package com.aslyon.lpiem.aslyon1.dependencyinjection

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.viewModel.AuthentificationViewModel
import com.aslyon.lpiem.aslyon1.viewModel.EventDetailsViewModel
import com.aslyon.lpiem.aslyon1.viewModel.EventViewModel
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
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

    bind<EventViewModel.Factory>() with provider { EventViewModel.Factory(instance()) }
    bind<EventViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<EventViewModel.Factory>())
                .get(EventViewModel::class.java)
    }

    bind<ProfileViewModel.Factory>() with provider { ProfileViewModel.Factory(instance()) }
    bind<ProfileViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<ProfileViewModel.Factory>())
                .get(ProfileViewModel::class.java)
    }

    bind<EventDetailsViewModel.Factory>() with factory { idEvent : Int -> EventDetailsViewModel.Factory(instance(), idEvent) }
    bind<EventDetailsViewModel>() with factory { fragment: Fragment, idEvent : Int ->
        ViewModelProvider(fragment, instance<Int, EventDetailsViewModel.Factory>(arg = idEvent))
                .get(EventDetailsViewModel::class.java)
    }
}