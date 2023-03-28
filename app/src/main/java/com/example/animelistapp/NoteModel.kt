package com.example.animelistapp

import com.google.firebase.Timestamp

//Custom datatype
class NoteModel {
    /*   public NoteModel(String title, String content, Timestamp timestamp) {
        this.titleOfNote = titleOfNote;
        this.contentOfTitle = contentOfTitle;
        this.timestamp = timestamp;
    }*/
    var titleOfNote: String? = null
    var contentOfNote: String? = null
    var timestamp: Timestamp? = null
}