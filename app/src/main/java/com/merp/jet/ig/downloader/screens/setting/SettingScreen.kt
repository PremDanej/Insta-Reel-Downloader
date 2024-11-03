package com.merp.jet.ig.downloader.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
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
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.ScreenDefault

@Composable
fun SettingScreen(
    navController: NavController,
    isDark: MutableState<Boolean>
) {
    ScreenDefault(
        title = stringResource(id = R.string.lbl_setting),
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
        stringResource(R.string.lbl_light_mode),
        stringResource(R.string.lbl_dark_mode)
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
        val theme =
            if (checked.value)
                stringResource(id = R.string.lbl_light_mode)
            else
                stringResource(id = R.string.lbl_dark_mode)
        Text(text = "Turn to $theme mode")
        Switch(
            checked = checked.value,
            onCheckedChange = {
                onClick(it)
            }
        )
    }
}