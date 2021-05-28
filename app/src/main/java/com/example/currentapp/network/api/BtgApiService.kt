package com.example.currentapp.network.api

import org.koin.core.KoinComponent
import org.koin.core.get

object BtgApiService : KoinComponent {
    private val builder = get<ApiBuilder>()
    val btgApi: EndPoint = builder.getBtgCliente().create(EndPoint::class.java)
}