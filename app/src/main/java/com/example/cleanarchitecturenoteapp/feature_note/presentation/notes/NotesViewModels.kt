package com.example.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModels @Inject constructor(

    private val noteUseCases: NoteUseCases
): ViewModel() {

    // VARIABLE D'ETAT
    private val _state = mutableStateOf<NotesState>(NotesState())
    val state : State<NotesState> = _state

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {

            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotes
                }
            }

            is NotesEvent.RestoreNote -> {

            }

            // Change l'état de visibilité de la note
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

        }
    }
}
