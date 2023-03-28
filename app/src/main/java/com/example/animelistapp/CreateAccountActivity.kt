package com.example.animelistapp

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.os.Bundle
import com.example.animelistapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import android.util.Patterns
import android.view.View

class CreateAccountActivity : AppCompatActivity() {
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var confirmPasswordEditText: EditText? = null
    var createAccountButton: Button? = null
    var progressBar: ProgressBar? = null
    var loginBtnTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassowrd)
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword)
        createAccountButton = findViewById(R.id.button)
        progressBar = findViewById(R.id.progressBar)
        loginBtnTextView = findViewById(R.id.loginbtnTextView)
        createAccountButton.setOnClickListener(View.OnClickListener { view: View? -> createAccount() })
        loginBtnTextView.setOnClickListener(View.OnClickListener { view: View? -> finish() })
    }

    private fun createAccount() {
        val email = emailEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        val confirmPassword = confirmPasswordEditText!!.text.toString()
        val isValidated = validateData(email, password, confirmPassword)
        if (!isValidated) {
            return
        } else {
            createFirebaseAccount(email, password)
        }
    }

    //end of onCreate
    private fun createFirebaseAccount(email: String, password: String) {
        changeInProgress(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            this@CreateAccountActivity
        ) { task ->
            changeInProgress(false)
            if (task.isSuccessful) {
                //creating acc is done
                Toast.makeText(
                    this@CreateAccountActivity,
                    "Successfully create account,Check email to verify",
                    Toast.LENGTH_LONG
                ).show()
                firebaseAuth.currentUser!!.sendEmailVerification()
                firebaseAuth.signOut()
                finish()
            } else {
                //failure
                Toast.makeText(
                    this@CreateAccountActivity,
                    task.exception!!.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            progressBar!!.visibility = View.VISIBLE
            createAccountButton!!.visibility = View.GONE
        } else {
            progressBar!!.visibility = View.GONE
            createAccountButton!!.visibility = View.VISIBLE
        }
    }

    private fun validateData(email: String, password: String, confirmPassword: String): Boolean {
        //validate the data that are input by user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText!!.error = "Email is Invalid"
            return false
        }
        if (password.length < 5) {
            passwordEditText!!.error = "Password must contain more than 5 character"
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordEditText!!.error = "Password is invalid"
            return false
        }
        return true
    }
}