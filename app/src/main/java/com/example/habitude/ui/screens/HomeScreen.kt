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
import com.example.habitude.ui.components.HabitListFromBoth
import com.example.habitude.ui.components.HabitListItem
import com.example.habitude.ui.viewmodels.HabitsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navHostController: NavHostController) {
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
}
