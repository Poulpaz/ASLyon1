package com.aslyon.lpiem.aslyon1.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.viewModel.*
import com.aslyon.lpiem.aslyon1.viewModel.ProfileViewModel
import com.aslyon.lpiem.aslyon1.viewModel.EventViewModel
import com.aslyon.lpiem.aslyon1.viewModel.OfferFragmentViewModel
import com.aslyon.lpiem.aslyon1.viewModel.TournamentViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module("ViewModelModule") {

    bind<ProfileViewModel.Factory>() with provider { ProfileViewModel.Factory(instance()) }
    bind<ProfileViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<ProfileViewModel.Factory>())
                .get(ProfileViewModel::class.java)
    }

    bind<EventViewModel.Factory>() with provider { EventViewModel.Factory(instance()) }
    bind<EventViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<EventViewModel.Factory>())
                .get(EventViewModel::class.java)
    }

    bind<DetailsEventViewModel.Factory>() with factory { idEvent : Int -> DetailsEventViewModel.Factory(instance(), idEvent) }
    bind<DetailsEventViewModel>() with factory { activity: AppCompatActivity, idEvent : Int ->
        ViewModelProvider(activity, instance<Int, DetailsEventViewModel.Factory>(arg = idEvent))
                .get(DetailsEventViewModel::class.java)
    }

    bind<OfferFragmentViewModel.Factory>() with provider { OfferFragmentViewModel.Factory(instance()) }
    bind<OfferFragmentViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<OfferFragmentViewModel.Factory>())
                .get(OfferFragmentViewModel::class.java)
    }

    bind<TournamentViewModel.Factory>() with provider { TournamentViewModel.Factory(instance()) }
    bind<TournamentViewModel>() with factory { fragment: Fragment ->
        ViewModelProvider(fragment, instance<TournamentViewModel.Factory>())
                .get(TournamentViewModel::class.java)
    }

    bind<DetailsTournamentViewModel.Factory>() with factory { idTournament : Int -> DetailsTournamentViewModel.Factory(instance(), idTournament) }
    bind<DetailsTournamentViewModel>() with factory { activity: AppCompatActivity, idTournament : Int ->
        ViewModelProvider(activity, instance<Int, DetailsTournamentViewModel.Factory>(arg = idTournament))
                .get(DetailsTournamentViewModel::class.java)
    }
}