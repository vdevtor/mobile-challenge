package com.example.currentapp.base

import com.example.currentapp.network.response.GetResponseApi

interface BaseRepository {
    suspend fun getListOfCurrencies(): GetResponseApi
    suspend fun getLivePrice(): GetResponseApi
}