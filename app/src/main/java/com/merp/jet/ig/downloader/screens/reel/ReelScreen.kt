package com.merp.jet.ig.downloader.screens.reel

import android.content.Context
import android.webkit.URLUtil
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.HorizontalSpace
import com.merp.jet.ig.downloader.components.LoadingButton
import com.merp.jet.ig.downloader.components.LoadingIconButton
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.components.VideoCard
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.utils.Utils.downloadReel
import com.merp.jet.ig.downloader.utils.Utils.showToast

@Composable
fun ReelScreen(navController: NavController, viewModel: ReelViewModel = hiltViewModel()) {
    ScreenDefault(
        title = stringResource(R.string.app_name),
        isMainScreen = true,
        navController = navController
    ) {
        ScreenContent(viewModel = viewModel)
    }
}

@Composable
fun ScreenContent(viewModel: ReelViewModel) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    val owner = LocalLifecycleOwner.current
    var videoLink by remember { mutableStateOf("") }
    var isDownloadable by remember { mutableStateOf(false) }
    val reelResponse = remember { mutableStateOf<ReelResponse?>(null) }
    var isSaved by remember { mutableStateOf(viewModel.isDataEmpty()) }
    val focusManager = LocalFocusManager.current
    val clipboardManager = LocalClipboardManager.current
    val annotatedString = clipboardManager.getText()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
                    .padding(end = 5.dp),
                value = videoLink,
                onValueChange = { videoLink = it.trim() },
                placeholder = { Text(text = "Reel Link") },
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
            )

            LoadingIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                enabled = !viewModel.isLoading,
                isLoading = viewModel.isLoading
            ) {
                if (videoLink.isEmpty() || !URLUtil.isValidUrl(videoLink)) {
                    showToast(context, message = "Please enter a valid link")
                } else if (videoLink.contains("https://www.instagram.com/reel/")) {
                    viewModel.saveReelResponse.clear()
                    viewModel.getSaveReelByUrl(videoLink)
                    viewModel.isLoading = true
                    viewModel.getReelData(videoLink)
                    viewModel.reelResponse.observe(owner) {
                        reelResponse.value = it
                        isDownloadable = true
                    }
                } else showToast(context, "Enter valid reel link")
            }
        }

        HorizontalSpace()

        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if(annotatedString != null) {
                    // The pasted text is placed on the tail of the TextField
                    videoLink += annotatedString
                }
            },
                modifier = Modifier.fillMaxWidth(0.5f).padding(end = 5.dp)
                ) {
                Text(text = "Paste", color = BACKGROUND_COLOR)
            }

            LoadingButton(
                modifier = Modifier.fillMaxWidth(1f).padding(5.dp),
                text = "Paste & Go",
                enabled = !viewModel.isLoading,
                isLoading = viewModel.isLoading
            ) {
                if(annotatedString != null) {
                    // The pasted text is placed on the tail of the TextField
                    videoLink += annotatedString

                    if (videoLink.isEmpty() || !URLUtil.isValidUrl(videoLink)) {
                        showToast(context, message = "Please enter a valid link")
                    } else if (videoLink.contains("https://www.instagram.com/reel/")) {
                        viewModel.saveReelResponse.clear()
                        viewModel.getSaveReelByUrl(videoLink)
                        viewModel.isLoading = true
                        viewModel.getReelData(videoLink)
                        viewModel.reelResponse.observe(owner) {
                            reelResponse.value = it
                            isDownloadable = true
                        }
                    } else showToast(context, "Enter valid reel link")
                }
            }
        }

        HorizontalSpace()

        if (isDownloadable && !viewModel.isLoading) {
            if (!viewModel.isError) {
                reelResponse.value?.let { reelResponse ->
                    ReelDownloaderCard(reelResponse, isSaved) {
                        if (isSaved) {
                            viewModel.deleteSaveReel(reelResponse)
                            isSaved = false
                            showToast(context, "Remove Successfully")
                        } else {
                            viewModel.saveReel(reelResponse)
                            isSaved = true
                            showToast(context, "Save Successfully")
                        }
                        viewModel.saveReelResponse.clear()
                    }
                }
            } else {
                showToast(context, message = "Invalid link! Please try again")
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
            icon = Filled.Download,
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