package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun LoadingButton(text: String, enabled: Boolean, isLoading: Boolean, onclick: () -> Unit) {
    Button(onClick = onclick ,enabled = enabled)
    {
        if (isLoading) {
            CircularProgressBar()
        } else {
            Text(text = text)
        }
    }
}