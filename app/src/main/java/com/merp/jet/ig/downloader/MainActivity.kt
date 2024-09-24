@file:OptIn(ExperimentalMaterial3Api::class)

package com.merp.jet.ig.downloader

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.merp.jet.ig.downloader.ui.theme.IGDownloaderTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

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

    val context = LocalContext.current
    val videoLink =
    "https://instagram.fhan5-8.fna.fbcdn.net/o1/v/t16/f2/m69/AQOlk0qNNKAChw5gXxYXyQAot3iux5Upc1h_Glc2jWzEwmNJ4bCwmIuLiTDlVGWFqYfkFn2a98GZX2a4OxSyA67_.mp4?stp=dst-mp4&efg=eyJxZV9ncm91cHMiOiJbXCJpZ193ZWJfZGVsaXZlcnlfdnRzX290ZlwiXSIsInZlbmNvZGVfdGFnIjoidnRzX3ZvZF91cmxnZW4uaWd0di5jMi43MjAuYmFzZWxpbmUifQ&_nc_cat=108&vs=806037847731366_1659554972&_nc_vs=HBksFQIYOnBhc3N0aHJvdWdoX2V2ZXJzdG9yZS9HQkU4VnhySjNwM0Mzbm9GQUxZUWRiRjVNQ0lTYnZWQkFBQUYVAALIAQAVAhg6cGFzc3Rocm91Z2hfZXZlcnN0b3JlL0dHQU4tUm1uMWw5YkRLWUJBS2Y3NmJmbmdPOHVidlZCQUFBRhUCAsgBACgAGAAbAYgHdXNlX29pbAExFQAAJtS32Zio645AFQIoAkMzLBdALd2yLQ5WBBgSZGFzaF9iYXNlbGluZV8xX3YxEQB17AcA&ccb=9-4&oh=00_AYDAIJvzx89LM2dKvBZ1ZQRlW4XP0Lmo4yammniPWjsEtQ&oe=66F49636&_nc_sid=10d13b"
    var reelLink by remember {
        mutableStateOf(TextFieldValue(videoLink))
    }
    var isDownloading by remember {
        mutableStateOf(false)
    }

    // Permission
    val storagePermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Storage Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Storage Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

    LaunchedEffect(Unit) {
        storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                OutlinedTextField(
                    value = reelLink, onValueChange = {
                        reelLink = it
                    },
                    label = { Text(text = "Reel Link") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isDownloading = true
                        //downloadPdfFromUrl(reelLink.text)
                        downloadReel(reelLink.text, context, onComplete = { isDownloading = false })
                    },
                    enabled = !isDownloading
                ) {
                    if (isDownloading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(text = "Download")
                    }
                }

            }

        }
    }
}

fun downloadReel(reelLink: String, context: Context, onComplete: () -> Unit) {
    if (reelLink.isEmpty()) {
        Toast.makeText(context, "Please enter a link", Toast.LENGTH_SHORT).show()
        onComplete()
        return
    }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL(reelLink)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val contentValue = ContentValues().apply {
                    put(
                        MediaStore.MediaColumns.DISPLAY_NAME,
                        "insta_reel_${System.currentTimeMillis()}.mp4"
                    )
                    put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Movies/InstaReels")
                }
                val resolver = context.contentResolver
                val uri: Uri? =
                    resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValue)

                if (uri != null) {
                    val outputStream: OutputStream? = resolver.openOutputStream(uri)

                    outputStream.use { output ->
                        connection.inputStream.use { input ->
                            input.copyTo(output!!)
                        }
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Downloaded: ${uri.path}", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to create media file", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to download reel", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        } finally {
            onComplete()
        }
    }
}