package com.merp.jet.ig.downloader.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.ig.downloader.data.preference.InstaReelDataStore
import com.merp.jet.ig.downloader.utils.Constants.DEFAULT_EN_LOCALE_CODE
import com.merp.jet.ig.downloader.utils.Constants.IS_DARK
import com.merp.jet.ig.downloader.utils.Constants.IS_DYNAMIC_COLOR
import com.merp.jet.ig.downloader.utils.Constants.LOCALE_CODE
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
            instaReelDataStore.putBoolean(IS_DARK, value)
        }
    }

    fun getDark(): Boolean? = runBlocking {
        instaReelDataStore.getBoolean(IS_DARK)
    }

    fun setDynamicColor(value: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            instaReelDataStore.putBoolean(IS_DYNAMIC_COLOR, value)
        }
    }

    fun getDynamicColor(): Boolean? = runBlocking {
        instaReelDataStore.getBoolean(IS_DYNAMIC_COLOR)
    }

    fun setLanguage(value: String = DEFAULT_EN_LOCALE_CODE) {
        viewModelScope.launch {
            instaReelDataStore.putString(LOCALE_CODE, value)
        }
    }

    fun getLanguage(): String? = runBlocking {
        instaReelDataStore.getString(LOCALE_CODE)
    }
}