package com.example.storeapp.data.remote

import com.example.storeapp.data.model.Product


interface OnlineDataSource {
    suspend fun getProducts(): List<Product> = emptyList()
}