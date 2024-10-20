package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.merp.jet.ig.downloader.R

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun LoadingButton(text: String, enabled: Boolean, isLoading: Boolean, onclick: () -> Unit) {
    Button(onClick = onclick, enabled = enabled)
    {
        if (isLoading) {
            CircularProgressBar()
        } else {
            Text(text = text, color = MaterialTheme.colorScheme.background)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopActionBar(
    title: String = stringResource(R.string.app_name),
    isMainScreen: Boolean = true,
    icon: ImageVector = Icons.Default.Favorite,
    onBackPressed: () -> Unit = {}
) {
    val backgroundColor: Color = MaterialTheme.colorScheme.background
    val onBackgroundColor: Color = MaterialTheme.colorScheme.onBackground

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(
                    onClick = {
                        onBackPressed()
                    },
                    enabled = !isMainScreen
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = onBackgroundColor
                    )
                }
                Text(text = title, color = onBackgroundColor, fontWeight = FontWeight.SemiBold)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(backgroundColor),
        actions = {
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Icon",
                    tint = onBackgroundColor
                )
            }
        }
    )
}