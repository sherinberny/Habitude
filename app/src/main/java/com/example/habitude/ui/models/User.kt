package com.example.habitude.ui.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class User(
    @DocumentId val id: String? = null, // Required to map document ID
    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val height: String = "",
    val weight: String = ""
)
