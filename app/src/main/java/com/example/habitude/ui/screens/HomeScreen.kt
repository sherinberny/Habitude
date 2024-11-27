package com.example.habitude.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.habitude.ui.components.HabitListFromBoth
import com.example.habitude.ui.components.HabitListItem
import com.example.habitude.ui.navigation.Routes
import com.example.habitude.ui.viewmodels.HabitsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
/*fun HomeScreen(navHostController: NavHostController) {
  val viewModel: HabitsViewModel = viewModel()
  val state = viewModel.uiState
  val scope = rememberCoroutineScope()

  LaunchedEffect(true) { viewModel.getHabitsFromBoth() }
  LazyColumn(modifier = Modifier.fillMaxHeight().padding(4.dp)) {
    items(state.habitsAll, key = { it.id!! }) { habits ->
      HabitListFromBoth(
          habit = habits,
          toggle = { scope.launch { habits.id?.let { id -> viewModel.toggleHabitCompletion(id) } } },
          onEditPressed = { navHostController.navigate("habitmodification?id=${habits.id}") })
    }
  }
}*/

fun HomeScreen(navHostController: NavHostController, habitsViewModel: HabitsViewModel = viewModel()) {
    // Nested NavController to handle Dos and Donts screens
    val nestedNavController = rememberNavController()

    // Bottom navigation state is directly tied to nestedNavController's current route
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = nestedNavController.currentBackStackEntryAsState().value?.destination?.route == Routes.dos.route,
                    onClick = {
                        nestedNavController.navigate(Routes.dos.route) {
                            // Ensure we don't duplicate the screen in the back stack
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Do's") },
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Do's") }
                )
                NavigationBarItem(
                    selected = nestedNavController.currentBackStackEntryAsState().value?.destination?.route == Routes.donts.route,
                    onClick = {
                        nestedNavController.navigate(Routes.donts.route) {
                            // Ensure we don't duplicate the screen in the back stack
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Don'ts") },
                    icon = { Icon(Icons.Default.Clear, contentDescription = "Don'ts") }
                )
            }
        }
    ) { innerPadding ->
        // Set up the NavHost for nested navigation (Dos and Donts screens)
        NavHost(
            navController = nestedNavController,
            startDestination = Routes.dos.route, // Default to Dos screen
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.dos.route) { DosScreen(navHostController, habitsViewModel) }
            composable(Routes.donts.route) { DontsScreen(navHostController, habitsViewModel) }
        }
    }
}