package com.aslyon.lpiem.aslyon1.dependencyinjection

import com.aslyon.lpiem.aslyon1.model.User
import com.aslyon.lpiem.aslyon1.repository.DataRepository
import com.aslyon.lpiem.aslyon1.repository.UserRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Author:        oronot
 * Creation date: 17/04/2018
 */
val repoModule = Kodein.Module("RepositoryModule") {
    bind<UserRepository>() with singleton { UserRepository(instance(), instance(), instance()) }
    bind<DataRepository>() with singleton { DataRepository(instance()) }
}