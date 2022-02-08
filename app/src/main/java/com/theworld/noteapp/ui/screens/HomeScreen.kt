package com.theworld.noteapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.theworld.noteapp.navigation.BottomNavigationItem
import com.theworld.noteapp.navigation.NavigationItem
import com.theworld.noteapp.note.Note
import com.theworld.noteapp.note.viewModel.NoteViewModel
import com.theworld.noteapp.topBar.Fab
import kotlinx.coroutines.launch


private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(navController: NavHostController, viewModel: NoteViewModel) {

    val notes = viewModel.notes.collectAsState()

    val listState = rememberLazyListState()
    // Remember a CoroutineScope to be able to launch

    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
        floatingActionButton = {
            Fab {
                coroutineScope.launch {
                    /*scaffoldState.snackbarHostState.showSnackbar(
                        message = "This is your message",
                        actionLabel = "Do something."
                    )*/


                    navController.navigate(
                        NavigationItem.SaveNoteScreen.routeWithTitleAndNote(
                            title = "Create Note",
                        )
                    ) {
                        popUpTo(BottomNavigationItem.Home.route)
                    }
                }
            }
        }
    ) {

        Column {
            /*Button(
                onClick = {
                    coroutineScope.launch {
                        // Animate scroll to the first item
                        listState.animateScrollToItem(index = 0)
                    }
                }
            ) {
                Text(text = "Scroll to TOP")
            }*/

            LazyColumn(state = listState) {
                items(notes.value.sortedByDescending { it.title }) { note ->


                    Note(
                        note = note,
                        onNoteClick = {

                            Log.e(TAG, "HomeScreen:CLICKED NOTE ID ${it.id}")


                            navController.navigate(
                                NavigationItem.SaveNoteScreen.routeWithTitleAndNote(
                                    title = "Save Note",
                                    note = it.id
                                )
                            )

                            /*navController.navigate(
                                NavigationItem.SaveNoteScreen.routeWithTitleAndNote(
                                    title = "Save Note",
                                    note = it
                                )
                            )*/
                        },
                        onNoteCheckedChange = {
                            viewModel.onNoteCheckedChange(it)
                        }
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController(), viewModel())
}
