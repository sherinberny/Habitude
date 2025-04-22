package com.example.habitude.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitude.ui.models.User
import com.example.habitude.ui.repositories.UserRepository
import com.example.habitude.ui.repositories.UserRepository.getCurrentUserId
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ProfileViewModel : ViewModel() {

    private val _userData = mutableStateOf<User?>(null)
    val userData: State<User?> = _userData

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val userId = getCurrentUserId() ?: return

        viewModelScope.launch {
            try {
                val userDocument = FirebaseFirestore.getInstance().collection("users").document(userId).get().await()
                val user = userDocument.toObject(User::class.java)
                _userData.value = user
            } catch (e: Exception) {
                _userData.value = null // Handle error scenario
            }
        }
    }
}
