package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.Group
import com.scottwehby.fetchexercise.data.model.Item

interface ItemRepository {
    suspend fun getGroupedItems(): Result<List<Group>>
    suspend fun deleteItem(item: Item)
}
