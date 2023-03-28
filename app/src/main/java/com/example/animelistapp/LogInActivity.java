package com.example.animelistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button createLogInButton;
    ProgressBar progressBar;
    TextView createAccountBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassowrd);
        createLogInButton = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        createAccountBtnTextView = findViewById(R.id.createAccountBtnTextView);

        createLogInButton.setOnClickListener(view -> logInUser());
        createAccountBtnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogInActivity.this,CreateAccountActivity.class);

                startActivity(i);
            }
        });
    }

    private void logInUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValidated = validateData(email,password);
        if(!isValidated){
            return;
        }else{
            logInAccountInFirebase(email,password);

        }

    }

    private void logInAccountInFirebase(String email, String password) {
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    //login is success
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to main activity
                        Toast.makeText(LogInActivity.this,"Welcome weeb",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LogInActivity.this,"Email not Verified,Please verify your email.",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(LogInActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void changeInProgress(boolean inProgress) {
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createLogInButton.setVisibility(View.GONE);
        }else{

            progressBar.setVisibility(View.GONE);
            createLogInButton.setVisibility(View.VISIBLE);

        }

    }

    private boolean validateData(String email,String password){
        //validate the data that are input by user

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is Invalid");
            return false;
        }

        if(password.length()<5){
            passwordEditText.setError("Password must contain more than 5 character");
            return false;
        }
        return true;
    }



}