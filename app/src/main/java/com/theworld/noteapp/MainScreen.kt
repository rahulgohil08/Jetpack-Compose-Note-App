package com.theworld.noteapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.theworld.noteapp.navigation.BottomNavigationBar
import com.theworld.noteapp.navigation.BottomNavigationItem
import com.theworld.noteapp.navigation.Navigation
import com.theworld.noteapp.note.viewModel.NoteViewModel
import com.theworld.noteapp.topBar.TopBar
import com.theworld.noteapp.utils.Navigator


@Composable
fun MainScreen(viewModel: NoteViewModel) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
        /*snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                // custom snackbar with the custom colors
                Snackbar(
                    actionColor = Green,
                    contentColor = Color.White,
                    snackbarData = data,
                    shape = RectangleShape
                )
            }
        },*/

        topBar = {
            if (currentRoute in listOf(
                    BottomNavigationItem.Home.route,
                    BottomNavigationItem.Movies.route,
                    BottomNavigationItem.Profile.route,
                )
            ) {
                TopBar()
            }
        },
        bottomBar = {
            if (currentRoute in listOf(
                    BottomNavigationItem.Home.route,
                    BottomNavigationItem.Movies.route,
                    BottomNavigationItem.Profile.route,
                )
            ) {
                BottomNavigationBar(navController)
            }
        }

    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController = navController, viewModel)
        }
    }

}


