package com.example.habitude.ui.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitude.ui.models.Habit
import com.example.habitude.ui.models.Habit_Tracker
import com.example.habitude.ui.models.Habits
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

object HabitRepository {

  val habitCache = mutableListOf<Habit>()
  val habitCacheFromBoth = mutableListOf<Habits>()
  val donthabitCache = mutableListOf<Habit>()
  val donthabitsCache = mutableListOf<Habits>()
  val dohabitCache = mutableListOf<Habit>()
  val dohabitsCache = mutableListOf<Habits>()
  val habitTrackerCache = mutableListOf<Habit_Tracker>()
  private var docacheInitialized = false
  private var dontcacheInitialized = false
  private var cacheInitialized = false


  suspend fun createHabit(title: String, start_date: String, frequency: String, daily_duration: String, doIt: Boolean): Habit {
    val doc = Firebase.firestore.collection("habits").document()
    val habit =
        Habit(
            title = title,
            start_date = start_date,
            frequency = frequency,
            daily_duration = daily_duration,
            completed = false,
            avoid = doIt,
            userId = UserRepository.getCurrentUserId(),
            id = doc.id)
    doc.set(habit).await()
    habitCache.add(habit)
    return habit
  }

  suspend fun getHabits(doordont: Boolean): List<Habit> {
    if(doordont) {
        if (!dontcacheInitialized) {
            val snapshot =
                Firebase.firestore
                    .collection("habits")
                    .whereEqualTo("userId", UserRepository.getCurrentUserId())
                    .whereEqualTo("avoid", doordont)
                    .get()
                    .await()
            donthabitCache.addAll(snapshot.toObjects())
            dontcacheInitialized = true
        }
        return donthabitCache
    }
      else{
        if (!docacheInitialized) {
            val snapshot =
                Firebase.firestore
                    .collection("habits")
                    .whereEqualTo("userId", UserRepository.getCurrentUserId())
                    .whereEqualTo("avoid", doordont)
                    .get()
                    .await()
            dohabitCache.addAll(snapshot.toObjects())
            docacheInitialized = true
        }

        return dohabitCache
    }

  }

    suspend fun getHabits(): List<Habit> {
        if (!cacheInitialized) {
            val snapshot =
                Firebase.firestore
                    .collection("habits")
                    .whereEqualTo("userId", UserRepository.getCurrentUserId())
                    .get()
                    .await()
            habitCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }
        return habitCache
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getHabitsFromDo(): List<Habits> {
        dohabitsCache.clear()
        val snapshot = Firebase.firestore
            .collection("habits")
            .whereEqualTo("userId", UserRepository.getCurrentUserId())
            .whereEqualTo("avoid", false)
            .get()
            .await()

        val habits = snapshot.toObjects<Habits>()
        val habitTrackerCollection = Firebase.firestore.collection("habit_tracker")

        for (habit in habits) {
            val trackedSnapshot = habitTrackerCollection
                .whereEqualTo("habitid", habit.id)
                .get()
                .await()
            val trackedDates = trackedSnapshot.documents.mapNotNull { it.getString("date") }

            // Calculate expected dates
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val startDate = LocalDate.parse(habit.start_date, formatter)
            //   val expectedDates = (0 until (habit.frequency?.toInt() ?: 0)).map { startDate.plusDays(it.toLong()).toString() }

            // Calculate the end date using duration (assuming `habit.duration` is in days)
            val endDate = startDate.plusDays(habit.daily_duration?.toLong() ?: 0L)

            // Get today's date
            val today = LocalDate.now()

            // Only calculate expected dates up to today if the end date is in the future
            val expectedDates = if (endDate.isAfter(today)) {
                // Only include dates up to today
                (0 until (habit.daily_duration?.toInt() ?: 0))
                    .map { startDate.plusDays(it.toLong()).toString() }
                    .filter { LocalDate.parse(it, formatter).isBefore(today.plusDays(1)) }
            } else {
                // Include all expected dates up to the end date
                (0 until (habit.daily_duration?.toInt() ?: 0))
                    .map { startDate.plusDays(it.toLong()).toString() }
            }


            // Calculate skipped dates and total tracked
            habit.skippedDates = expectedDates.filter { it !in trackedDates }
            habit.totalTracked = trackedDates.size
            habit.trackedDates = trackedDates
        }

        dohabitsCache.addAll(habits)

        return dohabitsCache
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getHabitsFromDont(): List<Habits> {
        donthabitsCache.clear()
        val snapshot = Firebase.firestore
            .collection("habits")
            .whereEqualTo("userId", UserRepository.getCurrentUserId())
            .whereEqualTo("avoid", true)
            .get()
            .await()

        val habits = snapshot.toObjects<Habits>()
        val habitTrackerCollection = Firebase.firestore.collection("habit_tracker")

        for (habit in habits) {
            val trackedSnapshot = habitTrackerCollection
                .whereEqualTo("habitid", habit.id)
                .get()
                .await()
            val trackedDates = trackedSnapshot.documents.mapNotNull { it.getString("date") }
// Get today's date
            val today = LocalDate.now()
            // Calculate expected dates
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val startDate = LocalDate.parse(habit.start_date, formatter)

            // Only calculate expected dates up to today if the end date is in the future
            val expectedDates =
                (0..ChronoUnit.DAYS.between(startDate, today))
                    .map { startDate.plusDays(it.toLong()).format(formatter) }



            // Calculate skipped dates and total tracked
            habit.skippedDates = expectedDates.filter { it !in trackedDates }
            habit.totalTracked = trackedDates.size
            habit.trackedDates = trackedDates
        }

        donthabitsCache.addAll(habits)

        return donthabitsCache
    }



    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getHabitsFromBoth(): List<Habits> {
        val snapshot = Firebase.firestore
            .collection("habits")
            .whereEqualTo("userId", UserRepository.getCurrentUserId())
            .get()
            .await()

        val habits = snapshot.toObjects<Habits>()
        val habitTrackerCollection = Firebase.firestore.collection("habit_tracker")

        for (habit in habits) {
            val trackedSnapshot = habitTrackerCollection
                .whereEqualTo("habitid", habit.id)
                .get()
                .await()
            val trackedDates = trackedSnapshot.documents.mapNotNull { it.getString("date") }

            // Calculate expected dates
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val startDate = LocalDate.parse(habit.start_date, formatter)
         //   val expectedDates = (0 until (habit.frequency?.toInt() ?: 0)).map { startDate.plusDays(it.toLong()).toString() }

            // Calculate the end date using duration (assuming `habit.duration` is in days)
            val endDate = startDate.plusDays(habit.frequency?.toLong() ?: 0L)

            // Get today's date
            val today = LocalDate.now()

            // Only calculate expected dates up to today if the end date is in the future
            val expectedDates = if (endDate.isAfter(today)) {
                // Only include dates up to today
                (0 until (habit.frequency?.toInt() ?: 0))
                    .map { startDate.plusDays(it.toLong()).toString() }
                    .filter { LocalDate.parse(it, formatter).isBefore(today.plusDays(1)) }
            } else {
                // Include all expected dates up to the end date
                (0 until (habit.frequency?.toInt() ?: 0))
                    .map { startDate.plusDays(it.toLong()).toString() }
            }


            // Calculate skipped dates and total tracked
            habit.skippedDates = expectedDates.filter { it !in trackedDates }
            habit.totalTracked = trackedDates.size
        }

        habitCacheFromBoth.addAll(habits)

        return habitCacheFromBoth
    }


    suspend fun updateHabit(habit: Habit){
        Firebase.firestore.collection("habits").document(habit.id!!).set(habit).await()
        val oldHabitIndex = habitCache.indexOfFirst { it.id == habit.id }
        habitCache[oldHabitIndex] = habit
    }

  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun updateHabitTracker(habitid: String) {
      val tracker_doc = Firebase.firestore.collection("habit_tracker").document()
   //   val formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
      val formattedDate = LocalDate.now()
      val habit_tracker =
          Habit_Tracker(
              date = formattedDate.toString(),
              habitid = habitid,
              id = tracker_doc.id)
      tracker_doc.set(habit_tracker).await()
      habitTrackerCache.add(habit_tracker)
  }
}
