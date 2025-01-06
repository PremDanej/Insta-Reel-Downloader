package com.merp.jet.ig.downloader.screens.save

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.repository.SaveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(private val repository: SaveRepository) : ViewModel() {

    var isLoading by mutableStateOf(true)
    private val _savedList = MutableStateFlow<List<ReelResponse>>(emptyList())
    val savedList = _savedList.asStateFlow()

    fun getSaveReels() {
        viewModelScope.launch {
            delay(250L)
            repository.getSaveReels().distinctUntilChanged()
                .catch { e ->
                    Log.e("REEL", "SAVE_VIEWMODEL ERR | getSaveReels(): ${e.message}")
                }.collect { list ->
                    isLoading = false
                    _savedList.value = list.reversed()
                }
        }
    }

    fun deleteSaveReel(reelResponse: ReelResponse) {
        viewModelScope.launch {
            try {
                repository.deleteSaveReel(reelResponse)
            } catch (e: Exception) {
                Log.e("REEL", "SAVE_VIEWMODEL ERR | deleteSaveReel(): ${e.message}")
            }
        }
    }
}