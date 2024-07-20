@file:OptIn(ExperimentalMaterial3Api::class)

package com.merp.jet.ig.downloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.merp.jet.ig.downloader.ui.theme.IGDownloaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IGDownloaderTheme {
                MyApp {
                    InstaReelDownloaderApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    content()
}


@Composable
@Preview(showBackground = true)
fun InstaPreview() {
    InstaReelDownloaderApp()
}

@Composable
fun InstaReelDownloaderApp() {
}