package com.example.storeapp.data

import com.example.storeapp.data.local.OfflineDataSource
import com.example.storeapp.data.model.Product
import com.example.storeapp.data.remote.OnlineDataSource
import com.example.storeapp.utils.NetworkAwareHandler

class ProductsRepository(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkHandler: NetworkAwareHandler
) {



    suspend fun getProducts(): List<Product> {
        return if (networkHandler.isOnline()) {
            val data = onlineDataSource.getProducts()
            offlineDataSource.cacheProducts(data)
            data
        } else {
            offlineDataSource.getProducts()
        }

    }

}