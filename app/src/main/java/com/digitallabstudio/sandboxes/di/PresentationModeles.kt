package com.digitallabstudio.sandboxes.di

import android.content.Context
import android.content.Intent
import com.digitallabstudio.sandboxes.data.Storages
import com.digitallabstudio.sandboxes.data.network.StoragesFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    factory<Intent> {
        Intent(get<Context>(), com.digitallabstudio.sandboxes.MainActivity::class.java)
    }

    single<Storages> {
        StoragesFactory().createNew(get<Context>())
    }

    viewModel {
        com.digitallabstudio.sandboxes.MainViewModel()
    }


    viewModel {
        com.digitallabstudio.sandboxes.presenter.screens.start_screens.StartViewModel(
            localContext = get<Context>(),
        )
    }

    viewModel {
        com.digitallabstudio.sandboxes.presenter.screens.start_screens.WelcomeViewModel(
            localContext = get<Context>(),
        )
    }
}
