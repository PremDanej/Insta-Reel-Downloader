package com.merp.jet.ig.downloader.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.merp.jet.ig.downloader.model.ReelResponse

@Database(entities = [ReelResponse::class], version = 1, exportSchema = false)
@TypeConverters(InstaReelConverters::class)
abstract class InstaReelDatabase : RoomDatabase() {
    abstract fun getInstaReelDao(): InstaReelDao
}