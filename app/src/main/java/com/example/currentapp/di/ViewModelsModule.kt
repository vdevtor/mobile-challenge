package com.example.currentapp.di

import com.example.currentapp.ui.convert.ConvertViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ConvertViewModel(repository = get()) }
}