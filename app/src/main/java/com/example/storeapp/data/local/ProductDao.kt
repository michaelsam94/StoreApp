package com.example.storeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storeapp.data.model.Product

@Dao
interface ProductDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(products: List<Product>):List<Long>


    @Query("SELECT * FROM  Product")
    fun getAllProducts(): List<Product>

    @Query("DELETE FROM Product")
    suspend fun deleteAllProducts()


}