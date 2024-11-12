package com.merp.jet.ig.downloader.data

import androidx.room.TypeConverter
import com.merp.jet.ig.downloader.model.Media
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InstaReelConverters {

    @TypeConverter
    fun convertToString(list: List<Media>): String{
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun convertToList(string: String): List<Media>{
        return Json.decodeFromString<List<Media>>(string)
    }
}