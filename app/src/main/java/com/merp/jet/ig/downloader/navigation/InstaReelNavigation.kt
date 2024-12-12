package com.merp.jet.ig.downloader.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.AboutScreen
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.ReelScreen
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.SaveScreen
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.SettingScreen
import com.merp.jet.ig.downloader.screens.about.AboutScreen
import com.merp.jet.ig.downloader.screens.reel.ReelScreen
import com.merp.jet.ig.downloader.screens.save.SaveScreen
import com.merp.jet.ig.downloader.screens.setting.SettingScreen

@Composable
fun InstaReelNavigation(
    isDark: MutableState<Boolean>,
    isDynamicColor: MutableState<Boolean>,
    navController: NavHostController
) {
    NavHost(navController, ReelScreen.name) {
        composable(route = ReelScreen.name) {
            ReelScreen(navController)
        }
        composable(route = AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(route = SaveScreen.name) {
            SaveScreen(navController)
        }
        composable(route = SettingScreen.name) {
            SettingScreen(navController, isDark, isDynamicColor)
        }
    }
}