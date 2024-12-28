package com.merp.jet.ig.downloader.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.ig.downloader.data.preference.InstaReelDataStore
import com.merp.jet.ig.downloader.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val instaReelDataStore: InstaReelDataStore) :
    ViewModel() {

    fun setDark(value: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            instaReelDataStore.putBoolean(Constants.IS_DARK, value)
        }
    }

    fun getDark(): Boolean? = runBlocking {
        instaReelDataStore.getBoolean(Constants.IS_DARK)
    }

    fun setDynamicColor(value: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            instaReelDataStore.putBoolean(Constants.IS_DYNAMIC_COLOR, value)
        }
    }

    fun getDynamicColor(): Boolean? = runBlocking {
        instaReelDataStore.getBoolean(Constants.IS_DYNAMIC_COLOR)
    }
}