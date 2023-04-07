package com.example.storeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.ProductsRepository
import com.example.storeapp.data.model.Product
import com.example.storeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel(private val productsRepository: ProductsRepository) : ViewModel() {

    var products = MutableLiveData<Resource<Product>>()

    init {
        getHomeNews()
    }

    fun getHomeNews() {
        products.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = productsRepository.getProducts()
            products.postValue(Resource.Success(result))
            if(result.isNullOrEmpty()){
                products.postValue(Resource.Error(msg="No data saved "))
            }
        }
    }

    fun getProducts() = products as LiveData<Resource<Product>>

}