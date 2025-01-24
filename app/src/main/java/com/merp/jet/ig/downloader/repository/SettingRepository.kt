package com.merp.jet.ig.downloader.repository

import com.merp.jet.ig.downloader.data.InstaReelDao
import com.merp.jet.ig.downloader.data.preference.InstaReelDataStore
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val instaReelDao: InstaReelDao,
    private val instaReelDataStore: InstaReelDataStore,
) {

    suspend fun deleteAllSavedReel() = instaReelDao.deleteAllSavedReel()

    suspend fun putString(key: String, value: String) = instaReelDataStore.putString(key, value)

    suspend fun getString(key: String): String? = instaReelDataStore.getString(key)

}