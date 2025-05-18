package com.scottwehby.fetchexercise.data.model

/**
 * This is the filtered data mapped to a usable model
 * for the rest of the application.
 */
data class Item(
    val id: Int,
    val listId: Int,
    val name: String
)
