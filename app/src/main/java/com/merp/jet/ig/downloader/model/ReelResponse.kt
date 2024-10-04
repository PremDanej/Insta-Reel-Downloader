package com.merp.jet.ig.downloader.model

data class ReelResponse(
    val duration: String,
    val medias: List<Media>,
    val sid: Any,
    val source: String,
    val thumbnail: String,
    val title: String,
    val url: String
)