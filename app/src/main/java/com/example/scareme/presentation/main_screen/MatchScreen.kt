package com.example.scareme.presentation.main_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.scareme.R
import com.example.scareme.common.ErrorDialog
import com.example.scareme.domain.Entities.RequestBodies.UserRequest
import com.example.scareme.navigation.AppScreens
import com.example.scareme.presentation.bottomnav.NavigationBar
import com.example.scareme.presentation.main_screen.components.LoadingScreen
import com.example.scareme.presentation.ui.theme.balooFontFamily
import kotlinx.coroutines.launch

@Composable
fun Main(navController: NavController) {
    val context = LocalContext.current
    val viewModel: MatchViewModel = viewModel(factory = ViewModelFactory(context))
    Matches(navController, viewModel)
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun Matches(navController: NavController, viewModel: MatchViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchProfiles()
    }

    val profiles by viewModel.profiles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val scope = rememberCoroutineScope()
    val states = profiles.reversed().map { it to rememberSwipeableCardState() }

    if (errorMessage != null) {
        ErrorDialog(
            errorMessage = errorMessage!!,
            onDismiss = {
                viewModel.clearErrorMessage()
                if (errorMessage == "Something went wrong, please check your connection") {
                    navController.navigate(AppScreens.HomeScreen.route)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.back),
                contentScale = ContentScale.FillBounds
            )
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.trick_or_treat),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp, bottom = 5.dp, top = 15.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingScreen()
            }
        } else {
            if (profiles.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(height = 508.dp, width = 318.dp)
                        .align(Alignment.CenterHorizontally),
                ) {

                    // https://github.com/alexstyl/compose-tinder-card (Swipe Cards)
                    states.forEach { (user, state) ->
                        if (state.swipedDirection == null) {
                            ProfileCard(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .swipableCard(
                                        state = state,
                                        blockedDirections = listOf(Direction.Down),
                                        onSwiped = { direction ->
                                            when (direction) {
                                                Direction.Left -> {
                                                    viewModel.dislikeProfile(user.userId ?: "")
                                                }

                                                Direction.Right -> {
                                                    viewModel.likeProfile(user.userId ?: "")
                                                }

                                                else -> Log.d(
                                                    "Swipeable-Card",
                                                    "Unhandled swipe direction: $direction"
                                                )
                                            }
                                        },
                                        onSwipeCancel = {
                                            Log.d("Swipeable-Card", "Cancelled swipe")
                                        }
                                    ),
                                user = user,
                                navController = navController
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.no_profiles_available),
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 31.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleButton(
                onClick = {
                    scope.launch {
                        val last = states.reversed()
                            .firstOrNull {
                                it.second.offset.value == Offset(0f, 0f)
                            }
                        last?.let { (user, state) ->
                            viewModel.dislikeProfile(user.userId ?: "")
                            state.swipe(Direction.Left)
                        }
                    }
                },
                icon = painterResource(id = R.drawable.pass)
            )

            CircleButton(
                onClick = {
                    scope.launch {
                        val last = states.reversed()
                            .firstOrNull {
                                it.second.offset.value == Offset(0f, 0f)
                            }
                        last?.let { (user, state) ->
                            viewModel.likeProfile(user.userId ?: "")
                            state.swipe(Direction.Right)
                        }
                    }
                },
                icon = painterResource(id = R.drawable.like)
            )
        }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            NavigationBar(navController)
        }
    }
}

@Composable
fun ProfileCard(
    modifier: Modifier,
    user: UserRequest,
    navController: NavController
) {
    Card(
        modifier = modifier.clickable {
            navController.navigate(AppScreens.Profile.route)
        }
    ) {
        Box {
            val painter = if (user.avatar != null) {
                rememberAsyncImagePainter(user.avatar)
            } else {
                painterResource(id = R.drawable.def)
            }
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color(0xff401c34), shape = RoundedCornerShape(11.dp))
                    .fillMaxWidth()
                    .height(53.dp)
            ) {
                Text(
                    text = user.name,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = balooFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun CircleButton(
    onClick: () -> Unit,
    icon: Painter,
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xff401c34))
            .size(56.dp)
            .border(2.dp, Color.Black, CircleShape),
        onClick = onClick
    ) {
        Icon(icon, null,
            tint = Color(0xFFe84c3c),
            modifier = Modifier.size(24.dp)
        )
    }
}