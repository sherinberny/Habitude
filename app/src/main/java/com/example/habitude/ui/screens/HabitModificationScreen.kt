package com.example.habitude.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habitude.ui.viewmodels.HabitModificationViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitModificationScreen(navHostController: NavHostController, id: String?) {
    val viewModel: HabitModificationViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    // Initialize calendar for DatePickerDialog
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // LaunchedEffects for initial setup and navigation
    LaunchedEffect(true) { viewModel.setUpInitialState(id) }
    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            navHostController.navigate("home")
        }
    }

    // Function to show DatePickerDialog
    val showDatePickerDialog = {
        DatePickerDialog(
            navHostController.context,
            { _, selectedYear, selectedMonth, selectedDay ->

                state.start_date = "${selectedYear.toString().padStart(2, '0')}-" +
                        "${(selectedMonth + 1).toString().padStart(2, '0')}-$selectedDay"
            },
            year,
            month,
            day
        ).show()
    }

    Column(modifier = Modifier.fillMaxHeight().padding(vertical = 100.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
            shadowElevation = 4.dp,
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Title Input Field
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

                // Start Date Input Field with Calendar Icon
                OutlinedTextField(
                    value = state.start_date,
                    onValueChange = { },
                    placeholder = { Text(text = "Start Date") },
                    readOnly = true, // Prevent manual input
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Select Date",
                            modifier = Modifier
                                .clickable { showDatePickerDialog() }
                                .padding(8.dp)
                        )
                    }
                )

                // Avoid Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Avoid this habit:")
                    Checkbox(
                        checked = state.avoid,
                        onCheckedChange = { state.avoid = it }
                    )
                }

                // Conditionally display Duration and Frequency fields
                if (!state.avoid) {
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
                }

                Spacer(modifier = Modifier.padding(6.dp))
                Divider()
                Spacer(modifier = Modifier.padding(6.dp))

                // Cancel and Save Buttons
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.padding(30.dp))
                    Button(onClick = { scope.launch { viewModel.saveHabit() } }) {
                        Text(text = "Save Habit")
                    }
                }

                // Error Message
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
