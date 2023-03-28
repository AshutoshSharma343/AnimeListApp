package com.example.animelistapp

import android.content.Context
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.example.animelistapp.NoteModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import android.content.Intent
import com.example.animelistapp.NoteDetailsActivity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.animelistapp.R
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

//import java.util.ArrayList;
class NoteAdapter//as this is not a normal activity class we will have to make a context object for this class
//optional
//this.list = list;
//not needed as we are not manually putting data
// ArrayList<NoteModel> list;
    (options: FirestoreRecyclerOptions<NoteModel?>, var context: Context) :
    FirestoreRecyclerAdapter<NoteModel, NoteAdapter.ViewHolder>(options) {
    var isEditMode = false
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: NoteModel) {
        holder.textViewTitle.text = model.titleOfNote
        holder.textViewContent.text = model.contentOfNote
        // holder.textViewTimeStamp.setText(model.timestamp.toString());
        holder.itemView.setOnClickListener { view: View? ->
            isEditMode = false
            val intent = Intent(context, NoteDetailsActivity::class.java)
            intent.putExtra("title", model.titleOfNote)
            intent.putExtra("content", model.contentOfNote)
            // creating an id for this document to distinguish between saved and edited node
            val docId = this.snapshots.getSnapshot(position).id
            intent.putExtra("docId", docId)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapterlayout, parent, false)
        return ViewHolder(view)
    }

    //rather than creating an extra myViewHolder class ,we will create it in the note adapter class
    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView
        var textViewContent: TextView
        var textViewTimeStamp: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewContent = itemView.findViewById(R.id.textViewContent)
            textViewTimeStamp = itemView.findViewById(R.id.textViewTimeStamp)
        }
    }
}