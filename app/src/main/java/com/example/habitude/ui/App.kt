package com.example.habitude.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.habitude.ui.navigation.RootNavigation
import com.example.habitude.ui.navigation.RootNavigationDnB
import com.example.habitude.ui.theme.HabitudeTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    HabitudeTheme {
        RootNavigationDnB()
    }
}
