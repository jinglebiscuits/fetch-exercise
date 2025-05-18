package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.Item

interface ItemRepository {
    suspend fun getGroupedItems(): Result<Map<Int, List<Item>>>
}
