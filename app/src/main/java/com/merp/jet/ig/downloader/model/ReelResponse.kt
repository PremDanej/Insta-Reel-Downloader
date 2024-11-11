package com.merp.jet.ig.downloader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "reel_tbl")
data class ReelResponse(

    @PrimaryKey
    @ColumnInfo("url")
    val url: String,

    @ColumnInfo("thumbnail")
    val thumbnail: String,

    @ColumnInfo("duration")
    val duration: String,

    @ColumnInfo("source")
    val source: String,

    @ColumnInfo("medias")
    val medias: List<Media>,

    val title: String? = null,
    val sid: String? = null
)