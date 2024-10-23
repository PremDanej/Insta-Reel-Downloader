package com.merp.jet.ig.downloader.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.merp.jet.ig.downloader.screens.about.AboutScreen
import com.merp.jet.ig.downloader.screens.reel.ReelScreen
import com.merp.jet.ig.downloader.screens.save.SaveScreen
import com.merp.jet.ig.downloader.screens.setting.SettingScreen

@Composable
fun InstaReelNavigation(isDark: MutableState<Boolean>) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = InstaReelScreens.ReelScreen.name) {

        composable(route = InstaReelScreens.ReelScreen.name) {
            ReelScreen(navController)
        }
        composable(route = InstaReelScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(route = InstaReelScreens.SaveScreen.name) {
            SaveScreen(navController)
        }
        composable(route = InstaReelScreens.SettingScreen.name) {
            SettingScreen(navController, isDark)
        }
    }
}