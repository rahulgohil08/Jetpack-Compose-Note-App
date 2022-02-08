package com.theworld.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.theworld.noteapp.R
import com.theworld.noteapp.note.viewModel.NoteViewModel
import com.theworld.noteapp.ui.screens.HomeScreen
import com.theworld.noteapp.ui.screens.SaveNoteScreen
import com.theworld.noteapp.utils.Constants

@Composable
fun Navigation(navController: NavHostController,viewModel: NoteViewModel) {
    NavHost(navController, startDestination = BottomNavigationItem.Home.route) {
        composable(BottomNavigationItem.Home.route) {
            HomeScreen(navController, viewModel = viewModel)
        }

        composable(
            route = NavigationItem.SaveNoteScreen.route,
            arguments = listOf(
                navArgument(Constants.NOTE) {
//                    type = NavType.ParcelableType(UserNote::class.java)
                    type = NavType.StringType
                },
                navArgument(Constants.TITLE) {
                    type = NavType.StringType
                }
            )
        ) {
            val noteId: String? = it.arguments?.getString("note")
            val title: String =
                it.arguments?.getString("title") ?: stringResource(id = R.string.app_name)

            SaveNoteScreen(navController, title, noteId,viewModel)
        }

        composable(BottomNavigationItem.Movies.route) {
//            HomeScreen(navController)
        }

        composable(BottomNavigationItem.Profile.route) {
//            HomeScreen(navController)
        }
    }
}