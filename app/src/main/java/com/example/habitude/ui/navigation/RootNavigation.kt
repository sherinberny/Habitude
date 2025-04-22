package com.example.habitude.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.habitude.ui.repositories.UserRepository
import com.example.habitude.ui.screens.*
import kotlinx.coroutines.launch
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                // Do's Menu Item
                NavigationDrawerItem(
                    label = { Text(text = "Do's") },
                    selected = currentDestination?.route == Routes.dos.route,
                    onClick = {
                        navController.navigate(Routes.dos.route)
                        scope.launch { drawerState.close() }
                    }
                )
                // Don'ts Menu Item
                NavigationDrawerItem(
                    label = { Text(text = "Don'ts") },
                    selected = currentDestination?.route == Routes.donts.route,
                    onClick = {
                        navController.navigate(Routes.donts.route)
                        scope.launch { drawerState.close() }
                    }
                )
                Divider()
                // Logout Menu Item
                NavigationDrawerItem(
                    label = { Text(text = "Logout") },
                    selected = false,
                    onClick = {
                        UserRepository.logout()
                        navController.navigate(Routes.launchNavigation.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentDestination?.hierarchy?.none {
                        it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route
                    } == true) {
                    TopAppBar(
                        title = { Text(text = "Habitude", color = Color.White) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                                }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu button", tint = Color.White)
                            }
                        })
                }
            },
            floatingActionButton = {
                if (currentDestination?.hierarchy?.none {
                        it.route == Routes.launchNavigation.route ||
                                it.route == Routes.splashScreen.route ||
                                it.route == Routes.habitModification.route
                    } == true) {
                    FloatingActionButton(onClick = { navController.navigate("habitmodification") }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                    }
                }
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.splashScreen.route,
                modifier = Modifier.padding(paddingValues = it)
            ) {
                navigation(
                    route = Routes.launchNavigation.route, startDestination = Routes.launch.route
                ) {
                    composable(route = Routes.launch.route) { LaunchScreen(navController) }
                    composable(route = Routes.signIn.route) { SignInScreen(navController) }
                    composable(route = Routes.signUp.route) { SignUpScreen(navController) }
                }
                navigation(
                    route = Routes.appNavigation.route, startDestination = Routes.home.route
                ) {
                    composable(route = Routes.home.route) { HomeScreen(navController) }
                    composable(
                        route = Routes.habitModification.route,
                        arguments = listOf(navArgument("id") { defaultValue = "new" })
                    ) { navBackStackEntry ->
                        HabitModificationScreen(
                            navController, navBackStackEntry.arguments?.getString("id")
                        )
                    }
                    composable(route = Routes.dos.route) { DosScreen(navController) }
                    composable(route = Routes.donts.route) { DontsScreen(navController) }
                }
                composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
            }
        }
    }
}
