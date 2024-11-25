package com.merp.jet.ig.downloader.repository

import com.merp.jet.ig.downloader.data.InstaReelDao
import com.merp.jet.ig.downloader.model.ReelResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveRepository @Inject constructor(private val instaReelDao: InstaReelDao) {

    fun getSaveReels(): Flow<List<ReelResponse>> = instaReelDao.getSaveReels()
    suspend fun deleteSaveReel(reelResponse: ReelResponse) = instaReelDao. deleteSaveReel(reelResponse)

}