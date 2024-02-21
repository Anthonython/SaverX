package com.example.saverx.app.di

import com.example.data.data.DataRepository
import domain.DataRepositoryDomain
import org.koin.dsl.module

val dataModule = module {
    single<DataRepositoryDomain> { DataRepository() }
}