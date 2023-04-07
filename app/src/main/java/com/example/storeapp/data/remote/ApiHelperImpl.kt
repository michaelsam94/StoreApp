package com.example.storeapp.data.remote

import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getProducts()= flow { emit(apiService.getProducts()) }
}