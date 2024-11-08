package com.merp.jet.ig.downloader

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
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
            SystemBarIconColorTheme(!isDark.value)
            IGDownloaderTheme(darkTheme = isDark.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    InstaReelDownloaderApp(isDark)
                }
            }
        }
    }
}

@Composable
fun InstaReelDownloaderApp(isDark: MutableState<Boolean>) {

    val context: Context = LocalContext.current

    // Permission
    val storagePermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Access Denied", Toast.LENGTH_LONG).show()
            }
        }

    LaunchedEffect(Unit) {
        storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    InstaReelNavigation(isDark)
}


@Composable
fun SystemBarIconColorTheme(darkTheme: Boolean) {
    // Remember the current view
    val view = LocalView.current
    // Apply system bar icon colors
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as ComponentActivity).window
            val windowInsetsController = WindowInsetsControllerCompat(window, view)
            // Set light or dark icons based on theme
            windowInsetsController.isAppearanceLightStatusBars = darkTheme
            windowInsetsController.isAppearanceLightNavigationBars = darkTheme
            // Show transient bars by swipe gesture
            windowInsetsController.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}