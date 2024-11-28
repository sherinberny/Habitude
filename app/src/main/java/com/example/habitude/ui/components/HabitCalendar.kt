package com.example.habitude.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.habitude.ui.models.Habits
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitCalendar(
    habit: Habits,
    modifier: Modifier = Modifier
) {
    // Parse start date from habit.start_date
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Adjust format if necessary
    val startDate = try {
        LocalDate.parse(habit.start_date, formatter)
    } catch (e: Exception) {
        LocalDate.now() // Fallback to today if parsing fails
    }

    val today = LocalDate.now()
    val missedDates = habit.skippedDates.mapNotNull {
        try {
            LocalDate.parse(it, formatter)
        } catch (e: Exception) {
            null // Ignore invalid dates
        }
    }
    val trackedDates = habit.trackedDates.mapNotNull {
        try {
            LocalDate.parse(it, formatter)
        } catch (e: Exception) {
            null // Ignore invalid dates
        }
    }
    val totalTrackedDates = (0 until habit.totalTracked).map {
        startDate.plusDays(it.toLong())
    }

    // Get the current month and year
    val currentMonth = today.month
    val currentYear = today.year

    // Get the first and last day of the current month
    val firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1)
    val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())

    // Get the first day of the week for the current month (so the calendar aligns correctly)
    val firstDayOfWeek = firstDayOfMonth.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)

    // Generate the list of all dates for the current month (November only)
    val dateRange = mutableListOf<LocalDate>()
    var currentDate = firstDayOfWeek

    // Populate date range until the end of the current month
    while (currentDate <= lastDayOfMonth) {
        dateRange.add(currentDate)
        currentDate = currentDate.plusDays(1)
    }
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun")

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Habit Calendar for ${currentMonth.name}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp)) // Space between header and grid

        // Create a grid to display the calendar
        LazyVerticalGrid(
            columns = GridCells.Fixed(7), // 7 columns, one for each day of the week
            modifier = Modifier
                .padding(4.dp)
                .height(250.dp) // Set a fixed height to avoid infinite constraints
        ) {
            items(dateRange) { date ->
                val backgroundColor = when {
                    missedDates.contains(date) -> Color(0xFF950606)
                    trackedDates.contains(date) -> MaterialTheme.colorScheme.primary
                    else ->
                    {
                        if(date.month == today.month)
                        {
                            Color.Gray
                        }
                        else
                        {
                            Color.LightGray
                        }

                    }
                }

                Surface(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp),
                    color = backgroundColor,
                    shape = MaterialTheme.shapes.small
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}


/*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitCalendar(
    habit: Habits,
    modifier: Modifier = Modifier
) {
    // Parse start date from habit.start_date
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Adjust format if necessary
    val startDate = try {
        LocalDate.parse(habit.start_date, formatter)
    } catch (e: Exception) {
        LocalDate.now() // Fallback to today if parsing fails
    }

    val today = LocalDate.now()
    val missedDates = habit.skippedDates.mapNotNull {
        try {
            LocalDate.parse(it, formatter)
        } catch (e: Exception) {
            null // Ignore invalid dates
        }
    }
    val totalTrackedDates = (0 until habit.totalTracked).map {
        startDate.plusDays(it.toLong())
    }

    // Generate the complete date range for the current month (starting from the 1st)
    val currentMonth = today.month
    val firstDayOfMonth = LocalDate.of(today.year, currentMonth, 1)
    val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())

    // Get the first day of the week (Sunday or Monday, depending on locale)
    val firstDayOfWeek = firstDayOfMonth.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)

    // Calendar layout (to display a full month)
    val dateRange = generateSequence(firstDayOfWeek) { it.plusDays(1) }
        .takeWhile { it <= lastDayOfMonth }
        .toList()

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Habit Calendar for ${currentMonth.name}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(7), // 7 days in a week (columns for Sun to Sat)
            modifier = Modifier
                .padding(4.dp)
                .height(400.dp) // Set a fixed height to avoid infinite constraints
        ) {
            items(dateRange) { date ->
                val backgroundColor = when {
                    missedDates.contains(date) -> Color.Red
                    totalTrackedDates.contains(date) -> Color.Green
                    else -> Color.LightGray
                }

                Surface(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp),
                    color = backgroundColor,
                    shape = MaterialTheme.shapes.small
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}
*/
