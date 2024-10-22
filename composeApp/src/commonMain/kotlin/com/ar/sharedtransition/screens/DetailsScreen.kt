package com.ar.sharedtransition.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import sharedlayout.composeapp.generated.resources.Res
import sharedlayout.composeapp.generated.resources.cupcake1


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    name: String,
    id: Int,
    transitionScope: SharedTransitionScope,
    animationScope: AnimatedVisibilityScope,
    navigateBack: () -> Unit,
) {
    val selectionColor = Color(0xff3367ba)
    val state = rememberLazyListState()
    val expanded by remember {
        derivedStateOf {
            state.firstVisibleItemIndex > 1
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        with(transitionScope) {
            with(animationScope) {
                LazyColumn(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp, bottom = 15.dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "ItemCard $id"),
                            animatedVisibilityScope = animationScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            boundsTransform = { _, _ ->
                                tween(500)
                            }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                    contentPadding = PaddingValues(bottom = 200.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        navigateBack.invoke()
                                    },
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Cyan,
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .size(250.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .padding(5.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(key = "TestBox $id"),
                                        animatedVisibilityScope = animationScope,
                                        boundsTransform = { _, _ ->
                                            spring(
                                                dampingRatio = 0.8f,
                                                stiffness = 380f
                                            )
                                        }
                                    )
                            ) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .sharedElement(
                                            state = rememberSharedContentState(key = "TestImage $id"),
                                            animatedVisibilityScope = animationScope,
                                            boundsTransform = { _, _ -> tween(500) }
                                        ),
                                    painter = painterResource(Res.drawable.cupcake1),
                                    contentDescription = null
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    //                            .sharedBounds(
                                    //                                sharedContentState = rememberSharedContentState(key = "Test $id"),
                                    //                                animatedVisibilityScope = animationScope,
                                    //                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    //                                boundsTransform = { _, _ -> tween(500) }
                                    //                            ),
                                    .skipToLookaheadSize(),
                                text = name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.Blue,
                                    fontStyle = FontStyle.Italic
                                )
                            )
                        }
                    }
                    repeat(15) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .skipToLookaheadSize()
                                    .padding(horizontal = 15.dp),
                                text = "This is the Description for Item Number $id",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    textAlign = TextAlign.Start,
                                    fontSize = 21.sp
                                )
                            )
                        }
                    }
                }
                SharedTransitionLayout(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .renderInSharedTransitionScopeOverlay(
                            zIndexInOverlay = 1f
                        )
                        .animateEnterExit(
                            enter = fadeIn() + slideInVertically { it },
                            exit = fadeOut() + slideOutVertically { it }
                        )
                        .background(Color.White)
                ) {
                    AnimatedVisibility(
                        visible = !expanded,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .sharedBounds(
                                    rememberSharedContentState(key = "container-key"),
                                    this@AnimatedVisibility,
                                    enter = EnterTransition.None
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedBounds(
                                        rememberSharedContentState(key = "order button"),
                                        this@AnimatedVisibility,
                                        enter = EnterTransition.None,
                                        exit = ExitTransition.None
                                    )
                                    .padding(horizontal = 15.dp),
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = "Save Order", modifier = Modifier.sharedBounds(
                                        rememberSharedContentState(key = "orderButtonText"),
                                        this@AnimatedVisibility
                                    )
                                )
                            }
//                            Spacer(Modifier.height(20.dp))
//                            TextButton(onClick = {
//                                //expanded = !expanded
//                            }) {
//                                Text(text = "More options")
//                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = expanded,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .sharedBounds(
                                    rememberSharedContentState(key = "container-key"),
                                    this@AnimatedVisibility
                                ),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedBounds(
                                        rememberSharedContentState(key = "order button"),
                                        this@AnimatedVisibility
                                    )
                                    .padding(horizontal = 15.dp),
                                onClick = {
                                    //expanded = !expanded
                                },
                            ) {
                                Text(
                                    text = "Save Order", modifier = Modifier.sharedBounds(
                                        rememberSharedContentState(key = "orderButtonText"),
                                        this@AnimatedVisibility
                                    )
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp),
                                onClick = {},
                            ) {
                                Text(text = "Draft Order")
                            }

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp),
                                onClick = {},
                            ) {
                                Text(text = "Cancle Order")
                            }
                        }
                    }
                }
            }
        }


//        SharedTransitionLayout(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .align(Alignment.BottomEnd)
//        ) {
//            AnimatedVisibility(
//                visible = expanded,
//                enter = EnterTransition.None,
//                exit = ExitTransition.None
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .renderInSharedTransitionScopeOverlay(
//                            zIndexInOverlay = 1f
//                        )
//                        .animateEnterExit(
//                            enter = fadeIn() + slideInVertically { it },
//                            exit = fadeOut() + slideOutVertically { it }
//                        )
//                ) {
//                    Surface(
//                        Modifier
//                            .align(Alignment.Center)
//                            .padding(10.dp)
//                            .sharedBounds(
//                                rememberSharedContentState(key = "container"),
//                                this@AnimatedVisibility
//                            )
//                            .requiredHeightIn(min = 80.dp, max = 80.dp),
//                        shape = RoundedCornerShape(50),
//                    ) {
//                        Row(
//                            Modifier
//                                .padding(10.dp)
//                                .skipToLookaheadSize(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                Icons.Outlined.Share,
//                                contentDescription = "Share",
//                                modifier = Modifier.padding(
//                                    top = 10.dp,
//                                    bottom = 10.dp,
//                                    start = 10.dp,
//                                    end = 20.dp
//                                ).size(36.dp)
//                            )
//                            Icon(
//                                Icons.Outlined.Favorite,
//                                contentDescription = "Favorite",
//                                modifier = Modifier.padding(
//                                    top = 10.dp,
//                                    bottom = 10.dp,
//                                    start = 10.dp,
//                                    end = 20.dp
//                                ).size(36.dp)
//                            )
//                            Icon(
//                                Icons.Outlined.Create,
//                                contentDescription = "Create",
//                                tint = Color.White,
//                                modifier = Modifier
//                                    .pointerInput(Unit) {
//                                        detectTapGestures(
//                                            onLongPress = {
//                                                expanded = !expanded
//                                            }
//                                        )
//                                    }
//                                    .sharedBounds(
//                                        rememberSharedContentState(key = "icon_background"),
//                                        this@AnimatedVisibility
//                                    )
//                                    .background(selectionColor, RoundedCornerShape(50))
//                                    .padding(
//                                        top = 10.dp,
//                                        bottom = 10.dp,
//                                        start = 20.dp,
//                                        end = 20.dp
//                                    )
//                                    .size(36.dp)
//                                    .sharedElement(
//                                        rememberSharedContentState(key = "icon"),
//                                        this@AnimatedVisibility
//                                    )
//                            )
//                        }
//                    }
//                }
//            }
//            AnimatedVisibility(
//                visible = !expanded,
//                enter = EnterTransition.None,
//                exit = ExitTransition.None
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .renderInSharedTransitionScopeOverlay(
//                            zIndexInOverlay = 1f
//                        )
//                        .animateEnterExit(
//                            enter = fadeIn() + slideInVertically { it },
//                            exit = fadeOut() + slideOutVertically { it }
//                        )
//                ) {
//                    Surface(
//                        Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(bottom = 20.dp, end = 20.dp)
//                            .size(80.dp)
//                            .sharedBounds(
//                                rememberSharedContentState(key = "container"),
//                                this@AnimatedVisibility,
//                                enter = EnterTransition.None,
//                            )
//                            .sharedBounds(
//                                rememberSharedContentState(key = "icon_background"),
//                                this@AnimatedVisibility,
//                                enter = EnterTransition.None,
//                                exit = ExitTransition.None
//                            ),
//                        shape = RoundedCornerShape(30.dp),
//                        color = selectionColor
//                    ) {
//                        Icon(
//                            Icons.Outlined.Create,
//                            contentDescription = "Create",
//                            tint = Color.White,
//                            modifier = Modifier
//                                .pointerInput(Unit) {
//                                    detectTapGestures(
//                                        onLongPress = {
//                                            expanded = !expanded
//                                        },
//                                        onTap = {
//                                            navigateBack.invoke()
//                                        }
//                                    )
//                                }
//                                .padding(20.dp)
//                                .sharedElement(
//                                    rememberSharedContentState(key = "icon"),
//                                    this@AnimatedVisibility
//                                )
//                        )
//                    }
//                }
//            }
//
//        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimatedButtons(
    modifier: Modifier,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    SharedTransitionLayout(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = !expanded,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "container-key"),
                        this@AnimatedVisibility,
                        enter = EnterTransition.None
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedBounds(
                            rememberSharedContentState(key = "order button"),
                            this@AnimatedVisibility,
                            enter = EnterTransition.None,
                            exit = ExitTransition.None
                        )
                        .padding(horizontal = 15.dp),
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Text(
                        text = "Save Order", modifier = Modifier.sharedBounds(
                            rememberSharedContentState(key = "orderButtonText"),
                            this@AnimatedVisibility
                        )
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = expanded,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "container-key"),
                        this@AnimatedVisibility
                    )
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedBounds(
                            rememberSharedContentState(key = "order button"),
                            this@AnimatedVisibility
                        )
                        .padding(horizontal = 15.dp),
                    onClick = {
                        expanded = !expanded
                    },
                ) {
                    Text(
                        text = "Save Order", modifier = Modifier.sharedBounds(
                            rememberSharedContentState(key = "orderButtonText"),
                            this@AnimatedVisibility
                        )
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    onClick = {},
                ) {
                    Text(text = "Draft Order")
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    onClick = {},
                ) {
                    Text(text = "Cancle Order")
                }
            }
        }
    }
}