package com.merp.jet.ig.downloader.screens.reel

import android.content.Context
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.LoadingButton
import com.merp.jet.ig.downloader.components.ScreenDefault

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
    var videoLink by remember { mutableStateOf("") }

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
            }
        }
    }
}


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
