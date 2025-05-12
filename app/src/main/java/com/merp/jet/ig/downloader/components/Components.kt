package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val BACKGROUND_COLOR: Color @Composable get() = MaterialTheme.colorScheme.background
val ON_BACKGROUND_COLOR: Color @Composable get() = MaterialTheme.colorScheme.onBackground

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier, strokeWidth: Dp = 3.dp) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        color = ON_BACKGROUND_COLOR,
        strokeWidth = strokeWidth
    )
}

@Composable
fun HorizontalSpace(dp: Dp = 16.dp) {
    Spacer(Modifier.height(dp))
}

@Composable
fun Divider(thickness: Dp = 0.4.dp) {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = thickness,
        color = ON_BACKGROUND_COLOR
    )
}