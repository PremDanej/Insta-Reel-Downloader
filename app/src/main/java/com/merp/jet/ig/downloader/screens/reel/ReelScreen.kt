package com.merp.jet.ig.downloader.screens.reel

import android.app.Activity
import android.content.Context
import android.webkit.URLUtil
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.string.app_name
import com.merp.jet.ig.downloader.R.string.lbl_enter_valid_reel_link
import com.merp.jet.ig.downloader.R.string.lbl_go
import com.merp.jet.ig.downloader.R.string.lbl_invalid_link_please_try_again
import com.merp.jet.ig.downloader.R.string.lbl_paste
import com.merp.jet.ig.downloader.R.string.lbl_reel_link
import com.merp.jet.ig.downloader.R.string.lbl_remove_successfully
import com.merp.jet.ig.downloader.R.string.lbl_save_successfully
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.HorizontalSpace
import com.merp.jet.ig.downloader.components.LoadingButton
import com.merp.jet.ig.downloader.components.LoadingIconButton
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.components.VideoCard
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.utils.Constants
import com.merp.jet.ig.downloader.utils.Utils.downloadReel
import com.merp.jet.ig.downloader.utils.Utils.showToast

@Composable
fun ReelScreen(navController: NavController, viewModel: ReelViewModel = hiltViewModel()) {
    ScreenDefault(
        title = stringResource(app_name),
        isMainScreen = true,
        navController = navController
    ) {
        ScreenContent(viewModel = viewModel)
    }
}

@Composable
fun ScreenContent(viewModel: ReelViewModel) {

    val context = LocalContext.current
    val intent = (context as? Activity)?.intent
    LaunchedEffect(intent) { viewModel.processIntent(intent) }
    val keyboard = LocalSoftwareKeyboardController.current
    val owner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val clipboardManager = LocalClipboardManager.current
    val annotatedString = clipboardManager.getText()
    val videoLink by viewModel.reelLink.collectAsState()
    var isDownloadable by remember { mutableStateOf(false) }
    val reelResponse = remember { mutableStateOf<ReelResponse?>(null) }
    val columnAnimation = remember { Animatable(0f) }
    LaunchedEffect(true) {
        columnAnimation.animateTo(1f)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(BACKGROUND_COLOR)
            .padding(bottom = 100.dp)
            .scale(columnAnimation.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 10.dp),
            value = videoLink,
            onValueChange = { newValue -> viewModel.updateReelLink(newValue) },
            placeholder = { Text(text = stringResource(lbl_reel_link)) },
            keyboardActions = KeyboardActions(onDone = {
                keyboard?.hide()
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(50),
            maxLines = 1,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ON_BACKGROUND_COLOR,
                unfocusedBorderColor = ON_BACKGROUND_COLOR,
                focusedTextColor = ON_BACKGROUND_COLOR,
                unfocusedTextColor = ON_BACKGROUND_COLOR,
                focusedPlaceholderColor = ON_BACKGROUND_COLOR,
                unfocusedPlaceholderColor = ON_BACKGROUND_COLOR,
                focusedTrailingIconColor = ON_BACKGROUND_COLOR,
                unfocusedTrailingIconColor = ON_BACKGROUND_COLOR,
            ),
            trailingIcon = {
                if (videoLink.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier.size(26.dp),
                        onClick = { viewModel.updateReelLink("") }) {
                        Icon(
                            imageVector = Filled.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            }
        )

        HorizontalSpace()

        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { viewModel.updateReelLink(annotatedString?.text?.trim() ?: "") },
                modifier = Modifier.fillMaxWidth(0.5f).padding(end = 5.dp),
                colors = ButtonDefaults.buttonColors(ON_BACKGROUND_COLOR),
            ) {
                Text(text = stringResource(lbl_paste), color = BACKGROUND_COLOR)
            }

            LoadingButton(
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                text = stringResource(lbl_go),
                enabled = !viewModel.isLoading,
                isLoading = viewModel.isLoading
            ) {
                if (videoLink.isEmpty() || !URLUtil.isValidUrl(videoLink)) {
                    showToast(context, message = context.getString(lbl_enter_valid_reel_link))
                } else if (videoLink.contains(Constants.INSTA_REEL)) {
                    viewModel.isSaved = false
                    viewModel.getSaveReelByUrl(videoLink)
                    viewModel.isLoading = true
                    viewModel.getReelData(videoLink)
                    viewModel.reelResponse.observe(owner) {
                        reelResponse.value = it
                        isDownloadable = true
                    }
                } else showToast(context, context.getString(lbl_enter_valid_reel_link))
            }
        }

        HorizontalSpace()

        if (isDownloadable && !viewModel.isLoading) {
            if (!viewModel.isError) {
                reelResponse.value?.let { reelResponse ->
                    ReelDownloaderCard(reelResponse, viewModel.isSaved) {
                        if (viewModel.isSaved) {
                            viewModel.deleteSaveReel(reelResponse)
                            viewModel.isSaved = false
                            showToast(context, context.getString(lbl_remove_successfully))
                        } else {
                            viewModel.saveReel(reelResponse)
                            viewModel.isSaved = true
                            showToast(context, context.getString(lbl_save_successfully))
                        }
                    }
                }
            } else {
                showToast(context, message = stringResource(lbl_invalid_link_please_try_again))
                isDownloadable = false
            }
        }
    }
}

@Composable
fun ReelDownloaderCard(
    reelResponse: ReelResponse,
    isSaved: Boolean,
    onSaveAction: () -> Unit
) {

    val context: Context = LocalContext.current
    var isDownloading by rememberSaveable { mutableStateOf(false) }

    VideoCard(reelResponse = reelResponse) {
        LoadingIconButton(
            icon = Filled.SaveAlt,
            enabled = !isDownloading,
            isLoading = isDownloading
        ) {
            isDownloading = true
            downloadReel(reelResponse.medias[0].url, context) { isDownloading = false }
        }

        LoadingIconButton(
            icon = if (isSaved) Filled.BookmarkAdded else Filled.BookmarkBorder,
            enabled = true,
            isLoading = false
        ) {
            onSaveAction()
        }
    }
}