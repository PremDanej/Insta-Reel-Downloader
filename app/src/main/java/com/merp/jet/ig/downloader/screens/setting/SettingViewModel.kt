package com.merp.jet.ig.downloader.screens.setting

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.ig.downloader.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val repository: SettingRepository) :
    ViewModel() {

    fun deleteAllSavedReel() {
        viewModelScope.launch {
            try {
                repository.deleteAllSavedReel()
            } catch (e: Exception) {
                e("REEL", "SETTING_VIEWMODEL ERR | deleteAllSavedReel(): ${e.message}")
            }
        }
    }

    fun putString(key: String, value: String) {
        viewModelScope.launch {
            repository.putString(key, value)
        }
    }

    fun getString(key: String): String? = runBlocking {
        repository.getString(key)
    }
}