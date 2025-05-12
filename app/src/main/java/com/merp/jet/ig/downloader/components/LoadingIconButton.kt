package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class IconType {
    data class Vector(val imageVector: ImageVector) : IconType()
    data class PainterIcon(val painter: Painter) : IconType()
}

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        border = BorderStroke(
            ButtonDefaults.outlinedButtonBorder(enabled).width,
            ON_BACKGROUND_COLOR
        )
    ) {
        if (isLoading) {
            CircularProgressBar()
        } else {
            Text(text = text, color = ON_BACKGROUND_COLOR)
        }
    }
}

@Composable
fun LoadingIconButton(
    modifier: Modifier = Modifier,
    icon: IconType,
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(40.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressBar()
        } else {
            when (icon) {
                is IconType.Vector -> Icon(
                    icon.imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                is IconType.PainterIcon -> Icon(
                    icon.painter,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}