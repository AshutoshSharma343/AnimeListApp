package com.example.animelistapp

import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.os.Bundle
import com.example.animelistapp.R
import java.lang.Runnable
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.os.Handler
import com.example.animelistapp.LogInActivity
import com.example.animelistapp.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById(R.id.progressBar2)
        Handler().postDelayed({
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser == null) {
                val i1 = Intent(this@SplashScreenActivity, LogInActivity::class.java)
                startActivity(i1)
            } else {
                val i2 = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(i2)
            }
            finish()
        }, 1200)
    }
}