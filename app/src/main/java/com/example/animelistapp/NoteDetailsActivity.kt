package com.example.animelistapp

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageButton
import android.os.Bundle
import com.example.animelistapp.R
import android.content.Intent
import android.view.View
import com.example.animelistapp.Utility
import com.google.android.gms.tasks.OnCompleteListener
import java.lang.Void
import android.widget.Toast
import com.example.animelistapp.NoteModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class NoteDetailsActivity : AppCompatActivity() {
    var editTextTitle: EditText? = null
    var editMultiLineTextContent: EditText? = null
    var saveNoteButton: ImageButton? = null
    var title: String? = null
    var content: String? = null
    var docId: String? = null
    var deleteNoteButton: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        editTextTitle = findViewById(R.id.editTextTitle)
        editMultiLineTextContent = findViewById(R.id.editMultiLineTextContent)
        saveNoteButton = findViewById(R.id.saveNoteButton)
        deleteNoteButton = findViewById(R.id.imageBtnDeleteNote)
        val i = intent
        title = i.getStringExtra("title")
        content = i.getStringExtra("content")
        docId = i.getStringExtra("docId")
        editTextTitle.setText(title)
        editMultiLineTextContent.setText(content)
        saveNoteButton.setOnClickListener(View.OnClickListener { view: View? -> saveNote() })
        deleteNoteButton.setOnClickListener(View.OnClickListener { view: View? -> deleteNoteFromFirebase() })
    }

    private fun deleteNoteFromFirebase() {
        val documentReference = Utility.collectionReferenceForNotes.document(
            docId!!
        )
        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@NoteDetailsActivity,
                    "Note Deleted Successfully",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@NoteDetailsActivity,
                    task.exception!!.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveNote() {
        //agar koi savenote pe click kare toh kya hona chahiye
        val titleNote = editTextTitle!!.text.toString()
        val contentNote = editMultiLineTextContent!!.text.toString()
        val timestamp = Timestamp.now()
        if (titleNote.isEmpty()) {
            editTextTitle!!.error = "Title is Required"
            return
        }
        val noteModel = NoteModel()
        noteModel.titleOfNote = titleNote
        noteModel.contentOfNote = contentNote
        noteModel.timestamp = timestamp
        saveNoteToFirebase(noteModel)
    }

    private fun saveNoteToFirebase(noteModel: NoteModel) {
        val documentReference: DocumentReference
        documentReference = Utility.collectionReferenceForNotes.document(docId!!)
        //passing docId should update the note
        documentReference.set(noteModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@NoteDetailsActivity,
                    "Note Edited Successfully",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@NoteDetailsActivity,
                    task.exception!!.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}