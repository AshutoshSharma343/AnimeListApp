package com.example.animelistapp

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageButton
import android.os.Bundle
import android.view.View
import com.example.animelistapp.R
import com.example.animelistapp.NoteModel
import com.example.animelistapp.Utility
import com.google.android.gms.tasks.OnCompleteListener
import java.lang.Void
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class AddNoteActivity : AppCompatActivity() {
    var editTextTitle: EditText? = null
    var editMultiLineTextContent: EditText? = null
    var saveNoteButton: ImageButton? = null
    var timestamp: Timestamp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        editTextTitle = findViewById(R.id.editTextTitle)
        editMultiLineTextContent = findViewById(R.id.editMultiLineTextContent)
        saveNoteButton = findViewById(R.id.saveNoteButton)
        saveNoteButton.setOnClickListener(View.OnClickListener { view: View? -> saveNote() })
    }

    private fun saveNote() {
//agar koi savenote pe click kare toh kya hona chahiye
        val title = editTextTitle!!.text.toString()
        val content = editMultiLineTextContent!!.text.toString()
        timestamp = Timestamp.now()
        if (title.isEmpty()) {
            editTextTitle!!.error = "Title is Required"
            return
        }
        val noteModel = NoteModel()
        noteModel.titleOfNote = title
        noteModel.contentOfNote = content
        noteModel.timestamp = timestamp
        saveNoteToFirebase(noteModel)
    }

    private fun saveNoteToFirebase(noteModel: NoteModel) {
        val documentReference: DocumentReference
        documentReference = Utility.collectionReferenceForNotes.document()
        documentReference.set(noteModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@AddNoteActivity, "Note Added Successfully", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                Toast.makeText(
                    this@AddNoteActivity,
                    task.exception!!.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}