package com.example.currentapp.ui.convert

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currentapp.base.BaseViewModel
import com.example.currentapp.model.listofcurrencymodel.CurrencyListModel
import com.example.currentapp.model.liveprice.LivePriceModel
import com.example.currentapp.network.response.GetResponseApi
import com.example.currentapp.base.BaseRepository
import kotlinx.coroutines.launch

class ConvertViewModel(private val repository: BaseRepository) : BaseViewModel() {

    val onResultListOfCurrency: MutableLiveData<CurrencyListModel> = MutableLiveData()
    val onResultLivePrices: MutableLiveData<LivePriceModel> = MutableLiveData()
    val onResultError: MutableLiveData<String> = MutableLiveData()

    fun getPrices() {
        callLivePrices()
    }

    fun getList() {
        callListOfCurrency()
    }

    //region Calling Prices
    private fun callLivePrices() {
        viewModelScope.launch {
            when (val response = repository.getLivePrice()) {
                is GetResponseApi.ResponseSuccess -> {
                    onResultLivePrices.postValue(response.data as LivePriceModel)
                }
                is GetResponseApi.ResponseError -> {
                    onResultError.postValue(response.message)
                }
            }
        }

    }

    //endregion
    //region Calling List
    private fun callListOfCurrency() {
        viewModelScope.launch {
            when (val response = repository.getListOfCurrencies()) {
                is GetResponseApi.ResponseSuccess -> {
                    onResultListOfCurrency.postValue(response.data as CurrencyListModel)
                }
                is GetResponseApi.ResponseError -> {
                    onResultError.postValue(response.message)
                }
            }
        }
    }
    //endregion
}