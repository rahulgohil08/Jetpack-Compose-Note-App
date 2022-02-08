package com.theworld.noteapp.note.viewModel


import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theworld.noteapp.data.UserNote
import com.theworld.noteapp.navigation.BottomNavigationItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


private const val TAG = "NoteViewModel"

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<UserNote>>(mutableListOf())
    val notes = _notes.asStateFlow()


    private val _redirect = MutableSharedFlow<String>()
    val redirect = _redirect.asSharedFlow()


    init {
        Log.e(TAG, "INIT CALLED: ")
        fetchNotes()
    }

    fun onNoteCheckedChange(note: UserNote) {
        val index = notes.value.toMutableList().indexOf(note)
        val list = notes.value.toMutableList().also {
            it[index] = note.copy(isChecked = !note.isChecked)
        }
        _notes.value = list.toList()
    }

    fun updateNote(note: UserNote) = viewModelScope.launch {
        val list = notes.value.toMutableList()
        val data: UserNote? = list.find { it.id == note.id }

        data?.let {
            list.set(index = list.indexOf(it), note)

            Log.e(TAG, "updateNote: $list")
            _notes.value = list.toList()

            _redirect.emit(BottomNavigationItem.Home.route)
        }

            ?: kotlin.run {
                saveNote(note)
                _redirect.emit(BottomNavigationItem.Home.route)
                return@launch
            }


    }


    fun deleteNote(note: UserNote) = viewModelScope.launch {
        val list = notes.value.toMutableList()
        val data: UserNote? = list.find { it.id == note.id }

        data?.let {
            list.remove(it)
            _notes.value = list
            _redirect.emit(BottomNavigationItem.Home.route)
        }

    }


    private fun saveNote(note: UserNote) {
        val list = notes.value.toMutableList()
        list.add(note)
        _notes.value = list.toList()
    }


    private fun fetchNotes() {

        val notes = mutableListOf<UserNote>()

        repeat(20) {

            val id = it + 1
            notes.add(
                UserNote(
                    title = "Title $id",
                    color = listOf(
                        Color.Black,
                        Color.Green,
                        Color.Red,
                        Color.White,
                        Color.Gray
                    ).random()
                )
            )
        }



        _notes.value = notes
    }


    fun fetchNoteById(noteId: String): UserNote {
        val list = notes.value
        val isExist = list.any { it.id == noteId }

        Log.e(TAG, "fetchNoteById: IS ID EXIST $isExist")

        return list.find { it.id == noteId } ?: UserNote(
            title = "",
            content = "",
            color = Color.Green
        )
    }

}