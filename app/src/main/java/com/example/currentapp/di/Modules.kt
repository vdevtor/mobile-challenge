package com.example.currentapp.di

import com.example.currentapp.activities.mainactivity.ConvertCalc
import com.example.currentapp.network.api.ApiBuilder
import com.example.currentapp.network.response.ResponseCall
import com.example.currentapp.network.response.ResponseTreatment
import org.koin.dsl.module

val modules = module {
    single { ResponseCall() }
    single { ApiBuilder() }
    single { ResponseTreatment(responseCall = get()) }
    single { ConvertCalc() }

}