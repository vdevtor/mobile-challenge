package com.example.currentapp.di

import com.example.currentapp.base.BaseRepository
import com.example.currentapp.repositories.ConvertRepository
import org.koin.dsl.module

val repositoriesModule = module {

    single<BaseRepository> { ConvertRepository(responseTreatment = get()) }
}