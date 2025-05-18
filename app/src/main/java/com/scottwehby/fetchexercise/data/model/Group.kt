package com.scottwehby.fetchexercise.data.model

/**
 * We want to display our data in groups.
 * This object will help cleanly represent our groups.
 */
data class Group (
    val listId: Int,
    val items: List<Item>
)
