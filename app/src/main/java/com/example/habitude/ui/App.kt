package com.example.habitude.ui

import androidx.compose.runtime.Composable
import com.example.habitude.ui.navigation.RootNavigation
import com.example.habitude.ui.theme.HabitudeTheme

@Composable
fun App() {
    HabitudeTheme {
        RootNavigation()
    }
}
