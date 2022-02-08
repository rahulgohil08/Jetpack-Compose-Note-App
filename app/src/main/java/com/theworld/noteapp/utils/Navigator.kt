package com.theworld.noteapp.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.theworld.noteapp.navigation.BottomNavigationItem

object Navigator {
    var currentScreen by mutableStateOf(BottomNavigationItem.Home.route)

    fun navigateTo(destination: String) {
        currentScreen = destination
    }

//    fun navigateTo(destination: String, popUpTo: String, inclusive: Boolean = false) {
//        currentScreen = destination
//    }
}