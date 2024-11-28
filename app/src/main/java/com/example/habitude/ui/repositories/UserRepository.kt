package com.example.habitude.ui.repositories

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignUpException(message: String?): RuntimeException(message) {
}
object UserRepository {
    suspend fun createUser(email: String, password: String, name: String, age: String, gender: String, height: String, weight: String) {
        // Assume Firebase Authentication for email and password sign-up
        val authResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()

        // Once user is created, store additional user details in Firestore
        val userId = authResult.user?.uid ?: throw SignUpException("User creation failed.")

        val userData = mapOf(
            "name" to name,
            "email" to email,
            "age" to age,
            "gender" to gender,
            "height" to height,
            "weight" to weight
        )

        // Store user data in Firestore
        Firebase.firestore.collection("users").document(userId).set(userData).await()
    }

    suspend fun loginUser(email: String, password: String) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        } catch(e: FirebaseAuthException){
            throw SignUpException(e.message)
        }
    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}