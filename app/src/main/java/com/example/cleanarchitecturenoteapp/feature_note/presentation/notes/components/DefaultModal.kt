package com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cleanarchitecturenoteapp.feature_note.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultModal(
    note: Note?,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClick: (note: Note) -> Unit
) {
    if (isOpen) {
        BasicAlertDialog(
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Voulez-vous vraiment supprimer ${note?.title}?"
                    )

                    Spacer(modifier = Modifier.padding(24.dp))
                    TextButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Annuler")
                    }

                    TextButton(
                        onClick = {
                            onDismissRequest()
                            note?.let {
                                onDeleteClick(it)
                            } },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Supprimer")
                    }
                }
            }
        }
    }
}



