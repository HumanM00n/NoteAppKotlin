package com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.example.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    OnOrderChange: (NoteOrder) -> Unit
) {
}