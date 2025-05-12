package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.string.app_name

@Composable
fun ScreenDefault(
    title: String = stringResource(app_name),
    isMainScreen: Boolean = true,
    icon: ImageVector = Default.Favorite,
    navController: NavController,
    onBackPressed: () -> Unit = {},
    screenContent: @Composable () -> Unit = {},
) {
    Scaffold(topBar = {
        TopActionBar(
            title = title,
            isMainScreen = isMainScreen,
            icon = icon,
            navController = navController,
            onBackPressed = onBackPressed
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            if (!isMainScreen) {
                Divider()
            }
            screenContent()
        }
    }
}