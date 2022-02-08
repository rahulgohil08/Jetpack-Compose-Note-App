package com.theworld.noteapp.topBar

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.theworld.noteapp.R

@Composable
fun Fab(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onFabClick.invoke() },
        backgroundColor = MaterialTheme.colors.primary,
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    )
}