package com.example.scareme.presentation.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.scareme.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// https://github.com/SmartToolFactory/Jetpack-Compose-Tutorials (6-24 Projection change with Lerp)
@Composable
fun BubbleElement() {
    val bubbles = listOf(
        R.drawable.bubble1,
        R.drawable.bubble2,
        R.drawable.bubble3,
        R.drawable.bubble4,
        R.drawable.bubble5,
        R.drawable.bubble6
    )

    val density = LocalDensity.current
    val transition = rememberInfiniteTransition(label = "infinite angle transition")

    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 4000
                0.0f at 0 using LinearEasing
                360f at 4000 using LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ), label = "angle"
    )

    val outerCircleRadius = 220.dp
    val innerCircleRadius = 120.dp
    val isOut by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        if (isOut) 1f else 0f,
        animationSpec = tween(durationMillis = 700, easing = LinearEasing),
        label = "distance"
    )

    val outerCircleRadiusPx = with(density) { outerCircleRadius.toPx() }
    val innerCircleRadiusPx = with(density) { innerCircleRadius.toPx() }
    val distance = lerp(innerCircleRadiusPx, outerCircleRadiusPx, progress)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        bubbles.forEachIndexed { index, drawableRes ->
            val itemAngle = 2 * PI * index / bubbles.size
            val totalAngle = itemAngle + (angle * DEGREE_TO_RAD)
            val offsetX = (distance * cos(totalAngle)).toInt()
            val offsetY = (distance * sin(totalAngle)).toInt()

            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            placeable.placeRelative(
                                offsetX + constraints.maxWidth / 2 - placeable.width / 2,
                                offsetY + constraints.maxHeight / 2 - placeable.height / 2
                            )
                        }
                    }
            )
        }
    }
}

private const val DEGREE_TO_RAD = (Math.PI / 180f).toFloat()