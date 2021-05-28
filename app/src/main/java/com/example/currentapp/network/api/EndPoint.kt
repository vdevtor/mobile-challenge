package com.example.currentapp.network.api

import com.example.currentapp.model.listofcurrencymodel.CurrencyListModel
import com.example.currentapp.model.liveprice.LivePriceModel
import retrofit2.Response
import retrofit2.http.GET

interface EndPoint {

    @GET("list")
    suspend fun getListOfCurrency(): Response<CurrencyListModel>

    @GET("live")
    suspend fun getlivePrice(): Response<LivePriceModel>
}