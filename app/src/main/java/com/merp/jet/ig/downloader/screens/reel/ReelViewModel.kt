package com.merp.jet.ig.downloader.screens.reel

import android.content.Intent
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.ig.downloader.di.Resource
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.repository.ReelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReelViewModel @Inject constructor(private val repository: ReelRepository) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var isError by mutableStateOf(false)
    var isSaved by mutableStateOf(false)
    val reelResponse: MutableLiveData<ReelResponse> by lazy {
        MutableLiveData<ReelResponse>()
    }
    private val _reelLink = MutableStateFlow("")
    var reelLink: StateFlow<String> = _reelLink
    private var isIntentProcessed = false // Flag to process the intent only once

    fun processIntent(intent: Intent?) {
        if (!isIntentProcessed) {
            viewModelScope.launch {
                // Process only if not already set
                val sharedText = intent?.getStringExtra(Intent.EXTRA_TEXT)
                if (!sharedText.isNullOrEmpty() && _reelLink.value.isEmpty()) {
                    _reelLink.value = sharedText
                }
                isIntentProcessed = true
            }
        }
    }

    fun updateReelLink(newValue: String) {
        _reelLink.value = newValue
    }

    fun getReelData(url: String) {

        viewModelScope.launch {
            when (val response = repository.getReelData(url)) {
                is Resource.Success -> {
                    response.data.let {
                        reelResponse.value = it
                        isLoading = false
                        isError = false
                    }
                }

                is Resource.Error -> {
                    isLoading = false
                    isError = true
                    e("REEL", "REEL_VIEWMODEL ERR | getReelData(): ${response.message}")
                }

                else -> isLoading = false
            }
        }
    }

    fun saveReel(reelResponse: ReelResponse) {
        viewModelScope.launch {
            try {
                repository.saveReel(reelResponse)
            } catch (e: Exception) {
                e("REEL", "REEL_VIEWMODEL ERR | saveReel(): ${e.message}")
            }
        }
    }


    fun getSaveReelByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getSaveReelByUrl(url)?.let {
                    isSaved = it.url.trim() == url.trim()
                }
            } catch (e: Exception) {
                e("REEL", "REEL_VIEWMODEL ERR | getSaveReelByUrl(): ${e.message}")
            }
        }
    }

    fun deleteSaveReel(reelResponse: ReelResponse) {
        viewModelScope.launch {
            try {
                repository.deleteSaveReel(reelResponse)
            } catch (e: Exception) {
                e("REEL", "REEL_VIEWMODEL ERR | deleteSaveReel(): ${e.message}")
            }
        }
    }
}