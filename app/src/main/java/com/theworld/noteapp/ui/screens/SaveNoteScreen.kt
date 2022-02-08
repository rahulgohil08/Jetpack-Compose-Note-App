package com.theworld.noteapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.theworld.noteapp.data.UserNote
import com.theworld.noteapp.navigation.NavigationItem
import com.theworld.noteapp.note.NoteColor
import com.theworld.noteapp.note.viewModel.NoteViewModel
import com.theworld.noteapp.ui.components.DialogViewModel
import com.theworld.noteapp.ui.components.SimpleAlertDialog
import kotlinx.coroutines.flow.collectLatest


private const val TAG = "SaveNoteScreen"

@Composable
fun SaveNoteScreen(
    navController: NavHostController,
    title: String,
    noteId: String?,
    viewModel: NoteViewModel
) {

    val alertViewModel = viewModel<DialogViewModel>()
    val noteData = viewModel.fetchNoteById(noteId = noteId.toString())

    val note = remember { mutableStateOf(noteData) }

    RedirectWhenRequired(viewModel, navController)


    Scaffold(
        topBar = {

            SaveNoteTopAppBar(
                title = title,
                isEditingMode = noteId != "null",
                navController = navController,
                onSaveNoteClick = {
                    Log.e(TAG, "Save Clicked")
                    viewModel.updateNote(note.value)
                },
                onDeleteNoteClick = {
                    Log.e(TAG, "Delete Clicked")
//                    viewModel.deleteNote(note.value)
                    alertViewModel.onOpenDialogClicked()
                }
            )

        },
        content = {
            SaveNoteContent(
                note = note.value,
                alertViewModel = alertViewModel,
                onNoteChange = { updateNoteEntry ->
//                    viewModel.onNoteEntryChange(updateNoteEntry)

                    note.value = updateNoteEntry

                    Log.e(TAG, "NOTE CHANGED is : $updateNoteEntry")
                },
                onConfirmDelete = {
                    alertViewModel.onDialogConfirm()
                    viewModel.deleteNote(note.value)
                }
            )
        }
    )
}

@Composable
private fun RedirectWhenRequired(
    viewModel: NoteViewModel,
    navController: NavHostController
) {
    val someState = viewModel.redirect.collectAsState(initial = "")

    LaunchedEffect(key1 = someState.value.isNotBlank()) {
        viewModel.redirect.collectLatest {
            navController.navigate(it) {
                popUpTo(NavigationItem.SaveNoteScreen.route) {
                    inclusive = true
                }
                Log.e(TAG, "SaveNoteScreen: REDIRECT TO : $it")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SaveNotePreview() {
    SaveNoteScreen(rememberNavController(), "Save Note", "1", viewModel())
}


/*------------------------------- Save Note Top Bar ------------------------*/

@Composable
fun SaveNoteTopAppBar(
    title: String = "",
    isEditingMode: Boolean,
    navController: NavController,
    onSaveNoteClick: () -> Unit,
    onDeleteNoteClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Save Note Button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            // Save note action icon
            IconButton(onClick = onSaveNoteClick) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = "Save Note"
                )
            }

            // Delete action icon (show only in editing mode)
            if (isEditingMode) {
                IconButton(onClick = onDeleteNoteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note Button",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }

        }
    )
}


@Preview
@Composable
fun SaveNoteTopAppBarPreview() {
    SaveNoteTopAppBar(
        isEditingMode = true,
        navController = rememberNavController(),
        onSaveNoteClick = {},
        onDeleteNoteClick = {}
    )
}


/*------------------------------- Save Note Content ------------------------*/

@Composable
private fun SaveNoteContent(
    note: UserNote,
    alertViewModel: DialogViewModel = viewModel(),
    onNoteChange: (UserNote) -> Unit,
    onConfirmDelete: () -> Unit = {},
) {

    /*val title by remember {
        mutableStateOf(note.title)
    }
    val content by remember {
        mutableStateOf(note.content)
    }*/

    val isDialogVisible = alertViewModel.showDialog.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .fillMaxWidth()
                .background(Color.Transparent, RoundedCornerShape(8.dp))
                .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter)
        ) {
            SimpleAlertDialog(
                title = "Delete Note",
                message = "Are you sure?",
                isVisible = isDialogVisible.value,
                onConfirm = onConfirmDelete,
                onDismiss = {
                    alertViewModel.onDialogDismiss()
                }
            )
        }



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                ContentTextField(
                    label = "Title",
                    text = note.title,
                    onTextChange = { newTitle ->
                        onNoteChange.invoke(note.copy(title = newTitle))
                    }
                )

                ContentTextField(
                    modifier = Modifier
                        .heightIn(max = 240.dp)
                        .padding(top = 16.dp),
                    label = "Body",
                    text = note.content,
                    onTextChange = { newContent ->
                        onNoteChange.invoke(note.copy(content = newContent))
                    }
                )

                PickedColor(color = note.color)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun SaveNoteContentPreview() {
    SaveNoteContent(
        note = UserNote(id = "1", title = "Title", content = "content", Color.Cyan),
        onNoteChange = {}
    )
}

/*------------------------------- Picked Color ------------------------*/

@Composable
private fun PickedColor(color: Color) {
    Row(
        Modifier
            .padding(8.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Picked color",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        NoteColor(
            color = color,
            size = 40.dp,
            border = 1.dp,
            modifier = Modifier.padding(4.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PickedColorPreview() {
    PickedColor(Color.Cyan)
}


/*------------------------------- TextField ------------------------*/

@Composable
private fun ContentTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onTextChange: (String) -> Unit
) {

    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        )
    )
}


@Preview(showBackground = true)
@Composable
fun ContentTextFieldPreview() {
    ContentTextField(
        label = "Title",
        text = "",
        onTextChange = {}
    )
}


/*------------------------------- Show Color Picker ------------------------*/

@Composable
private fun ColorPicker(
    colors: List<Color>,
    onColorSelect: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Color picker",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(colors) { color ->
                ColorItem(color, onColorSelect)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
    ColorPicker(
        colors = listOf(
            Color.White,
            Color.Red,
            Color.Black,
        )
    ) { }
}

/*------------------------------- Color Picker Item ------------------------*/

@Composable
fun ColorItem(
    color: Color,
    onColorSelect: (Color) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onColorSelect.invoke(color)
                }
            )
    ) {
        NoteColor(
            modifier = Modifier.padding(10.dp),
            color = color,
            size = 80.dp,
            border = 2.dp
        )
        Text(
            text = "Pick the Color",
            fontSize = 22.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorItemPreview() {
    ColorItem(MaterialTheme.colors.onPrimary) {}
}

