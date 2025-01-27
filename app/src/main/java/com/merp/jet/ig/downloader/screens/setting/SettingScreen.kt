package com.merp.jet.ig.downloader.screens.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.string.app_name
import com.merp.jet.ig.downloader.R.string.lbl_app_info
import com.merp.jet.ig.downloader.R.string.lbl_change_download_location
import com.merp.jet.ig.downloader.R.string.lbl_change_language
import com.merp.jet.ig.downloader.R.string.lbl_clear_data
import com.merp.jet.ig.downloader.R.string.lbl_confirm_delete
import com.merp.jet.ig.downloader.R.string.lbl_dark_mode
import com.merp.jet.ig.downloader.R.string.lbl_default_language
import com.merp.jet.ig.downloader.R.string.lbl_delete
import com.merp.jet.ig.downloader.R.string.lbl_delete_no
import com.merp.jet.ig.downloader.R.string.lbl_delete_yes
import com.merp.jet.ig.downloader.R.string.lbl_dynamic_theme
import com.merp.jet.ig.downloader.R.string.lbl_faq
import com.merp.jet.ig.downloader.R.string.lbl_help
import com.merp.jet.ig.downloader.R.string.lbl_reset_setting
import com.merp.jet.ig.downloader.R.string.lbl_setting
import com.merp.jet.ig.downloader.R.string.lbl_setting_preference
import com.merp.jet.ig.downloader.R.string.lbl_setting_support
import com.merp.jet.ig.downloader.R.string.lbl_setting_theme
import com.merp.jet.ig.downloader.R.string.lbl_video_delete
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.Divider
import com.merp.jet.ig.downloader.components.HorizontalSpace
import com.merp.jet.ig.downloader.components.LoadingButton
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.navigation.InstaReelScreens.AboutScreen
import com.merp.jet.ig.downloader.utils.Constants.DEFAULT_EN_LOCALE_CODE
import com.merp.jet.ig.downloader.utils.Constants.HINDI_LOCALE_CODE
import com.merp.jet.ig.downloader.utils.Utils.setAppLanguage
import com.merp.jet.ig.downloader.utils.Utils.showToast

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
        val viewModel: SettingViewModel = hiltViewModel<SettingViewModel>()
        ScreenContent(viewModel, navController, isDark, isDynamicColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    viewModel: SettingViewModel,
    navController: NavController,
    isDark: MutableState<Boolean>,
    isDynamicColor: MutableState<Boolean>
) {
    val columnAnimation = remember { Animatable(0.9f) }
    LaunchedEffect(true) {
        columnAnimation.animateTo(1f, tween(300))
    }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val dialogOpen = remember {
        mutableStateOf(false)
    }.apply {
        if (value) DeleteDialog(
            dialogTitle = context.getString(lbl_delete),
            dialogText = context.getString(lbl_confirm_delete),
            onDismissRequest = { value = false },
            onConfirmation = {
                viewModel.deleteAllSavedReel()
                showToast(context, context.getString(lbl_video_delete))
                value = false
            },
        )
    }

    if (showBottomSheet) {
        AppLanguageBottomSheet(
            sheetState = sheetState,
            selectedLanguage = viewModel.getLanguage() ?: DEFAULT_EN_LOCALE_CODE,
            onDismissRequest = { showBottomSheet = false },
        ){ languageCode ->
            viewModel.setLanguage(languageCode)
            setAppLanguage(context,languageCode)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .scale(columnAnimation.value)
            .blur(if (dialogOpen.value) 5.dp else 0.dp)
    ) {
        HorizontalSpace(20.dp)

        // For Theme

        CategoryLabelText(lbl_setting_theme)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ChangeDynamicTheme(checked = isDynamicColor.value) {
                isDynamicColor.value = !isDynamicColor.value
            }
        }

        ChangeTheme(checked = isDark.value) { isDark.value = !isDark.value }

        // For Preferences

        CategoryLabelWithDivider(lbl_setting_preference)

        SubCategory(Icons.Outlined.DeleteForever, lbl_clear_data) {
            dialogOpen.value = !dialogOpen.value
        }

        SubCategory(Icons.Outlined.RestartAlt, lbl_reset_setting) {
            viewModel.setLanguage(DEFAULT_EN_LOCALE_CODE)
            setAppLanguage(context, DEFAULT_EN_LOCALE_CODE)
        }

        SubCategory(
            icon = Icons.Outlined.Translate,
            resId = lbl_change_language,
            extraValue = context.getString(lbl_default_language)
        ) {
            showBottomSheet = true
        }

        SubCategory(Icons.Outlined.Folder, lbl_change_download_location, "InstaReels")

        // For App Support

        CategoryLabelWithDivider(lbl_setting_support)

        SubCategory(Icons.Outlined.SupportAgent, lbl_setting_support) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                setData(Uri.parse("mailto:"))
                putExtra(Intent.EXTRA_EMAIL, arrayOf("prem.dev@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Regarding IG Downloader app")
            }
            context.startActivity(intent)
        }

        SubCategory(Icons.AutoMirrored.Outlined.HelpOutline, lbl_help)

        SubCategory(Icons.AutoMirrored.Outlined.LiveHelp, lbl_faq)

        SubCategory(Icons.Outlined.Info, lbl_app_info) {
            navController.navigate(AboutScreen.name)
        }
    }
}

@Composable
private fun CategoryLabelText(@StringRes resId: Int = app_name) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
            .alpha(0.6f),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
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
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
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
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
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
    extraValue: String? = null,
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier.clickable {
        onClick()
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
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
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )
            extraValue?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                        .copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .alpha(0.6f)
                )
            }
        }
    }
}

@Composable
fun DeleteDialog(
    dialogTitle: String,
    dialogText: String,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    val dialogAnimation = remember { Animatable(0f) }
    LaunchedEffect(true) {
        dialogAnimation.animateTo(1f, tween(300))
    }
    AlertDialog(
        containerColor = BACKGROUND_COLOR,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .scale(dialogAnimation.value)
            .border(0.1.dp, ON_BACKGROUND_COLOR, RoundedCornerShape(12)),
        shape = RoundedCornerShape(12),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        title = {
            Text(
                text = dialogTitle,
                color = ON_BACKGROUND_COLOR,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = dialogText,
                color = ON_BACKGROUND_COLOR,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.alpha(0.7f)
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Button(
                onClick = { onConfirmation() },
                colors = ButtonDefaults.buttonColors(ON_BACKGROUND_COLOR),
            ) {
                Text(
                    stringResource(id = lbl_delete_yes),
                    color = BACKGROUND_COLOR
                )
            }
        },
        dismissButton = {
            LoadingButton(
                text = stringResource(id = lbl_delete_no),
                enabled = true,
                isLoading = false
            ) {
                onDismissRequest()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguageBottomSheet(
    sheetState: SheetState,
    selectedLanguage: String? = null,
    onDismissRequest: () -> Unit = {},
    onLanguageChange: (String) -> Unit = {}
) {
    ModalBottomSheet(
        modifier = Modifier
            .padding(top = 50.dp),
        scrimColor = ON_BACKGROUND_COLOR.copy(0.3f),
        sheetState = sheetState,
        containerColor = BACKGROUND_COLOR,
        onDismissRequest = { onDismissRequest() }
    ) {

        val radioLanguages = listOf(
            listOf("English", DEFAULT_EN_LOCALE_CODE),
            listOf("Hindi", HINDI_LOCALE_CODE),
        )
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(selectedLanguage)
        }

        Text(
            text = "App Language",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalSpace()
        Divider()
        HorizontalSpace()

        radioLanguages.forEach { language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (language.last() == selectedOption),
                        onClick = {
                            onOptionSelected(language.last())
                            onLanguageChange(language.last())
                            onDismissRequest()
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(10.dp, 5.dp),
                    selected = language.last() == selectedOption,
                    onClick = {
                        onOptionSelected(language.last())
                        onLanguageChange(language.last())
                        onDismissRequest()
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = ON_BACKGROUND_COLOR,
                        unselectedColor = ON_BACKGROUND_COLOR.copy(0.5f)
                    )
                )

                Column {
                    Text(text = language[0])
                    Text(
                        modifier = Modifier.alpha(0.7f),
                        text = language[1],
                        style = MaterialTheme.typography.bodySmall
                            .copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
            HorizontalSpace(10.dp)
        }
        HorizontalSpace()
    }
}