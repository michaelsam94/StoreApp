package com.example.storeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.storeapp.data.model.Product
import com.example.storeapp.utils.Converters

@Database(entities = [Product::class], version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}