@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.ar.sharedtransition.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import sharedlayout.composeapp.generated.resources.Res
import sharedlayout.composeapp.generated.resources.cupcake1


val number = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    transitionScope: SharedTransitionScope,
    animationScope: AnimatedVisibilityScope,
    navigateToDetails: (String, Int) -> Unit,
) {
    with(transitionScope) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(15.dp)
        ) {
            number.forEach {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 36.dp)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "ItemCard $it"),
                                animatedVisibilityScope = animationScope,
                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                boundsTransform = { _, _ ->
                                    tween(500)
                                }
                            ).clickable {
                                navigateToDetails.invoke("Test $it", it)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Cyan
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally)
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(key = "TestBox $it"),
                                    animatedVisibilityScope = animationScope,
                                    boundsTransform = { _, _ ->
                                        spring(
                                            dampingRatio = 0.8f,
                                            stiffness = 380f
                                        )
                                    }
                                )
                        ){
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "TestImage $it"),
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
//                                .sharedBounds(
//                                    sharedContentState = rememberSharedContentState(key = "Test $it"),
//                                    animatedVisibilityScope = animationScope,
//                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
//                                    boundsTransform = { _, _ -> tween(500) }
//                                ),
                                .skipToLookaheadSize(),
                            text = "Test $it",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Black
                            )
                        )
                    }
                }
            }
        }
    }
}