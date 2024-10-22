package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.navigation.InstaReelScreens

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun LoadingButton(text: String, enabled: Boolean, isLoading: Boolean, onclick: () -> Unit) {
    Button(onClick = onclick, enabled = enabled)
    {
        if (isLoading) {
            CircularProgressBar()
        } else {
            Text(text = text, color = MaterialTheme.colorScheme.background)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopActionBar(
    title: String = stringResource(R.string.app_name),
    isMainScreen: Boolean = true,
    icon: ImageVector = Icons.Default.Favorite,
    navController: NavController,
    onBackPressed: () -> Unit = {}
) {
    val backgroundColor: Color = MaterialTheme.colorScheme.background
    val onBackgroundColor: Color = MaterialTheme.colorScheme.onBackground
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog, navController)
    }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(
                    onClick = {
                        onBackPressed()
                    },
                    enabled = !isMainScreen
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = onBackgroundColor
                    )
                }
                Text(text = title, color = onBackgroundColor, fontWeight = FontWeight.SemiBold)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(backgroundColor),
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Icon",
                        tint = onBackgroundColor
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
    val expanded by remember {
        mutableStateOf(showDialog)
    }
    val items = listOf(
        stringResource(R.string.lbl_about),
        stringResource(R.string.lbl_save),
        stringResource(R.string.lbl_setting)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 80.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
            modifier = Modifier
                .width(120.dp)
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            items.forEach { element ->
                DropdownMenuItem(text = {
                    Text(
                        text = element,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                    onClick = {
                        expanded.value = false
                        showDialog.value = false
                        navController.navigate(
                            route = when (element) {
                                "About" -> InstaReelScreens.AboutScreen.name
                                "Save" -> InstaReelScreens.SaveScreen.name
                                else -> InstaReelScreens.SettingScreen.name
                            }
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (element) {
                                stringResource(R.string.lbl_about) -> Icons.Default.Info
                                stringResource(R.string.lbl_save) -> Icons.Default.Favorite
                                else -> Icons.Default.Settings
                            }, contentDescription = "Leading Icon",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ScreenDefault(
    title: String = stringResource(R.string.app_name),
    isMainScreen: Boolean = true,
    icon: ImageVector = Icons.Default.Favorite,
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
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            screenContent()
        }
    }
}