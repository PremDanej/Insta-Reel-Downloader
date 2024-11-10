package com.merp.jet.ig.downloader.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.string.lbl_dark_mode
import com.merp.jet.ig.downloader.R.string.lbl_light_mode
import com.merp.jet.ig.downloader.R.string.lbl_setting
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault

@Composable
fun SettingScreen(
    navController: NavController,
    isDark: MutableState<Boolean>
) {
    ScreenDefault(
        title = stringResource(lbl_setting),
        isMainScreen = false,
        icon = Icons.AutoMirrored.Default.ArrowBack,
        navController = navController,
        onBackPressed = { navController.popBackStack() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            ChangeTheme(checked = isDark) {
                isDark.value = it
            }
        }

    }
}

@Composable
fun ThemeOption(modifier: Modifier = Modifier) {
    val themeOption = listOf(
        "System default",
        stringResource(lbl_light_mode),
        stringResource(lbl_dark_mode)
    )
    val (selectOption, onOptionSelected) = remember {
        mutableStateOf(themeOption[0])
    }

    Column {
        themeOption.forEach { value ->
            Row(modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Text(text = value, color = MaterialTheme.colorScheme.onBackground)
                RadioButton(
                    selected = value == selectOption, onClick = { onOptionSelected(value) },
                    colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}

@Composable
fun ChangeTheme(checked: MutableState<Boolean>, onClick: (Boolean) -> Unit = {}) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Text(text = if(checked.value) "Turn off dark mode" else "Turn on dark mode")
        Switch(
            checked = checked.value,
            onCheckedChange = {
                onClick(it)
            },
            thumbContent = {
                if (checked.value) {
                    Icon(
                        imageVector = Icons.Filled.DarkMode,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.LightMode,
                        contentDescription = null,
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