package com.merp.jet.ig.downloader.repository

import com.merp.jet.ig.downloader.di.Resource
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.network.ReelApi
import javax.inject.Inject

class ReelRepository @Inject constructor(private val api: ReelApi) {

    suspend fun getReelData(url: String): Resource<ReelResponse> {
        return try {
            Resource.Loading(true)
            val reelResponse = api.getReelData(url)
            Resource.Success(data = reelResponse)
        } catch (e: Exception) {
            Resource.Error(message = e.message)
        } finally {
            Resource.Loading(false)
        }
    }
}