package com.example.saverx.app.di

import com.example.saverx.app.MainViewModel
import domain.DataRepositoryDomain
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            vkGetData = get(),
            dataRepos = get<DataRepositoryDomain>()
        )
    }
}