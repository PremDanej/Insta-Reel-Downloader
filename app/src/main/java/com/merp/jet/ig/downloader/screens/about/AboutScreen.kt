package com.merp.jet.ig.downloader.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ScreenDefault

@Composable
fun AboutScreen(navController: NavController) {

    ScreenDefault(
        title = stringResource(id = R.string.lbl_about),
        isMainScreen = false,
        icon = Icons.AutoMirrored.Default.ArrowBack,
        navController = navController,
        onBackPressed = { navController.popBackStack() }
    ) {
        ScreenContent()
    }
}

@Composable
fun ScreenContent() {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = BACKGROUND_COLOR),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
            color = ON_BACKGROUND_COLOR
        )
        Text(
            text = stringResource(R.string.lbl_version),
            style = MaterialTheme.typography.bodyMedium
        )
        Image(
            painter = painterResource(id = R.drawable.launcher),
            contentDescription = "",
            modifier = Modifier.size(130.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.lbl_made_with),
                style = MaterialTheme.typography.bodyLarge,
                color = ON_BACKGROUND_COLOR,
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier.padding(5.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Heart",
                tint = Color.Red.copy(700f)
            )
        }
    }
}