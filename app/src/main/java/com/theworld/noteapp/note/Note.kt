package com.theworld.noteapp.note

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theworld.noteapp.data.UserNote

private const val TAG = "Note"

@Composable
fun Note(
    note: UserNote,
    onNoteClick: (UserNote) -> Unit = {},
    onNoteCheckedChange: (UserNote) -> Unit = {},
) {
    val backgroundShape: Shape = RoundedCornerShape(4.dp)

    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 1.dp)
            .clickable { onNoteClick(note) },
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            NoteColor(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp, end = 8.dp), // here
                color = note.color,
                size = 40.dp,
                border = 1.dp
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            ) {
                Text(
                    text = note.title,
                    maxLines = 1,
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        letterSpacing = 0.15.sp,
                    ),
                )
                Text(
                    text = note.content,
                    modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    maxLines = 1,
                    style = TextStyle( // here
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        letterSpacing = 0.25.sp
                    )
                )
            }


            Checkbox(
                checked = note.isChecked,
                onCheckedChange = {
                    val newNote = note.copy(isChecked = it)
                    Log.e(TAG, "${note.title}: is checked = $it")
                    onNoteCheckedChange(note)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically),
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotePreview() {
    Note(note = UserNote("1", "Note 1", "Content 1",Color.Black))
}