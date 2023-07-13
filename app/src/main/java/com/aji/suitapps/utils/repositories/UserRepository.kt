package com.aji.suitapps.utils.repositories

import com.aji.suitapps.utils.api.APIConfig

class UserRepository {
    private val client = APIConfig.getApiService()
    suspend fun getList(page: Int, size: Int) = client.get(page, size)
}