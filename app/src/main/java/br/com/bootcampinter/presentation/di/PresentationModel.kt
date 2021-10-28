package br.com.bootcampinter.presentation.di

import br.com.bootcampinter.presentation.DetailViewModel
import br.com.bootcampinter.presentation.EditViewModel
import br.com.bootcampinter.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModel {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            viewModel { MainViewModel(get()) }
            viewModel { DetailViewModel(get()) }
            viewModel { EditViewModel(get()) }
        }
    }
}