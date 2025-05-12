package com.merp.jet.ig.downloader.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.merp.jet.ig.downloader.R.string.app_name
import com.merp.jet.ig.downloader.R.string.lbl_about
import com.merp.jet.ig.downloader.R.string.lbl_save
import com.merp.jet.ig.downloader.R.string.lbl_setting
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.AboutScreen
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.SaveScreen
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.SettingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopActionBar(
    title: String = stringResource(app_name),
    isMainScreen: Boolean = true,
    icon: ImageVector = Default.Favorite,
    navController: NavController,
    onBackPressed: () -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog, navController)
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = ON_BACKGROUND_COLOR,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackPressed() },
                enabled = !isMainScreen
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = ON_BACKGROUND_COLOR
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(BACKGROUND_COLOR),
        actions = {
            if (isMainScreen) {
                IconButton(
                    onClick = {
                        showDialog.value = true
                    }) {
                    Icon(
                        imageVector = Default.MoreVert,
                        contentDescription = "Icon",
                        tint = ON_BACKGROUND_COLOR
                    )
                }
            }
        }
    )
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController = NavHostController(LocalContext.current)
) {
    val about: String = stringResource(id = lbl_about)
    val save: String = stringResource(id = lbl_save)
    val setting: String = stringResource(id = lbl_setting)
    val items: List<String> = listOf(about, save, setting)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 80.dp, right = 20.dp)
    ) {
        AnimatedVisibility(showDialog.value) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { showDialog.value = false },
                modifier = Modifier.width(120.dp),
                containerColor = BACKGROUND_COLOR,
                border = BorderStroke(0.1.dp, ON_BACKGROUND_COLOR),
            ) {
                items.forEach { element ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = element,
                                color = ON_BACKGROUND_COLOR,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        onClick = {
                            showDialog.value = false
                            navController.navigate(
                                route = when (element) {
                                    about -> AboutScreen.name
                                    save -> SaveScreen.name
                                    else -> SettingScreen.name
                                }
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = when (element) {
                                    about -> Default.Info
                                    save -> Default.BookmarkAdded
                                    else -> Default.Settings
                                }, contentDescription = "Leading Icon",
                                tint = ON_BACKGROUND_COLOR
                            )
                        }
                    )
                }
            }
        }
    }
}