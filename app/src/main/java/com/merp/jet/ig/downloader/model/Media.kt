package com.merp.jet.ig.downloader.model

data class Media(
    val audioAvailable: Boolean,
    val cached: Boolean,
    val chunked: Boolean,
    val extension: String,
    val formattedSize: String,
    val quality: String,
    val requiresRendering: Boolean,
    val size: Int,
    val url: String,
    val videoAvailable: Boolean
)