package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.merp.jet.ig.downloader.R.string.lbl_video_download_ready
import com.merp.jet.ig.downloader.R.string.lbl_video_quality
import com.merp.jet.ig.downloader.R.string.lbl_video_size
import com.merp.jet.ig.downloader.model.ReelResponse
import com.merp.jet.ig.downloader.utils.Utils.convertBase64ToBitmap

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    reelResponse: ReelResponse,
    content: @Composable (RowScope.() -> Unit) = {}
) {
    val imageUrl = reelResponse.thumbnail.split(',')[1]
    val media = reelResponse.medias[0]

    Card(
        shape = RoundedCornerShape(10),
        modifier = modifier.padding(10.dp, 8.dp),
        colors = CardDefaults.cardColors(BACKGROUND_COLOR),
        border = BorderStroke(0.2.dp, ON_BACKGROUND_COLOR),
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(140.dp)) {
            Image(
                bitmap = convertBase64ToBitmap(imageUrl),
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(140.dp).width(90.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = lbl_video_download_ready),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(lbl_video_quality, media.quality),
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(lbl_video_size, media.formattedSize),
                )
                Row(content = content)
            }
        }
    }
}