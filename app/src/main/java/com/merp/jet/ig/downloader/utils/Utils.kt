package com.merp.jet.ig.downloader.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object Utils {

    fun downloadReel(videoLink: String, context: Context, onComplete: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(videoLink)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val contentValue = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME,"InstaReel_${System.currentTimeMillis()}.mp4")
                        put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, "Movies/InstaReels")
                    }

                    val resolver: ContentResolver = context.contentResolver
                    val uri: Uri? =
                        resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValue)

                    uri?.let { validUri ->
                        resolver.openOutputStream(validUri)?.use { outputStream ->
                            connection.inputStream.use { input ->
                                input.copyTo(outputStream)
                            }
                        }
                        withContext(Dispatchers.Main) {
                            showToast(context, message = "Reel Downloaded")
                        }
                    } ?: run {
                        withContext(Dispatchers.Main) {
                            showToast(context, message = "Failed to download reel")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showToast(context, message = "Failed to download reel")
                    }
                }
            } catch (e: MalformedURLException) {
                Log.e("REEL", "Malformed URL: ${e.message}")
                withContext(Dispatchers.Main) {
                    showToast(context, message = "Invalid URL")
                }
            } catch (e: IOException) {
                Log.e("REEL", "IO Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    showToast(context, message = "Something went wrong")
                }
            } finally {
                onComplete()
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
}