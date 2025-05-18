package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.Group

class StubItemRepository(
    private val itemDataProcessor: ItemDataProcessor = ItemDataProcessor()
) :
    ItemRepository {

    private val sampleData = """
        [
        {"id": 755, "listId": 2, "name": ""},
        {"id": 203, "listId": 2, "name": ""},
        {"id": 684, "listId": 1, "name": "Item 684"},
        {"id": 276, "listId": 1, "name": "Item 276"},
        {"id": 736, "listId": 3, "name": null},
        {"id": 926, "listId": 4, "name": null},
        {"id": 808, "listId": 4, "name": "Item 808"},
        {"id": 599, "listId": 1, "name": null},
        {"id": 424, "listId": 2, "name": null},
        {"id": 444, "listId": 1, "name": ""},
        {"id": 809, "listId": 3, "name": null},
        {"id": 293, "listId": 2, "name": null},
        {"id": 510, "listId": 2, "name": null},
        {"id": 680, "listId": 3, "name": "Item 680"},
        {"id": 231, "listId": 2, "name": null},
        {"id": 534, "listId": 4, "name": "Item 534"},
        {"id": 294, "listId": 4, "name": ""},
        {"id": 439, "listId": 1, "name": null},
        {"id": 156, "listId": 2, "name": null},
        {"id": 906, "listId": 2, "name": "Item 906"},
        {"id": 49, "listId": 2, "name": null},
        {"id": 48, "listId": 2, "name": null},
        {"id": 735, "listId": 1, "name": "Item 735"},
        {"id": 52, "listId": 2, "name": ""},
        {"id": 681, "listId": 4, "name": "Item 681"},
        {"id": 137, "listId": 3, "name": "Item 137"}
        ]
    """.trimIndent()

    override suspend fun getGroupedItems(): Result<List<Group>> = runCatching {
        val itemsDto = MoshiProvider.parseJson(sampleData)
        itemDataProcessor.processData(itemsDto)
    }
}
