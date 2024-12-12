package com.merp.jet.ig.downloader

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.navigation.compose.rememberNavController
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.navigation.InstaReelNavigation
import com.merp.jet.ig.downloader.ui.theme.IGDownloaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isSystemIsDark = isSystemInDarkTheme()
            val isDark = remember { mutableStateOf(isSystemIsDark) }
            val isDynamicColor = remember { mutableStateOf(false) }
            val navController = rememberNavController()
            SystemBarIconColorTheme(!isDark.value)
            IGDownloaderTheme(darkTheme = isDark.value, dynamicColor = isDynamicColor.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BACKGROUND_COLOR)
                ) {
                    InstaReelNavigation(isDark, isDynamicColor, navController)
                }
            }
        }
    }
}

@Composable
fun SystemBarIconColorTheme(darkTheme: Boolean) {
    // Remember the current view
    val view = LocalView.current
    // Apply system bar icon colors
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as ComponentActivity).window
            window.navigationBarColor = Color.Transparent.toArgb()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            val windowInsetsController = WindowInsetsControllerCompat(window, view)
            // Set light or dark icons based on theme
            windowInsetsController.isAppearanceLightStatusBars = darkTheme
            windowInsetsController.isAppearanceLightNavigationBars = darkTheme
            // Show transient bars by swipe gesture
            windowInsetsController.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}