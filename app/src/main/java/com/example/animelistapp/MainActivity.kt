package com.example.animelistapp

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import com.example.animelistapp.NoteAdapter
import android.os.Bundle
import com.example.animelistapp.R
import android.content.Intent
import com.example.animelistapp.AddNoteActivity
import com.example.animelistapp.Utility
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.example.animelistapp.NoteModel
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.example.animelistapp.LogInActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {
    var addNoteButton: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var menuButton: ImageButton? = null
    var noteAdapter: NoteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addNoteButton = findViewById(R.id.floatingActionButton)
        addNoteButton.setOnClickListener(View.OnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@MainActivity,
                    AddNoteActivity::class.java
                )
            )
        })
        recyclerView = findViewById(R.id.recyclerView)
        menuButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener(View.OnClickListener { view: View? -> showMenu() })
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
//get data from firebase and display in firebase


        //The Query class (and its subclass, DatabaseReference) are used for reading data. Listeners are attached,
        // and they will be triggered when the corresponding data changes.
        val query = Utility.collectionReferenceForNotes
            .orderBy("timestamp", Query.Direction.DESCENDING)
        //first we query the data with the timestamp,then we convert the query into options,this options we pass in notes adapter
        //and in note adapter we set data to the text view
        val options = FirestoreRecyclerOptions.Builder<NoteModel>()
            .setQuery(query, NoteModel::class.java).build()
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(options, this)
        recyclerView!!.adapter = noteAdapter
    }

    private fun showMenu() {
        //TODO DISPLAY MENU
        val popupMenu = PopupMenu(this, menuButton)
        popupMenu.menu.add("Logout")
        popupMenu.menu.add("Add note")
        popupMenu.menu.add("Grid Mode")
        popupMenu.menu.add("List Mode")
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
            if (menuItem.title === "Logout") {
                val i1 = Intent(this@MainActivity, LogInActivity::class.java)
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this@MainActivity, "You have been Logged out", Toast.LENGTH_SHORT)
                    .show()
                startActivity(i1)
                finish()
                return@OnMenuItemClickListener true
            }
            if (menuItem.title === "Add note") {
                val i2 = Intent(this@MainActivity, AddNoteActivity::class.java)
                startActivity(i2)
                return@OnMenuItemClickListener true
            }

            //TODO work of mode changing
            //TODO timeStamp addition
            false
        })
    }

    override fun onStart() {
        super.onStart()
        noteAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        noteAdapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        noteAdapter!!.notifyDataSetChanged()
    }
}