package com.example.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components.DefaultModal
import com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import com.example.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModels = hiltViewModel()
) {
    val state = viewModel.state.value
    val countNote = state.notes.size
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedNote by remember { mutableStateOf<Note?>(null) }
    var isOpen by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)

                    scope.launch {
                        snackbarHostState.showSnackbar("Add Note clicked")
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vous avez ${countNote} notes",
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                OrderSection (
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),

                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(2),
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(230.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },

                        onOpenModal = {
                            selectedNote = note
                            isOpen = true
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        DefaultModal(
            note = selectedNote,
            isOpen = isOpen,
            onDismissRequest = {
                isOpen = false
            },
            onDeleteClick = { note ->
                viewModel.onEvent(NotesEvent.DeleteNote(note))
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Note supprimée",
                        actionLabel = "Annuler"
                    )

                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NotesEvent.RestoreNote)
                    }
                }
            }

        )
    }
}