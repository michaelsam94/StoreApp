package com.example.storeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product(
    val category: String,
    val description: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
): Serializable

data class Rating(
    val count: Int,
    val rate: Double
): Serializable