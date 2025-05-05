package com.merp.jet.ig.downloader.screens.save

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.AutoMirrored.Default
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R.drawable.empty_data
import com.merp.jet.ig.downloader.R.drawable.ic_instagram
import com.merp.jet.ig.downloader.R.string.lbl_save
import com.merp.jet.ig.downloader.R.string.lbl_video_not_saved
import com.merp.jet.ig.downloader.R.string.lbl_instagram_not_installed
import com.merp.jet.ig.downloader.R.string.lbl_link_copied
import com.merp.jet.ig.downloader.R.string.lbl_video_deleted
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.CircularProgressBar
import com.merp.jet.ig.downloader.components.LoadingIconButton
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.components.VideoCard
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.utils.Utils.downloadReel
import com.merp.jet.ig.downloader.utils.Utils.showToast

@Composable
fun SaveScreen(navController: NavController) {
    ScreenDefault(
        title = stringResource(id = lbl_save),
        isMainScreen = false,
        icon = Default.ArrowBack,
        navController = navController,
        onBackPressed = { navController.popBackStack() }
    ) {
        val viewModel: SaveViewModel = hiltViewModel<SaveViewModel>()
        ScreenContent(viewModel = viewModel)
    }
}

@Composable
fun ScreenContent(viewModel: SaveViewModel) {

    LaunchedEffect(Unit) { viewModel.getSaveReels() }

    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current
    val context: Context = LocalContext.current

    when{
        uiState.isLoading -> LoadingView()
        uiState.reels.isNotEmpty() -> ReelListView(
            reelList = uiState.reels,
            clipboardManager = clipboardManager,
            context = context,
            onDelete = { viewModel.deleteSaveReel(it)}
        )
        else -> EmptyDataSet()
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressBar(
            modifier = Modifier.size(40.dp),
            strokeWidth = 2.dp
        )
    }
}


@Composable
private fun ReelListView(
    reelList: List<ReelResponse>,
    clipboardManager: ClipboardManager,
    context: Context,
    onDelete: (ReelResponse) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = reelList,
            key = { it.thumbnail }
        ) { reel ->
            SaveDataRow(
                reelResponse = reel,
                onCopyAction = {
                    clipboardManager.setClip(ClipEntry(ClipData.newPlainText("text", reel.url)))
                    showToast(context, context.getString(lbl_link_copied))
                },
                onDeleteAction = {
                    onDelete(reel)
                    showToast(context, context.getString(lbl_video_deleted))
                }
            )
        }
    }
}

@Composable
fun EmptyDataSet() {
    val columnAnimation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        columnAnimation.animateTo(1f,tween(200))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_COLOR)
            .padding(bottom = 100.dp)
            .scale(columnAnimation.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(empty_data),
            contentDescription = "NotFound",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = lbl_video_not_saved),
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
    var isDownloading by rememberSaveable { mutableStateOf(false) }

    VideoCard(reelResponse = reelResponse) {
        ActionButtons(
            reelResponse = reelResponse,
            isDownloading = isDownloading,
            onCopy = onCopyAction,
            onDelete = onDeleteAction,
            onStartDownload = { isDownloading = true },
            onDownloadComplete = { isDownloading = false }
        )
    }
}

@Composable
private fun ActionButtons(
    reelResponse: ReelResponse,
    isDownloading: Boolean,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    onStartDownload: () -> Unit,
    onDownloadComplete: () -> Unit
) {
    val context = LocalContext.current

    LoadingIconButton(
        modifier = Modifier.rotate(-45f),
        icon = Filled.Link,
        enabled = !isDownloading,
        isLoading = false,
        onclick = onCopy
    )
    LoadingIconButton(
        icon = Filled.SaveAlt,
        enabled = !isDownloading,
        isLoading = isDownloading
    ) {
        onStartDownload()
        downloadReel(reelResponse.medias[0].url, context) {
            onDownloadComplete()
        }
    }
    LoadingIconButton(
        icon = Filled.DeleteOutline,
        enabled = !isDownloading,
        isLoading = false,
        onclick = onDelete
    )
    LoadingIconButton(
        icon = painterResource(id = ic_instagram),
        enabled = !isDownloading,
        isLoading = false,
        onclick = {
            onInstaOpen(context, reelResponse.url)
        }
    )
}

fun onInstaOpen(context: Context, url: String){
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri).setPackage("com.instagram.android")
    if (isIntentAvailable(context, intent)) {
        context.startActivity(intent)
    } else {
        showToast(context, context.getString(lbl_instagram_not_installed))
    }
}

fun isIntentAvailable(context: Context, intent: Intent): Boolean {
    return try {
        context.packageManager.getPackageInfo(intent.`package` ?: "", PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}