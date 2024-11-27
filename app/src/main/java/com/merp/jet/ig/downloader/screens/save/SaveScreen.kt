package com.merp.jet.ig.downloader.screens.save

import android.content.ClipData
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.AutoMirrored.Default
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.LoadingIconButton
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.components.VideoCard
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.utils.Utils.downloadReel
import com.merp.jet.ig.downloader.utils.Utils.showToast

@Composable
fun SaveScreen(navController: NavController, viewModel: SaveViewModel = hiltViewModel()) {
    ScreenDefault(
        title = stringResource(id = R.string.lbl_save),
        isMainScreen = false,
        icon = Default.ArrowBack,
        navController = navController,
        onBackPressed = { navController.popBackStack() }
    ) {
        ScreenContent(viewModel = viewModel)
    }
}

@Composable
fun ScreenContent(viewModel: SaveViewModel) {
    val list = viewModel.savedList.collectAsState().value
    val clipboardManager = LocalClipboardManager.current
    val context: Context = LocalContext.current

    if (list.isEmpty()) {
        EmptyDataSet()
    } else {
        Column(
            Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            list.forEach { element ->
                SaveDataRow(
                    reelResponse = element,
                    onCopyAction = {
                        clipboardManager.setClip(ClipEntry(ClipData.newPlainText("text",element.url)))
                        showToast(context, "Link Copied")
                    },
                    onDeleteAction = {
                        viewModel.deleteSaveReel(element)
                        showToast(context, "Video Deleted")
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyDataSet() {
    Column(
        Modifier
            .fillMaxSize()
            .background(BACKGROUND_COLOR)
            .padding(bottom = 100.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.empty_data),
            contentDescription = "NotFound",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Data is Empty",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
            color = ON_BACKGROUND_COLOR
        )
    }
}

@Composable
fun SaveDataRow(
    reelResponse: ReelResponse,
    onCopyAction: () -> Unit,
    onDeleteAction: () -> Unit
) {
    val context = LocalContext.current
    var isDownloading by rememberSaveable { mutableStateOf(false) }

    VideoCard(reelResponse = reelResponse)
    {
        LoadingIconButton(
            icon = Filled.Link,
            enabled = true,
            isLoading = false,
            onclick = onCopyAction
        )
        LoadingIconButton(
            icon = Filled.Download,
            enabled = !isDownloading,
            isLoading = isDownloading
        ) {
            isDownloading = true
            downloadReel(reelResponse.medias[0].url, context) { isDownloading = false }
        }
        LoadingIconButton(
            icon = Filled.Delete,
            enabled = true,
            isLoading = false,
            onclick = onDeleteAction
        )
    }
}
