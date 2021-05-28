package com.example.currentapp.repositories

import com.example.currentapp.base.BaseRepository
import com.example.currentapp.network.response.GetResponseApi
import com.example.currentapp.network.response.ResponseTreatment
import org.koin.core.KoinComponent
import org.koin.core.get

class ConvertRepository(var responseTreatment: ResponseTreatment) : BaseRepository, KoinComponent {

    init {
        responseTreatment = get<ResponseTreatment>()
    }

    override suspend fun getListOfCurrencies(): GetResponseApi {
        return responseTreatment.getListOfCurrency()
    }

    override suspend fun getLivePrice(): GetResponseApi {
        return responseTreatment.getLivePrice()
    }
}