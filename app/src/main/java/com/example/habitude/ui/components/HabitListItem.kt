package com.example.habitude.ui.components

import android.content.res.Resources
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
import com.example.habitude.ui.models.Habit

val doColor = Color(0xFF3F6C51)
val dontColor = Color(0xFFDD4050)
val checkedColor = Color(0xFFAFCFF8)
val uncheckedColor = Color.White

@Composable
fun HabitListItem(
    habit: Habit,
    toggle: (checked: Boolean) -> Unit = {},
    onEditPressed: () -> Unit = {}
) {
  val typeOfHabit = if (habit.avoid == true) "DON'T: " else "DO: "
  val habitColor = if (habit.avoid == true) dontColor else doColor
  var isChecked by remember { mutableStateOf(habit.completed ?: false) }
  val backgroundColor by animateColorAsState(
    targetValue = if (isChecked) checkedColor else uncheckedColor, label = ""
  )
  val rotationDegrees by
      animateFloatAsState(
          targetValue = if (isChecked) 720f else 0f,
          animationSpec = tween(durationMillis = 1000),
          label = ""
          )
  Surface(
      modifier =
          Modifier.fillMaxWidth().padding(16.dp).border(2.dp, habitColor).graphicsLayer {
            rotationX = rotationDegrees
            cameraDistance = 12f * Resources.getSystem().displayMetrics.density
          },
      shadowElevation = 4.dp,
      color = backgroundColor) {
        Column {
          Row(
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth()) {
                habit.title?.let {
                  Text(
                      text = typeOfHabit + it,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
                  )
                }
                IconButton(onClick = { onEditPressed() }) {
                  Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                }
              }
          Divider()
          Row(
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth()) {
                Text(text = "Mark as completed:", modifier = Modifier.padding(8.dp))
                habit.completed?.let {
                  Checkbox(
                      checked = it,
                      onCheckedChange = {isChecked = it
                                        toggle(it)},
                      modifier = Modifier.padding(6.dp))
                }
              }
        }
      }
}
