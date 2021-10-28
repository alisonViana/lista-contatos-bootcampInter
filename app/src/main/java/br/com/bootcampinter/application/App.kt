package br.com.bootcampinter.application

import android.app.Application
import br.com.bootcampinter.data.di.DataModule
import br.com.bootcampinter.presentation.di.PresentationModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Classe responsável pelo gerenciamento do repositório
 * Executada antes da MainActivity
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        PresentationModel.load()
    }
}