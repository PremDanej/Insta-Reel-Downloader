package com.merp.jet.ig.downloader.screens.reel

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.LoadingButton
import com.merp.jet.ig.downloader.components.ScreenDefault
import com.merp.jet.ig.downloader.model.ReelResponse
import java.io.ByteArrayInputStream

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

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = videoLink,
            onValueChange = { videoLink = it },
            label = { Text(text = "Reel Link") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            keyboardActions = KeyboardActions {
                keyboard?.hide()
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoadingButton(
            text = stringResource(R.string.lbl_get),
            enabled = !viewModel.isLoading,
            isLoading = viewModel.isLoading
        ) {
            if (videoLink.isEmpty() || !URLUtil.isValidUrl(videoLink)) {
                showToast(context, message = "Please enter a valid link")
            } else if (videoLink.contains("https://www.instagram.com/reel/")) {
                viewModel.isLoading = true
                viewModel.getReelData(videoLink)
                viewModel.reelResponse.observe(owner) {
                    reelResponse.value = it
                    isDownloadable = true
                }
            } else showToast(context, "Enter valid reel link")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isDownloadable && !viewModel.isLoading) {
            if (!viewModel.isError) {
                reelResponse.value?.let { ReelDownloading(reelResponse = it) }
            } else {
                showToast(context, message = "Invalid link! Please try again")
                isDownloadable = false
            }
        }
    }
}

@Composable
fun ReelDownloading(reelResponse: ReelResponse) {

    var isDownloading by rememberSaveable { mutableStateOf(false) }
    val context: Context = LocalContext.current
    val imageUrl = reelResponse.thumbnail.split(',')[1]
    val media = reelResponse.medias[0]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 10.dp)
    ) {
        Image(
            bitmap = convertBase64ToBitmap(imageUrl),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(120.dp).width(90.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 2.dp)
        ) {
            Text(
                text = reelResponse.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Text(
                text = "Quality: ${media.quality}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
            )
            Text(
                text = "Size: ${media.formattedSize}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
            )
            LoadingButton(
                text = stringResource(R.string.lbl_download_now),
                enabled = !isDownloading,
                isLoading = isDownloading
            ) {
                isDownloading = true
            }
        }
    }
}


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun convertBase64ToBitmap(base65String: String): ImageBitmap {
    val byteArray = Base64.decode(base65String, Base64.DEFAULT)
    return BitmapFactory.decodeStream(ByteArrayInputStream(byteArray)).asImageBitmap()
}