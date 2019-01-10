package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Tournament
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class DetailsTournamentViewModel(dataRepository: DataRepository, idTournament: Int) : BaseViewModel() {

    val tournament: BehaviorSubject<Tournament> = BehaviorSubject.create()

    init {
        dataRepository.loadTournament(idTournament)
                .subscribe(
                        {
                            tournament.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)
    }

    class Factory(private val repository: DataRepository, private val idTournament: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailsTournamentViewModel(repository, idTournament) as T
        }
    }
}