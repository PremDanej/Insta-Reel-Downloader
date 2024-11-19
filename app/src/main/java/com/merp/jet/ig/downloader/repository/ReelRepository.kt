package com.merp.jet.ig.downloader.repository

import com.merp.jet.ig.downloader.data.InstaReelDao
import com.merp.jet.ig.downloader.di.Resource
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.network.ReelApi
import javax.inject.Inject

class ReelRepository @Inject constructor(private val api: ReelApi, private val instaReelDao: InstaReelDao) {

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

    suspend fun saveReel(reelResponse: ReelResponse) = instaReelDao.saveReel(reelResponse)
    suspend fun getSaveReelByUrl(url: String): ReelResponse = instaReelDao.getSaveReelByUrl(url)
    suspend fun deleteSaveReel(reelResponse: ReelResponse) = instaReelDao.deleteSaveReel(reelResponse)
}