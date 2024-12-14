package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

val BACKGROUND_COLOR: Color @Composable get() = MaterialTheme.colorScheme.background
val ON_BACKGROUND_COLOR: Color @Composable get() = MaterialTheme.colorScheme.onBackground

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        color = ON_BACKGROUND_COLOR,
        strokeWidth = 3.dp
    )
}

@Composable
fun HorizontalSpace(modifier: Modifier = Modifier) {
    Spacer(modifier.height(16.dp))
}

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    isLoading: Boolean,
    onclick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onclick,
        enabled = enabled,
        border = BorderStroke(
            ButtonDefaults.outlinedButtonBorder(enabled).width,
            ON_BACKGROUND_COLOR
        ),
    )
    {
        if (isLoading) {
            CircularProgressBar()
        } else {
            Text(text = text, color = ON_BACKGROUND_COLOR)
        }
    }
}

@Composable
fun LoadingIconButton(
    icon: ImageVector,
    enabled: Boolean,
    isLoading: Boolean,
    onclick: () -> Unit
) {
    IconButton(onClick = onclick, enabled = enabled)
    {
        if (isLoading) {
            CircularProgressBar()
        } else {
            Icon(icon, null)
        }
    }
}

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
    val items = listOf(
        stringResource(lbl_about),
        stringResource(lbl_save),
        stringResource(lbl_setting)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 80.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = showDialog.value,
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
                                "About" -> AboutScreen.name
                                "Save" -> SaveScreen.name
                                else -> SettingScreen.name
                            }
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (element) {
                                stringResource(lbl_about) -> Default.Info
                                stringResource(lbl_save) -> Default.Favorite
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
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.4.dp,
                    color = ON_BACKGROUND_COLOR
                )
            }
            screenContent()
        }
    }
}