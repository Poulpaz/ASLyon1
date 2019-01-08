package com.aslyon.lpiem.aslyon1.dependencyinjection

import com.aslyon.lpiem.aslyon1.manager.KeystoreManager
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val managerModule = Kodein.Module("ManagerModule") {
    bind<KeystoreManager>() with singleton { KeystoreManager(instance()) }
}