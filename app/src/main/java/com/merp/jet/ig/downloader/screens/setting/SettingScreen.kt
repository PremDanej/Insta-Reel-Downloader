package com.merp.jet.ig.downloader.screens.setting

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.LiveHelp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Translate
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.string.app_name
import com.merp.jet.ig.downloader.R.string.lbl_about
import com.merp.jet.ig.downloader.R.string.lbl_change_download_location
import com.merp.jet.ig.downloader.R.string.lbl_change_language
import com.merp.jet.ig.downloader.R.string.lbl_clear_data
import com.merp.jet.ig.downloader.R.string.lbl_dark_mode
import com.merp.jet.ig.downloader.R.string.lbl_dynamic_theme
import com.merp.jet.ig.downloader.R.string.lbl_faq
import com.merp.jet.ig.downloader.R.string.lbl_help
import com.merp.jet.ig.downloader.R.string.lbl_reset_setting
import com.merp.jet.ig.downloader.R.string.lbl_setting
import com.merp.jet.ig.downloader.R.string.lbl_setting_preference
import com.merp.jet.ig.downloader.R.string.lbl_setting_support
import com.merp.jet.ig.downloader.R.string.lbl_setting_theme
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.Divider
import com.merp.jet.ig.downloader.components.HorizontalSpace
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.AboutScreen

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
                .verticalScroll(rememberScrollState())
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
            CategoryLabelWithDivider(lbl_setting_preference)
            SubCategory(Icons.Outlined.DeleteForever, lbl_clear_data)
            SubCategory(Icons.Outlined.RestartAlt, lbl_reset_setting)
            SubCategory(Icons.Outlined.Translate, lbl_change_language)
            SubCategory(Icons.Outlined.Folder, lbl_change_download_location)
            CategoryLabelWithDivider(lbl_setting_support)
            SubCategory(Icons.Outlined.SupportAgent, lbl_setting_support)
            SubCategory(Icons.AutoMirrored.Outlined.HelpOutline, lbl_help)
            SubCategory(Icons.AutoMirrored.Outlined.LiveHelp, lbl_faq)
            SubCategory(Icons.Outlined.Info, lbl_about){
                navController.navigate(AboutScreen.name)
            }
        }
    }
}

@Composable
private fun CategoryLabelText(@StringRes resId: Int = app_name) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
        text = stringResource(id = resId),
        style = MaterialTheme.typography.bodySmall // try - titleMedium, bodyLarge
            .copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
private fun CategoryLabelWithDivider(@StringRes resId: Int = app_name) {
    HorizontalSpace(10.dp)
    Divider()
    HorizontalSpace(20.dp)
    CategoryLabelText(resId)
}

@Composable
private fun SwitchIcon(icon: ImageVector) {
    Icon(
        imageVector = icon,
        contentDescription = "Icon",
        modifier = Modifier.size(SwitchDefaults.IconSize)
    )
}

@Composable
private fun ChangeDynamicTheme(checked: Boolean, onClick: () -> Unit = {}) {
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
                        SwitchIcon(Icons.Filled.Check)
                    } else {
                        SwitchIcon(Icons.Filled.Close)
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
private fun ChangeTheme(checked: Boolean, onClick: () -> Unit = {}) {
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
                        SwitchIcon(icon = Icons.Filled.DarkMode)
                    } else {
                        SwitchIcon(icon = Icons.Filled.LightMode)
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
private fun SubCategory(
    icon: ImageVector? = null,
    @StringRes resId: Int = app_name,
    onClick: () -> Unit = {}
) {
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
                icon?.let {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = ON_BACKGROUND_COLOR
                    )
                }
            }
            Text(
                text = stringResource(id = resId),
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            )
        }
    }
}