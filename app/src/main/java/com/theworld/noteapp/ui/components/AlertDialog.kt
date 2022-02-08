package com.theworld.noteapp.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SimpleAlertDialog(
    title: String = "Please confirm",
    message: String = "Should I continue with the requested action?",
    isVisible: Boolean = false,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {

    if (isVisible) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = "Cancel") }
            },
            title = { Text(text = title) },
            text = { Text(text = message) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SimpleAlertDialogPreview() {
    SimpleAlertDialog(
        title = "Please confirm",
        message = "Should I continue with the requested action?",
        onConfirm = {},
        onDismiss = {}
    )
}

