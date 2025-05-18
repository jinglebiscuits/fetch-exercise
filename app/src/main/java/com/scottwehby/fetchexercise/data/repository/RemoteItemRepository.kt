package com.scottwehby.fetchexercise.data.repository

import com.scottwehby.fetchexercise.data.model.Group
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteItemRepository(
    private val itemDataProcessor: ItemDataProcessor = ItemDataProcessor()
) : ItemRepository {

    private val client = OkHttpClient()

    companion object {
        private const val HIRING_URL = "https://hiring.fetch.com/hiring.json"
    }

    override suspend fun getGroupedItems(): Result<List<Group>> = runCatching {
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(HIRING_URL)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code ${'$'}{response.code}")

                val responseBody = response.body?.string()
                    ?: throw IOException("Response body is null")
                itemDataProcessor.processData(MoshiProvider.parseJson(responseBody))
            }
        }
    }
} 
