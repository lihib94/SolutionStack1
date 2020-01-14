package com.example.myapplicationtemp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpUser extends AppCompatActivity {

    private EditText fNameField, lNameField, mEmailField, mPasswordField, mConPasswordField;
    private FirebaseAuth mAuth;
    private final boolean isManager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        mAuth = FirebaseAuth.getInstance();

        // initialisings
        mEmailField = (EditText) findViewById(R.id.emailInput);
        mPasswordField = (EditText) findViewById(R.id.passInput);
        mConPasswordField = (EditText) findViewById(R.id.passConInput);
        fNameField = (EditText) findViewById(R.id.firstInput);
        lNameField = (EditText) findViewById(R.id.lastInput);

        Button mainActivityBtn = (Button) findViewById(R.id.nextBtn);
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {
        final String mail = mEmailField.getText().toString();
        final String password = mPasswordField.getText().toString();
        final String conPassword = mPasswordField.getText().toString();
        final String firstName = fNameField.getText().toString();
        final String lastName = lNameField.getText().toString();

        if (mail.isEmpty()) {
            mEmailField.setError("Email is required");
            mEmailField.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mEmailField.setError("Please enter a valid email");
            mEmailField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            mPasswordField.setError("Password is required");
            mPasswordField.requestFocus();
            return;
        }
        if (password.length() < 8) {
            mPasswordField.setError("Password must be with length of 8");
            mPasswordField.requestFocus();
            return;
        }
        if (!(password.equals(conPassword))) {
            mPasswordField.setError("Passwords must be the same");
            mPasswordField.requestFocus();
            Toast.makeText(getApplicationContext(), "Passwords must be the same", Toast.LENGTH_SHORT).show();
            return;
        }
        if (firstName.isEmpty()) {
            mEmailField.setError("First Name Required");
            mEmailField.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            lNameField.setError("Last Name Required");
            lNameField.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users user = new Users(firstName, lastName, mail, password, isManager);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignUpUser.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                                openMainActivity();
                                            } else {
                                                Toast.makeText(SignUpUser.this, "Error, couldn't sign up", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(SignUpUser.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        saveData();
    }

    public void openMainActivity () {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", "Hello " + fNameField.getText().toString());

        editor.apply();
    }

}