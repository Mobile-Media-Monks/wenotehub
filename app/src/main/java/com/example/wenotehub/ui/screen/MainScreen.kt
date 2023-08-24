package com.example.wenotehub.ui.screen


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wenotehub.ui.components.DotVector
import com.example.wenotehub.ui.components.HomeVector
import com.example.wenotehub.ui.components.PlusVector
import com.example.wenotehub.ui.components.SettingsVector
import com.example.wenotehub.ui.navigation.NavGraph
import com.example.wenotehub.ui.navigation.ScreenNavGraph
import com.example.wenotehub.ui.utils.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()


    when (navBackStackEntry?.destination?.route) {
        "create_screen" -> {
            bottomBarState.value = false
        }

        else -> {
            bottomBarState.value = true
        }
    }


    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                bottomBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomBar(navController)
            }
        },
    ) { contentPadding ->
        // Screen content
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = 24.dp)
        ) {
            NavGraph(navController = navController) {
                when (navBackStackEntry?.destination?.route) {
                    "create_screen" -> {
                        bottomBarState.value = false
                    }

                    else -> {
                        bottomBarState.value = true
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val containerColor = BottomAppBarDefaults.containerColor

    Surface(
        color = containerColor,
        contentColor = contentColorFor(containerColor),
        tonalElevation = BottomAppBarDefaults.ContainerElevation,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
                .height(100.dp)
                .padding(BottomAppBarDefaults.ContentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally)
        ) {
            AddItem(
                screen = ScreenNavGraph.HomeScreen,
                currentDestination = currentDestination,
                navController = navController,
                imageVector = HomeVector()
            )
            Icon(
                imageVector = PlusVector(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier
                    .noRippleClickable {
                        navController.navigate(ScreenNavGraph.CreateScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                    .size(70.dp)
            )
            AddItem(
                screen = ScreenNavGraph.SettingsScreen,
                currentDestination = currentDestination,
                navController = navController,
                imageVector = SettingsVector()

            )
        }
    }


}

@Composable
fun RowScope.AddItem(
    screen: ScreenNavGraph,
    currentDestination: NavDestination?,
    navController: NavHostController,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {

    val isCurrent = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true


    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint.copy(alpha = if (isCurrent) 1f else 0.2f),
                modifier = modifier
                    .noRippleClickable {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                    .size(32.dp)
            )

            Icon(
                imageVector = DotVector(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint.copy(alpha = if (isCurrent) 1f else 0.2f),
                modifier = modifier.size(10.dp).padding(top = 4.dp)
            )
        }
    }

    /*NavigationBarItem(
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = null)
        },
        label = {
            Text(text = screen.title)
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true
    )*/
}