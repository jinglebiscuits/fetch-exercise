package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.ItemDto
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ItemDataProcessorTest {

    private val processor = ItemDataProcessor()

    @Test
    fun `processItems filters out null or blank names and sorts correctly`() {
        // Arrange
        val inputItems = listOf(
            // Valid items
            ItemDto(id = 1, listId = 2, name = "101"),
            ItemDto(id = 2, listId = 2, name = "102"),
            ItemDto(id = 3, listId = 1, name = "1001"),
            ItemDto(id = 4, listId = 1, name = "1002"),
            // Items to be filtered out
            ItemDto(id = 5, listId = 1, name = ""),
            ItemDto(id = 6, listId = 1, name = null),
            ItemDto(id = 7, listId = 3, name = "   "),
            ItemDto(id = 8, listId = 2, name = null),
            // Invalid items
            ItemDto(id = null, listId = 1, name = "Invalid ID"),
            ItemDto(id = 9, listId = null, name = "Invalid ListId")
        )

        // Act
        val result = processor.processData(inputItems)

        // Assert
        // 1. Verify we have the correct number of groups
        assertThat("Only listId 1 and 2 should be present", result.size, `is`(2))

        // 2. Verify groups are sorted by listId
        assertThat("first group should be 1", result[0].listId, `is`(1))
        assertThat("second group should be 2", result[1].listId, `is`(2))

        // 3. Verify items within each group are sorted by name
        val group1 = result[0]
        assertThat("group1 should have 2 items", group1.items.size, `is`(2))
        assertThat("group1 item1 should be 1001", group1.items[0].name, `is`("1001"))
        assertThat("group1 item2 should be 1002", group1.items[1].name, `is`("1002"))

        val group2 = result[1]
        assertThat("group2 should have 2 items", group2.items.size, `is`(2))
        assertThat("group2 item1 should be 101", group2.items[0].name, `is`("101"))
        assertThat("group2 item2 should be 102", group2.items[1].name, `is`("102"))

        // 4. Verify filtered items are not present
        val allItems = result.flatMap { it.items }
        assertThat("There should only be 4 items present", allItems.size, `is`(4))
        assertThat(
            "check that the correct items are present",
            allItems.map { it.id },
            CoreMatchers.allOf(
                CoreMatchers.hasItem(1),
                CoreMatchers.hasItem(2),
                CoreMatchers.hasItem(3),
                CoreMatchers.hasItem(4)
            )
        )
        assertThat("no null or blank items", !allItems.any { it.name.isBlank() })
        assertThat("blank filtered out", !allItems.any { it.id == 5 })
        assertThat("null name filtered out", !allItems.any { it.id == 6 })
        assertThat("whitespace name filtered out", !allItems.any { it.id == 7 })
        assertThat("null id filtered out", !allItems.any { it.id == null })
        assertThat("null listId filtered out", !allItems.any { it.listId == null })
    }

    @Test
    fun `processItems handles empty input`() {
        // Act
        val result = processor.processData(emptyList())

        // Assert
        assertThat("check that empty is handled", result.isEmpty())
    }

    @Test
    fun `processItems handles all invalid items`() {
        // Arrange
        val inputItems = listOf(
            ItemDto(id = 1, listId = 1, name = ""),
            ItemDto(id = 2, listId = 1, name = null),
            ItemDto(id = null, listId = 1, name = "Invalid ID"),
            ItemDto(id = 3, listId = null, name = "Invalid ListId")
        )

        // Act
        val result = processor.processData(inputItems)

        // Assert
        assertThat("check that all invalid case is handled", result.isEmpty())
    }
}
