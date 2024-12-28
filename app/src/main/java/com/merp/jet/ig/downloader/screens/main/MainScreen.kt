package com.merp.jet.ig.downloader.screens.main

import android.os.Build
import androidx.activity.ComponentActivity
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

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val isSystemIsDark = viewModel.getDark() ?: isSystemInDarkTheme()
    val isDark = remember { mutableStateOf(isSystemIsDark) }
    val isDynamicColor = remember { mutableStateOf(viewModel.getDynamicColor() ?: false) }
    val navController = rememberNavController()

    IGDownloaderTheme(
        darkTheme = isDark.value,
        dynamicColor = isDynamicColor.value)
    {
        Box(modifier = Modifier.fillMaxSize()
            .background(BACKGROUND_COLOR))
        {
            InstaReelNavigation(isDark, isDynamicColor, navController)
        }
    }

    // When `isDark` value is changed, This block will be execute.
    isDark.value.let {
        viewModel.setDark(it)
        SystemBarIconColorTheme(!it)
    }

    // When `isDynamicColor` value is changed, This block will be execute.
    isDynamicColor.value.let {
        viewModel.setDynamicColor(it)
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