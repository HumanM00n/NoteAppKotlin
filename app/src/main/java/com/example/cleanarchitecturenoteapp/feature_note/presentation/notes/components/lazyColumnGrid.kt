package com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarResult
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesEvent

@Composable
fun LazyColumnNote(
    note: NotesState,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.height(16.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(note.notes) { note ->
            NoteItem(
                note = note,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Screen.AddEditNoteScreen.route +
                                    "?noteId=${note.id}&noteColor=${note.color}"
                        )
                    },
                onDeleteClick = {
                    viewModel.onEvent(NotesEvent.DeleteNote(note))
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Note deleted",
                            actionLabel = "undo"
                        )

                        // Si l'action est établi
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(NotesEvent.RestoreNote)
                        }
                    }
                }
            )
        }
    }
}