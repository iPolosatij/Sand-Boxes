package com.digitallabstudio.sandboxes.di

import android.content.Context
import android.content.Intent
import com.digitallabstudio.sandboxes.MainActivity
import com.digitallabstudio.sandboxes.MainViewModel
import com.digitallabstudio.sandboxes.data.Storages
import com.digitallabstudio.sandboxes.data.network.StoragesFactory
import com.digitallabstudio.sandboxes.presenter.screens.start_screens.StartViewModel
import com.digitallabstudio.sandboxes.presenter.screens.start_screens.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.dlsunity.arctour.domain.usecases.bd.DeleteAllBdDataUseCase
import space.dlsunity.arctour.domain.usecases.bd.GetBdDataUseCase
import space.dlsunity.arctour.domain.usecases.bd.SaveBdDataUseCase

val presentationModule = module {

    factory<Intent> {
        Intent(get<Context>(), MainActivity::class.java)
    }

    single<Storages> {
        StoragesFactory().createNew(get<Context>())
    }

    viewModel {
        MainViewModel()
    }


    viewModel {
        StartViewModel(
            localContext = get<Context>(),
        )
    }

    viewModel {
        WelcomeViewModel(
            localContext = get<Context>(),
            deleteAllBdDataUseCase = get<DeleteAllBdDataUseCase>(),
            saveBdDataUseCase = get<SaveBdDataUseCase>(),
            getAllBdDataUseCase = get<GetBdDataUseCase>()
        )
    }
}
