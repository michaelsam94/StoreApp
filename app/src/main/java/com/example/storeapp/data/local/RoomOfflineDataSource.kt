package com.example.storeapp.data.local

import com.example.storeapp.data.model.Product

class RoomOfflineDataSource(private val productDao: ProductDao) : OfflineDataSource {

    override fun getProducts(): List<Product> = productDao.getAllProducts()

    override suspend fun cacheProducts(data: List<Product>) {
        productDao.insertList(data)
    }

    override suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }
}