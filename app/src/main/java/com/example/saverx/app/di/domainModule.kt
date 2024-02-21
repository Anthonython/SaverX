package com.example.saverx.app.di

import domain.VKGetDataUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { VKGetDataUseCase(repository = get()) }
}