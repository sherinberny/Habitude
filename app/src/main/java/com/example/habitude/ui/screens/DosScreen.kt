package com.example.habitude.ui.screens


import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DosScreen(navHostController: NavHostController, habitsViewModel: HabitsViewModel = viewModel()) {
    val state = habitsViewModel.uiState
    val scope = rememberCoroutineScope()

    // Fetch habits when the screen is launched
    LaunchedEffect(Unit) {
        scope.launch { habitsViewModel.getHabits(doordont = false) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(state.habits, key = { habit -> "do_${habit.id}" }) { habit ->
            HabitListItem(
                habit = habit,
                toggle = {
                    scope.launch { habit.id?.let { id -> habitsViewModel.toggleHabitCompletion(id) } }
                },
                onEditPressed = {
                    navHostController.navigate("habitmodification?id=${habit.id}")
                }
            )
        }
    }
}
