package com.example.habitude.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.habitude.ui.repositories.UserRepository
import com.example.habitude.ui.screens.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationDnB() {
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

                // Home Menu Item (Defaults to Dos)
                NavigationDrawerItem(
                    label = { Text("Home",
                        color = if (currentDestination?.route == Routes.home.route)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.White
                    ) },
                    selected = currentDestination?.route == Routes.home.route,
                    onClick = {
                        navController.navigate(Routes.home.route) {
                            popUpTo(Routes.home.route) { inclusive = true }
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                // Profile
                NavigationDrawerItem(
                    label = { Text("Profile",
                        color = if (currentDestination?.route == Routes.profile.route)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.White
                    ) },
                    selected = currentDestination?.route == Routes.profile.route,
                    onClick = {
                        navController.navigate(Routes.profile.route) {
                            popUpTo(Routes.home.route) { inclusive = false }
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                // Notifications Menu Item
                NavigationDrawerItem(
                    label = { Text("Notifications",
                        color = if (currentDestination?.route == Routes.notifications.route)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.White
                    ) },
                    selected = currentDestination?.route == Routes.notifications.route,
                    onClick = {
                        navController.navigate(Routes.notifications.route) {
                            popUpTo(Routes.home.route) { inclusive = false }
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                Divider()

                // Logout Menu Item
                NavigationDrawerItem(
                    label = { Text(text = "Logout",
                            color = Color.White
                    ) },
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
                // Make sure the FAB is only shown on the Home screen, Dos, and Don'ts
                if (currentDestination?.route == Routes.home.route || currentDestination?.route == Routes.dos.route || currentDestination?.route == Routes.donts.route) {
                    FloatingActionButton(
                        onClick = { navController.navigate("habitmodification?id=someId")},
                            modifier = Modifier.padding(bottom = 80.dp),
                        containerColor = MaterialTheme.colorScheme.primary) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                    }
                }
            },
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = Routes.splashScreen.route,
                modifier = Modifier.padding(paddingValues)
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
                    // Home with Bottom Navigation for Dos and Donts
                    composable(route = Routes.home.route) {
                        HomeScreen(navController) // Parent screen with Bottom Navigation
                    }

                    // Dos Screen
                    composable(route = Routes.dos.route) {
                        DosScreen(navController)
                    }

                    // Donts Screen
                    composable(route = Routes.donts.route) {
                        DontsScreen(navController)
                    }

                    // Habit Modification Screen with Argument
                    composable(
                        route = Routes.habitModification.route,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")
                        HabitModificationScreen(navController, id)
                    }

                    // Profile Screen
                    composable(route = Routes.profile.route) { ProfileScreen(navController) }

                    // Notifications Screen
                    composable(route = Routes.notifications.route) {
                        NotificationsScreen(
                            navController
                        )
                    }
                }
                composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
            }
        }
    }
}


/*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationDnB() {
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

                // Home Menu Item (Defaults to Dos)
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = currentDestination?.route == Routes.home.route,
                    onClick = {
                        navController.navigate(Routes.home.route) {
                            popUpTo(Routes.home.route) { inclusive = true }
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                //Profile
                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = currentDestination?.route == Routes.profile.route,
                    onClick = {
                        navController.navigate(Routes.profile.route) {
                            popUpTo(Routes.home.route) { inclusive = false }
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                // Notifications Menu Item
                NavigationDrawerItem(
                    label = { Text("Notifications") },
                    selected = currentDestination?.route == Routes.notifications.route,
                    onClick = {
                        navController.navigate(Routes.notifications.route) {
                            popUpTo(Routes.home.route) { inclusive = false }
                        }
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
                    FloatingActionButton(onClick = { navController.navigate("habitmodification?id=someId") }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                    }
                }
            },
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = Routes.splashScreen.route,
                modifier = Modifier.padding(paddingValues)
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
                    // Deep Link Handling for Sign In
                    */
/* composable(
                    route = Routes.signIn.route,
                    deepLinks = listOf(navDeepLink { uriPattern = "android-app://androidx.navigation/signin" })
                ) { SignInScreen(navController) }

                composable(route = Routes.appNavigation.route) { AppNavigationScreen(navController) }

                // SignUp Screen with deepLink
                composable(
                    route = Routes.signUp.route,
                    deepLinks = listOf(navDeepLink { uriPattern = "android-app://androidx.navigation/signup" })
                ) { SignUpScreen(navController) }*//*


                    // Home with Bottom Navigation for Dos and Donts
                    composable(route = Routes.home.route) {
                        HomeScreen(navController) // Parent screen with Bottom Navigation
                    }

                    // Dos Screen
                    composable(route = Routes.dos.route) {
                        DosScreen(navController)
                    }

                    // Donts Screen
                    composable(route = Routes.donts.route) {
                        DontsScreen(navController)
                    }

                    // Habit Modification Screen with Argument
                    composable(
                        route = Routes.habitModification.route,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")
                        HabitModificationScreen(navController, id)
                    }

                    // Profile Screen
                    composable(route = Routes.profile.route) { ProfileScreen(navController) }

                    // Notifications Screen
                    composable(route = Routes.notifications.route) {
                        NotificationsScreen(
                            navController
                        )
                    }
                }
            composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
            }
        }
    }
}
*/
