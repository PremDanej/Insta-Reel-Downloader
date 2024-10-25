package com.merp.jet.ig.downloader.screens.reel

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.ScreenDefault

@Composable
fun ReelScreen(navController: NavController, viewModel: ReelViewModel = hiltViewModel()) {
    ScreenDefault(
        title = stringResource(R.string.app_name),
        isMainScreen = true,
        navController = navController
    ) {
        // Content
    }
}