package com.merp.jet.ig.downloader.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.merp.jet.ig.downloader.model.ReelResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface InstaReelDao {

    @Query("SELECT * FROM reel_tbl")
    fun getSaveReels(): Flow<List<ReelResponse>>

    @Query("SELECT * FROM reel_tbl WHERE url =:url")
    suspend fun getSaveReelByUrl(url: String): ReelResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReel(reel: ReelResponse)

    @Delete
    suspend fun deleteSaveReel(reel: ReelResponse)
}