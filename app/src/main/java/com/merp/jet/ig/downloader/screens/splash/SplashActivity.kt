package com.merp.jet.ig.downloader.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.merp.jet.ig.downloader.MainActivity
import com.merp.jet.ig.downloader.R.anim.zoom_in
import com.merp.jet.ig.downloader.R.anim.zoom_out
import com.merp.jet.ig.downloader.screens.main.MainViewModel
import com.merp.jet.ig.downloader.screens.main.SystemBarIconColorTheme
import com.merp.jet.ig.downloader.ui.theme.IGDownloaderTheme
import com.merp.jet.ig.downloader.utils.Constants.DEFAULT_EN_LOCALE_CODE
import com.merp.jet.ig.downloader.utils.Utils.setAppLanguage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            val isDynamicColor = mainViewModel.getDynamicColor() ?: false
            val isDark = mainViewModel.getDark() ?: isSystemInDarkTheme()
            val languageCode = mainViewModel.getLanguage() ?: DEFAULT_EN_LOCALE_CODE

            LaunchedEffect(Unit) {
                if (languageCode != DEFAULT_EN_LOCALE_CODE) {
                    setAppLanguage(context, languageCode)
                }

                mainViewModel.setDynamicColor(value = isDynamicColor)
                mainViewModel.setDark(value = isDark)
                mainViewModel.setLanguage(value = languageCode)

                delay(3000L)
                startMainActivity()
            }

            SystemBarIconColorTheme(darkTheme = !isDark)

            IGDownloaderTheme(darkTheme = isDark, dynamicColor = isDynamicColor) {
                SplashScreen()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN,
                zoom_in,
                zoom_out,
                OVERRIDE_TRANSITION_CLOSE
            )
        } else {
            overridePendingTransition(zoom_in, zoom_out)
        }
    }
}