package com.merp.jet.ig.downloader.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.merp.jet.ig.downloader.R.drawable.launcher
import com.merp.jet.ig.downloader.R.string.app_name
import com.merp.jet.ig.downloader.R.string.lbl_reels_downloader_app
import com.merp.jet.ig.downloader.components.BACKGROUND_COLOR
import com.merp.jet.ig.downloader.components.ON_BACKGROUND_COLOR

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_COLOR)
    ) {
        val logoAnimation = remember { Animatable(0f) }
        val appTextAnimation = remember { Animatable(0f) }
        val reelTextAnimation = remember { Animatable(0f) }

        LaunchedEffect(true) {
            appTextAnimation.animateTo(0.9f)
            reelTextAnimation.animateTo(0.9f, tween(100))
            logoAnimation.animateTo(
                targetValue = 0.9f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(10f).getInterpolation(it)
                    },
                    delayMillis = 200
                )
            )
            appTextAnimation.animateTo(0f, animationSpec = tween(700))
            reelTextAnimation.animateTo(0f, animationSpec = tween(400))
            logoAnimation.animateTo(600f, animationSpec = tween(2100))
        }

        Column(
            modifier = Modifier.fillMaxSize().background(BACKGROUND_COLOR),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.size(200.dp).scale(logoAnimation.value),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AppLogo()
            }
            Text(
                modifier = Modifier.scale(appTextAnimation.value),
                text = stringResource(app_name),
                style = MaterialTheme.typography.titleLarge,
                color = ON_BACKGROUND_COLOR
            )
            Text(
                modifier = Modifier.scale(reelTextAnimation.value * 2 / 3),
                text = stringResource(lbl_reels_downloader_app),
                style = MaterialTheme.typography.titleLarge,
                color = ON_BACKGROUND_COLOR
            )
        }
    }
}

@Composable
fun AppLogo() {
    Image(
        painter = painterResource(id = launcher),
        contentDescription = "",
        modifier = Modifier.size(200.dp)
    )
}
