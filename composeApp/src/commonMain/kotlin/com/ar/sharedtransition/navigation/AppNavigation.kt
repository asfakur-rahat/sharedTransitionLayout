package com.ar.sharedtransition.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ar.sharedtransition.screens.DetailsScreen
import com.ar.sharedtransition.screens.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object Home

@Serializable
data class Details(val name: String, val id: Int)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionApp(){
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(navController = navController , startDestination = Home){
            composable<Home> {
                HomeScreen(
                    transitionScope = this@SharedTransitionLayout,
                    animationScope = this,
                    navigateToDetails = { name , number ->
                        navController.navigate(Details(name, number))
                    }
                )
            }
            composable<Details> {
                val details : Details = it.toRoute()
                DetailsScreen(
                    name = details.name,
                    id = details.id,
                    transitionScope = this@SharedTransitionLayout,
                    animationScope = this,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}