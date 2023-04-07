package com.example.storeapp.data.remote

import com.example.storeapp.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getProducts(): Flow<List<Product>>
}