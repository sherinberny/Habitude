package com.example.habitude.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habitude.ui.components.HabitListItem
import com.example.habitude.ui.viewmodels.HabitsViewModel
import kotlinx.coroutines.launch

@Composable
fun DosScreen(navHostController: NavHostController) {
    val viewModel: HabitsViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) { viewModel.getHabits(false) }
    LazyColumn(modifier = Modifier.fillMaxHeight().padding(4.dp)) {
        items(state.habits, key = { "do_${it.id}" }) { habit -> // Add unique "do_" prefix
            HabitListItem(
                habit = habit,
                toggle = { scope.launch { habit.id?.let { id -> viewModel.toggleHabitCompletion(id) } } },
                onEditPressed = { navHostController.navigate("habitmodification?id=${habit.id}") }
            )
        }
    }
}
