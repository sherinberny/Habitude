package com.example.habitude.ui.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitude.ui.models.Habit
import com.example.habitude.ui.models.Habit_Tracker
import com.example.habitude.ui.models.Habits
import com.example.habitude.ui.repositories.HabitRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import androidx.compose.runtime.State
import java.time.format.DateTimeFormatter

class HabitsScreenState {
  val _habits = mutableStateListOf<Habit>()
  val _habitsDo = mutableStateListOf<Habits>()
  val _habitsDont = mutableStateListOf<Habits>()
  val _habit_tracker = mutableStateListOf<Habit_Tracker>()
  val habits: List<Habit>
    get() = _habits
  val habitsDo: List<Habits>
  get() = _habitsDo
  val habitsDont: List<Habits>
    get() = _habitsDont
}

class HabitViewModel : ViewModel() {
  // Use mutableStateOf for observing the habit list state in Composables
  private val _habits = mutableStateOf<List<Habit>>(emptyList())
  val habits: State<List<Habit>> = _habits // Expose this as immutable state to Composables

  // Function to refresh habits data
  fun refreshHabits() {
    viewModelScope.launch {
      val freshHabits = HabitRepository.getHabits()
      _habits.value = freshHabits
    }
  }
}


class HabitsViewModel(application: Application) : AndroidViewModel(application) {
  val uiState = HabitsScreenState()

  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun getHabits(doordont: Boolean){
    val habits = HabitRepository.getHabits(doordont)
    uiState._habits.clear()
    uiState._habits.addAll(habits)
    // val habits = HabitRepository.getHabitsBasedOnDoDonts(doordont)
  /*  uiState._habitsAll.clear()
    uiState._habitsAll.addAll(habits)*/
  }

  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun getHabitsFromDo(){
    val habits = HabitRepository.getHabitsFromDo()
    uiState._habitsDo.clear()
    uiState._habitsDo.addAll(habits)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun getHabitsFromDont(){
    val habits = HabitRepository.getHabitsFromDont()
    uiState._habitsDont.clear()
    uiState._habitsDont.addAll(habits)
  }


/*  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun getHabitsFromBoth() {
    val habits = HabitRepository.getHabitsFromBoth()
    uiState._habitsAll.clear()
    uiState._habitsAll.addAll(habits)
  }*/

  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun toggleHabitCompletion(habitid: String){

   /* val habitCopy = habit.copy(completed = !(habit.completed ?: false))
    uiState._habits[uiState._habits.indexOf(habit)] = habitCopy
    HabitRepository.updateHabit(habitCopy)*/
    val firestore = Firebase.firestore
    val habitTrackerCollection = firestore.collection("habit_tracker")

    try {
      // Query to check for an entry with the specified date and habit_id
      val formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    //  val formattedDate = LocalDate.now()
      val querySnapshot = habitTrackerCollection
        .whereEqualTo("date", formattedDate)
        .whereEqualTo("habitid", habitid)
        .get()
        .await()

      if (!querySnapshot.isEmpty) {
        // If an entry exists, delete the document
        for (document in querySnapshot.documents) {
          habitTrackerCollection.document(document.id).delete().await()
        }
       // println("Entry with date: $date and habit_id: $habitId deleted.")
      } else {
        // If no entry exists, insert a new document
        HabitRepository.updateHabitTracker(habitid)
      }
    } catch (e: Exception) {
      println("Error toggling habit tracker entry: ${e.message}")
    }
  }
}
