package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.Group

interface ItemRepository {
    suspend fun getGroupedItems(): Result<List<Group>>
}
