package com.example.currentapp.network.response

import com.example.currentapp.network.api.BtgApiService
import com.example.currentapp.base.BaseResponse

class ResponseCall : BaseResponse() {

    suspend fun responseCallList(): GetResponseApi {
        val response = returnListOfCurrency()
        return responseBase(response)
    }

    suspend fun responseCallPrice(): GetResponseApi {
        val response = returnLivePrice()
        return responseBase(response)
    }

    private suspend fun returnListOfCurrency() = BtgApiService.btgApi.getListOfCurrency()
    private suspend fun returnLivePrice() = BtgApiService.btgApi.getlivePrice()
}