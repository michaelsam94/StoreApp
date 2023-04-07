package com.example.storeapp.data.remote

import com.example.storeapp.data.model.Product
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): List<Product>
}