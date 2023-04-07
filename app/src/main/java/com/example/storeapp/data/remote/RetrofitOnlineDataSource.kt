package com.example.storeapp.data.remote

import android.util.Log
import com.example.storeapp.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class RetrofitOnlineDataSource(private val service: ApiHelper): OnlineDataSource {
    private val products = mutableListOf<Product>()

    companion object {
        var errorMsg: String = ""
    }

    override suspend fun getProducts(): List<Product> {
        service.getProducts()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                errorMsg = e.message.toString()
                Log.e("TAG", errorMsg)
            }
            .collect {
                products.addAll(it)
            }

        return products

    }

}