package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.Group
import com.scottwehby.fetchexercise.data.model.Item
import com.scottwehby.fetchexercise.data.model.ItemDto

class ItemDataProcessor {
    fun processData(data: List<ItemDto>): List<Group> =
        data.filter { dto ->
                dto.id != null && dto.listId != null && !dto.name.isNullOrBlank()
            }
            .map { dto ->
                Item(
                    id = dto.id!!,
                    listId = dto.listId!!,
                    name = dto.name!!
                )
            }.groupBy { it.listId }
            .map { (listId, items) ->
                Group(
                    listId = listId,
                    items = items.sortedBy { it.id }
                )
            }
            .sortedBy { it.listId }

}
