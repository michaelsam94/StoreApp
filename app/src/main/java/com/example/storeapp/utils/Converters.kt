package com.example.storeapp.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.storeapp.data.model.Rating
import com.google.gson.Gson

@ProvidedTypeConverter
object Converters {

    @TypeConverter
    fun fromRating(value: Rating): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toRating(value: String): Rating {
        return try {
            Gson().fromJson(value,Rating::class.java)
        } catch (e: Exception) {
            Rating(0,0.0)
        }
    }

}

