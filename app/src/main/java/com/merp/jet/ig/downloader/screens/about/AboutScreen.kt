package com.merp.jet.ig.downloader.screens.about

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.merp.jet.ig.downloader.R
import com.merp.jet.ig.downloader.R.drawable.launcher
import com.merp.jet.ig.downloader.R.string.app_name
import com.merp.jet.ig.downloader.R.string.lbl_made_with
import com.merp.jet.ig.downloader.R.string.lbl_version
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
    val context = LocalContext.current
    val version = context.packageManager.getPackageInfo(context.packageName,0).versionName
    val columnAnimation = remember { Animatable(0f) }
    LaunchedEffect(true) {
        columnAnimation.animateTo(1f, tween(200))
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = BACKGROUND_COLOR)
            .scale(columnAnimation.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(app_name),
            style = MaterialTheme.typography.titleMedium,
            color = ON_BACKGROUND_COLOR
        )
        Text(
            text = stringResource(lbl_version)+ " - " + version,
            style = MaterialTheme.typography.bodyMedium
        )
        Image(
            painter = painterResource(launcher),
            contentDescription = "",
            modifier = Modifier.size(130.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(lbl_made_with),
                style = MaterialTheme.typography.labelLarge,
                color = ON_BACKGROUND_COLOR,
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier.padding(5.dp),
                imageVector = Filled.Favorite,
                contentDescription = "Heart",
                tint = Color.Red.copy(700f)
            )
        }
    }
}