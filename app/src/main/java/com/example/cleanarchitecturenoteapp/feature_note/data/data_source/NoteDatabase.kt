package com.example.cleanarchitecturenoteapp.feature_note.data.data_source
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleanarchitecturenoteapp.feature_note.domain.model.Note

// Permet de créer les tables ou repr&sente l'architecture de la table

@Database (
    entities = [Note::class],
    version = 1
)

// abstract fun -> Méthode sans corps, doit être implémenter par les enfants

//class Dog : Animal() {
//    override fun makeSound() {
//        println("Wouf !")
//    }
//}

// abstract class signifie que c'est une class incomplète, elle sert juste de "base"
abstract class NoteDatabase: RoomDatabase() {

    // abstract val -> Propriété sans valeur, doit être definit dans les enfant
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}