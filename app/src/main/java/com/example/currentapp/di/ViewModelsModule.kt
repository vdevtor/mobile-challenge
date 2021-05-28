package com.example.currentapp.di

import com.example.currentapp.viewmodels.ConvertViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ConvertViewModel(repository = get()) }
}