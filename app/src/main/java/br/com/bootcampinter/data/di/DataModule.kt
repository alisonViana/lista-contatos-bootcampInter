package br.com.bootcampinter.data.di

import br.com.bootcampinter.data.database.ContactDatabase
import br.com.bootcampinter.data.repositories.ContactRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule {

    fun load() {
        loadKoinModules(daoModule() + repositoriesModule() )
    }

    // single - o koin devolve sempre a mesma instância
    // factory - o koin devolve uma instância nova a cada chamada

    private fun daoModule(): Module {
        return module {
            single { ContactDatabase.getInstance(androidContext()).contactDao }
        }
    }

    private fun repositoriesModule(): Module {
        return module {
            single { ContactRepository(get()) }
        }
    }

}
