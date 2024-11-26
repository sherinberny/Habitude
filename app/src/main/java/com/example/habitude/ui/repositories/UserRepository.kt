package com.example.habitude.ui.repositories

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignUpException(message: String?): RuntimeException(message) {
}
object UserRepository {
    suspend fun createUser(email: String, password: String) {
        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        } catch(e: FirebaseAuthException){
            throw SignUpException(e.message)
        }
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