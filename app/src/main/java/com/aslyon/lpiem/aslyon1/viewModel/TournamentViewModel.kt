package com.aslyon.lpiem.aslyon1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslyon.lpiem.aslyon1.model.Tournament
import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import com.aslyon.lpiem.aslyon1.utils.disposedBy
import com.gojuno.koptional.Optional
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class TournamentViewModel(private val dataRepository: DataRepository, private val userRepository: UserRepository): BaseViewModel() {

    val tournamentList: BehaviorSubject<List<Tournament>?> = BehaviorSubject.create()
    val connectedUser: BehaviorSubject<Optional<User>> = userRepository.connectedUser

    init {
        getListTournament()
    }

    fun getListTournament(){

        dataRepository.fetchTournament()
                .subscribe(
                        {
                            tournamentList.onNext(it)
                        },
                        { Timber.e(it) }
                )
                .disposedBy(disposeBag)


    }

    class Factory constructor(private val dataRepository: DataRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TournamentViewModel(dataRepository, userRepository) as T
        }
    }

}