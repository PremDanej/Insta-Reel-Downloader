package com.merp.jet.ig.downloader.model

data class SaveUiState(
    val isLoading: Boolean = true,
    val reels: List<ReelResponse> = emptyList()
)