package com.example.scareme.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scareme.R
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.ui.theme.balooFontFamily
import com.example.scareme.presentation.ui.theme.jollyFontFamily
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    MainScreen(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180c14)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "ScareMe",
                    fontFamily = jollyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 100.sp,
                    color = Color(0xFFF6921D)
                )

                BubbleElement()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                colors = CardDefaults.cardColors(Color(0xFFF6921D)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .width(328.dp)
                    .height(56.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                onClick = {
                    navController.navigate(route = AppScreens.SignUp.route)
                },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign Up",
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }

            Text(
                text = "Already have an account?",
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFFB14623),
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = "Sign In",
                color = Color(0xFFF6921D),
                fontFamily = balooFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .background(Color(0xFF180c14))
                    .clickable {
                        navController.navigate(route = AppScreens.SignIn.route)
                    }
            )
        }
    }
}
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
                durationMillis = 3000
                0.0f at 0 using LinearEasing
                360f at 3000 using LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ), label = "angle"
    )

    val outerCircleRadius = 250.dp
    val innerCircleRadius = 150.dp
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
                    .size(100.dp)
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