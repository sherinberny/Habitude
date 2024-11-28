package com.example.habitude.ui.components

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.habitude.ui.models.Habits


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitListFromBoth(
    habit: Habits,
    toggle: (checked: Boolean) -> Unit = {},
    onEditPressed: () -> Unit = {}
) {
    val typeOfHabit = if (habit.avoid == true) "DON'T: " else "DO: "
    val habitColor = if (habit.avoid == true) Color(0xFF950606) else Color(0xFF570C3E) // Use vibrant colors for distinction
    var isChecked by remember { mutableStateOf(habit.completed ?: false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) Color(0xFFD7FFE0) else Color(0xFFF8F9FA), // Subtle green or neutral gray
        label = ""
    )
    val rotationDegrees by animateFloatAsState(
        targetValue = if (isChecked) 720f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = ""
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(1.dp, habitColor)
            .graphicsLayer {
                rotationX = rotationDegrees
                cameraDistance = 16f * Resources.getSystem().displayMetrics.density
            },
        shadowElevation = 6.dp,
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$typeOfHabit ${habit.title.orEmpty()}",
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium.copy(
                        color = habitColor,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onEditPressed() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Habit",
                        tint = Color.Gray
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tracked Progress",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Gray
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Completed: ${habit.totalTracked} / ${habit.daily_duration}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                // Remove missed dates text field and replace it with the HabitCalendar
                HabitCalendar(habit = habit, modifier = Modifier.padding(top = 5.dp))
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Mark as Completed:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        toggle(it)
                    },
                    colors = androidx.compose.material3.CheckboxDefaults.colors(
                        checkedColor = habitColor,
                        uncheckedColor = Color.LightGray
                    )
                )
            }
        }
    }
}
