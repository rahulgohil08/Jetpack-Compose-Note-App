package com.theworld.noteapp.data

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class UserNote(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String = "Hello Cool Content!!",
    val color: Color,
    var isChecked: Boolean = false,
) : Parcelable, Serializable
