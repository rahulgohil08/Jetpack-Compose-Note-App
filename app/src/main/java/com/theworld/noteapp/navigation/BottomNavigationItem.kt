package com.theworld.noteapp.navigation

import android.content.res.Resources
import android.provider.Settings.Global.getString
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.theworld.noteapp.R
import com.theworld.noteapp.data.UserNote
import com.theworld.noteapp.utils.Constants.NOTE
import com.theworld.noteapp.utils.Constants.TITLE

sealed class BottomNavigationItem(
    var route: String,
    var icon: ImageVector? = null,
    var title: String? = null
) {
    object Home : BottomNavigationItem("home", Icons.Default.Home, "Home")
    object Movies : BottomNavigationItem("movies", Icons.Default.ThumbUp, "Movies")
    object Profile : BottomNavigationItem("profile", Icons.Default.Person, "Profile")

}

sealed class NavigationItem(
    var route: String,
    var title: String = "Note App",
) {
    object SaveNoteScreen : NavigationItem("save_note/{$TITLE}?note={$NOTE}", "Save Note") {
        fun routeWithTitleAndNote(note: String? = null, title: String = "Save Note"): String {
            return "save_note/$title?note=$note"
        }
    }
}