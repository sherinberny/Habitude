package com.example.habitude.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: @Composable () -> Unit,
    password: Boolean = false,
    error: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.background // Default background color
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor), // Apply background using Modifier
        visualTransformation =
        if (password) PasswordVisualTransformation() else VisualTransformation.None,
        isError = error,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = backgroundColor, // Set the background color here for the TextField itself
            focusedIndicatorColor = Color.White, // Color of the indicator when focused
            unfocusedIndicatorColor = Color.DarkGray, // Color of the indicator when unfocused
            errorIndicatorColor = MaterialTheme.colorScheme.error // Error indicator color when there's an error
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
}