package com.merp.jet.ig.downloader.screens.setting

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.string.lbl_dark_mode
import com.merp.jet.ig.downloader.R.string.lbl_dynamic_theme
import com.merp.jet.ig.downloader.R.string.lbl_setting
import com.merp.jet.ig.downloader.R.string.lbl_setting_theme
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.HorizontalSpace
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault

@Composable
fun SettingScreen(
    navController: NavController,
    isDark: MutableState<Boolean>,
    isDynamicColor: MutableState<Boolean>
) {
    ScreenDefault(
        title = stringResource(lbl_setting),
        isMainScreen = false,
        icon = Icons.AutoMirrored.Default.ArrowBack,
        navController = navController,
        onBackPressed = { navController.popBackStack() }
    ) {
        val columnAnimation = remember { Animatable(0.9f) }
        LaunchedEffect(true) {
            columnAnimation.animateTo(1f, tween(300))
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .background(BACKGROUND_COLOR)
                .scale(columnAnimation.value)
        ) {
            HorizontalSpace(20.dp)
            CategoryLabelText(lbl_setting_theme)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ChangeDynamicTheme(checked = isDynamicColor.value) {
                    isDynamicColor.value = !isDynamicColor.value
                }
            }
            ChangeTheme(checked = isDark.value) { isDark.value = !isDark.value }
        }
    }
}

@Composable
fun CategoryLabelText(@StringRes resId: Int) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
        text = stringResource(id = resId),
        style = MaterialTheme.typography.bodySmall // try - titleMedium, bodyLarge
            .copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
fun ChangeDynamicTheme(checked: Boolean, onClick: () -> Unit = {}) {
    Box(modifier = Modifier.clickable {
        onClick()
    }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { },
                enabled = false
            ) {
                Icon(
                    imageVector = Icons.Filled.Palette,
                    contentDescription = "Icon",
                    tint = ON_BACKGROUND_COLOR
                )
            }
            Text(
                text = stringResource(lbl_dynamic_theme),
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    onClick()
                },
                thumbContent = {
                    if (checked) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Check",
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Uncheck",
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = ON_BACKGROUND_COLOR,
                    uncheckedBorderColor = ON_BACKGROUND_COLOR,
                    checkedIconColor = ON_BACKGROUND_COLOR,
                    checkedTrackColor = ON_BACKGROUND_COLOR,
                    uncheckedTrackColor = BACKGROUND_COLOR,
                    checkedThumbColor = BACKGROUND_COLOR,
                    uncheckedThumbColor = ON_BACKGROUND_COLOR,
                )
            )
        }
    }
}

@Composable
fun ChangeTheme(checked: Boolean, onClick: () -> Unit = {}) {
    Box(modifier = Modifier.clickable {
        onClick()
    }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { },
                enabled = false
            ) {
                Icon(
                    imageVector = Icons.Filled.Contrast,
                    contentDescription = "Icon",
                    tint = ON_BACKGROUND_COLOR
                )
            }
            Text(
                text = stringResource(lbl_dark_mode),
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    onClick()
                },
                thumbContent = {
                    if (checked) {
                        Icon(
                            imageVector = Icons.Filled.DarkMode,
                            contentDescription = "DarkMode",
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.LightMode,
                            contentDescription = "LightMode",
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = ON_BACKGROUND_COLOR,
                    uncheckedBorderColor = ON_BACKGROUND_COLOR,
                    checkedIconColor = ON_BACKGROUND_COLOR,
                    checkedTrackColor = ON_BACKGROUND_COLOR,
                    uncheckedTrackColor = BACKGROUND_COLOR,
                    checkedThumbColor = BACKGROUND_COLOR,
                    uncheckedThumbColor = ON_BACKGROUND_COLOR,
                )
            )
        }
    }
}