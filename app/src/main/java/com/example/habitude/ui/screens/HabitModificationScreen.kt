package com.example.habitude.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habitude.ui.components.FormField
import com.example.habitude.ui.viewmodels.HabitModificationViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitModificationScreen(navHostController: NavHostController, id: String?) {
    val viewModel: HabitModificationViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(true) { viewModel.setUpInitialState(id) }
    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            navHostController.navigate("home")
        }
    }
    Column(modifier = Modifier.fillMaxHeight().padding(vertical = 100.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
            shadowElevation = 4.dp,
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Create Habit",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { state.title = it },
                    placeholder = { Text(text = "Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = state.daily_duration,
                    onValueChange = { state.daily_duration = it },
                    placeholder = { Text(text = "Duration") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                )

                OutlinedTextField(
                    value = state.frequency,
                    onValueChange = { state.frequency = it },
                    placeholder = { Text(text = "Frequency (in min)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                )

                OutlinedTextField(
                    value = state.start_date,
                    onValueChange = { state.start_date = it },
                    placeholder = { Text(text = "Start Date") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Avoid this habit:")
                    Checkbox(checked = state.avoid, onCheckedChange = { state.avoid = it })
                }

                Spacer(modifier = Modifier.padding(6.dp))
                Divider()
                Spacer(modifier = Modifier.padding(6.dp))
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { navHostController.popBackStack() }) { Text(text = "Cancel") }
                    Spacer(modifier = Modifier.padding(30.dp))
                    Button(onClick = { scope.launch { viewModel.saveHabit() } }, elevation = null) {
                        Text(text = "Save Habit")
                    }
                }

                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}

/*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitModificationScreen(navHostController: NavHostController, id: String?) {
  val viewModel: HabitModificationViewModel = viewModel()
  val scope = rememberCoroutineScope()
  val state = viewModel.uiState

  LaunchedEffect(true) { viewModel.setUpInitialState(id) }
  LaunchedEffect(state.saveSuccess) {
    println(id)
    if (state.saveSuccess) {
      navHostController.navigate("home")
    }
  }
  Column(modifier = Modifier.fillMaxHeight().padding(vertical = 100.dp)) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
        shadowElevation = 4.dp,
        color = Color.White) {
          Column(
              modifier = Modifier.padding(12.dp),
          ) {
            Text(
                text = "Create Habit",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp))
            OutlinedTextField(
                value = state.title,
                onValueChange = { state.title = it },
                placeholder = { Text(text = "Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),)

              OutlinedTextField(
                  value = state.daily_duration,
                  onValueChange = { state.daily_duration = it },
                  placeholder = { Text(text = "Duration") },
                  keyboardOptions = KeyboardOptions.Default.copy(
                          keyboardType = KeyboardType.Number // Force numeric keyboard
                          ),
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 16.dp),)

              OutlinedTextField(
                  value = state.frequency,
                  onValueChange = { state.frequency = it },
                  placeholder = { Text(text = "Frequency (in min)") },
                  keyboardOptions = KeyboardOptions.Default.copy(
                      keyboardType = KeyboardType.Number // Force numeric keyboard
                  ),
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 16.dp),)

              OutlinedTextField(
                  value = state.start_date,
                  onValueChange = { state.start_date = it },
                  placeholder = { Text(text = "Start Date") },
                  keyboardOptions = KeyboardOptions.Default.copy(
                      keyboardType = KeyboardType.Number // Force numeric keyboard
                  ),
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 16.dp),)

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "Avoid this habit:")
              Checkbox(checked = state.avoid, onCheckedChange = { state.avoid = it })
            }

            Spacer(modifier = Modifier.padding(6.dp))
            Divider()
            Spacer(modifier = Modifier.padding(6.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
              Button (onClick = { navHostController.popBackStack() }) { Text(text = "Cancel") }
                Spacer(modifier = Modifier.padding(30.dp))
              Button(onClick = { scope.launch { viewModel.saveHabit() } }, elevation = null) {
                Text(text = "Save Habit")
              }
            }
            Text(
                text = state.errorMessage,
                style = TextStyle(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right)
          }
        }
  }
}
*/
