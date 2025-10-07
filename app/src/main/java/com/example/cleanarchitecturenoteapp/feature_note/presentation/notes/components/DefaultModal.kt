package com.example.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cleanarchitecturenoteapp.feature_note.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultModal(
    modifier: Modifier,
    note: Note?,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClick: (note: Note) -> Unit
) {
    if (isOpen) {
        BasicAlertDialog(
            modifier = Modifier
                .background(color = Color.Transparent),
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = "${note?.title}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.padding(14.dp))
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = "Voulez-vous vraiment supprimer cette note?"
                    )

                    Spacer(modifier = Modifier.padding(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        /*----------------------------------
                        |            UNDO BUTTON           |
                        -----------------------------------*/
                        TextButton(
                            onClick = {
                                onDismissRequest()
                            },
                        ) {
                            Text("Annuler")
                        }

                        /*----------------------------------
                        |            DELETE BUTTON          |
                        -----------------------------------*/
                        TextButton(
                            onClick = {
                                onDismissRequest()
                                note?.let {
                                    onDeleteClick(it)
                                } },
                        ) {
                            Text("Supprimer")
                        }
                    }
                }
            }
        }
    }
}



