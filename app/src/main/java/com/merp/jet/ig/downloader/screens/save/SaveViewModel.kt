package com.merp.jet.ig.downloader.screens.save

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.model.SaveUiState
import com.merp.jet.ig.downloader.repository.SaveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(private val repository: SaveRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SaveUiState())
    val uiState: StateFlow<SaveUiState> = _uiState.asStateFlow()

    fun getSaveReels() {
        viewModelScope.launch {
            repository.getSaveReels()
                .distinctUntilChanged()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { e ->
                    Log.e("REEL", "SAVE_VIEWMODEL ERR | getSaveReels(): ${e.message}")
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collect { list ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            reels = list.reversed()
                        )
                    }
                }
        }
    }

    fun deleteSaveReel(reelResponse: ReelResponse) {
        viewModelScope.launch {
            runCatching {
                repository.deleteSaveReel(reelResponse)
            }.onFailure { e ->
                Log.e("REEL", "SAVE_VIEWMODEL ERR | deleteSaveReel(): ${e.message}")
            }
        }
    }
}