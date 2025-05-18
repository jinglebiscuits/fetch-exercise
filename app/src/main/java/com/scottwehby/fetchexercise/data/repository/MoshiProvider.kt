package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.ItemDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiProvider {

    fun parseJson(json: String): List<ItemDto> {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(List::class.java, ItemDto::class.java)
        val jsonAdapter = moshi.adapter<List<ItemDto>>(type)
        return jsonAdapter.fromJson(json) ?: throw Exception("Failed to parse JSON")
    }
} 
