package com.merp.jet.ig.downloader.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import com.merp.jet.ig.downloader.MainActivity
import com.merp.jet.ig.downloader.R.anim.*
import com.merp.jet.ig.downloader.screens.main.MainViewModel
import com.merp.jet.ig.downloader.screens.main.SystemBarIconColorTheme
import com.merp.jet.ig.downloader.ui.theme.IGDownloaderTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SystemBarIconColorTheme(darkTheme = !(mainViewModel.getDark() ?: isSystemInDarkTheme()))
            IGDownloaderTheme(
                darkTheme = mainViewModel.getDark() ?: false,
                dynamicColor = mainViewModel.getDynamicColor() ?: false
            ) {
                SplashScreen()
                Handler(mainLooper).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
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
                }, 3000L)
            }
        }
    }
}