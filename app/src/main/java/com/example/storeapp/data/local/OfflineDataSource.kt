package com.example.storeapp.data.local

import com.example.storeapp.data.model.Product

interface OfflineDataSource {
    fun getProducts(): List<Product> = emptyList()

    suspend fun cacheProducts(data: List<Product>){}

    suspend fun deleteAllProducts(){}

}