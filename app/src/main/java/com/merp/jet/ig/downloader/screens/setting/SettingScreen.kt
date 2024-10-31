package com.merp.jet.ig.downloader.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Setting Screen")
            ChangeTheme(checked = isDark) {
                isDark.value = it
            }
        }
    }
}

@Preview
@Composable
fun ThemeOption(modifier: Modifier = Modifier) {
    val themeOption = listOf("System default", "Light", "Dark")
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
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Switch(
            checked = checked.value,
            onCheckedChange = {
                onClick(it)
            }
        )
    }
}