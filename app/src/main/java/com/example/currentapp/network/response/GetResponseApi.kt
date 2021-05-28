package com.example.currentapp.network.response

abstract class GetResponseApi{
    class ResponseSuccess(val data: Any?) : GetResponseApi()
    class ResponseError(val message: String) : GetResponseApi()
}