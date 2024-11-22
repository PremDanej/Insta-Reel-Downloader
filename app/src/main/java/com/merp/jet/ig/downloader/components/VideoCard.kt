package com.merp.jet.ig.downloader.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = Modifier.padding(10.dp, 8.dp),
        colors = CardDefaults.cardColors(BACKGROUND_COLOR),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Image(
                bitmap = convertBase64ToBitmap(imageUrl),
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(140.dp)
                    .width(90.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = reelResponse.title.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = "Quality: ${media.quality}",
                )
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = "Size: ${media.formattedSize}",
                )
                Row(content = content)
            }
        }
    }
}