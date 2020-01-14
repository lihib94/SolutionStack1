package com.example.myapplicationtemp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailField, mPasswordField;
    private String temp;
    private Boolean flag = false;
    private String isManager = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isManager = bundle.getString("manager?");
        System.out.println(isManager);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mEmailField =  (EditText)findViewById(R.id.emailInput);
        mPasswordField = (EditText)findViewById(R.id.passInput);

        if(isManager.equals("yes")) {
            String e = bundle.getString("email");
            String p = bundle.getString("pass");
            if ((e!=null)&&(p!=null)) {
                mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("manager?", isManager);
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                saveData();
            }}

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainPage();
            }
        });

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });

        Button forgotPassBtn = (Button) findViewById(R.id.forgotPassBtn);
        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPass();
            }
        });

        CheckBox rememberPassBox = (CheckBox) findViewById(R.id.RememberPassBox);
        rememberPassBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false) flag = true;
                else flag = false;
            }
        });
        loadData();
    }
    public void openMainPage () {
        String mail = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();


        //Email is not empty
        if (mail.isEmpty()) {
            mEmailField.setError("Email is required");
            mEmailField.requestFocus();
            return;
        }
        //Email is real
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mEmailField.setError("Please enter a valid email");
            mEmailField.requestFocus();
            return;
        }
        //Pass is not empty
        if (password.isEmpty()) {
            mPasswordField.setError("Password is required");
            mPasswordField.requestFocus();
            return;
        }
        //Pass is greater then 8
        if (password.length() < 8) {
            mPasswordField.setError("Password must be with length of 8");
            mPasswordField.requestFocus();
            return;
        }

        //check if all the info connection is on the DB
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("manager?", isManager);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveData();
    }

    public void openSignUp () {
        Intent intent = new Intent(this, SignUpUser.class);
        startActivity(intent);
    }

    public void openForgotPass () {
        //DO NOT FORGET TO SEND THE PASSWORD!!!!!!!!!!!!!
        DialogClass uploadDialog = new DialogClass(1);
        uploadDialog.show(getSupportFragmentManager(), "Password Issue");
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(flag == true) {
            editor.putString("email", mEmailField.getText().toString());
            editor.putString("pass", mPasswordField.getText().toString());
        }
        else{
            editor.putString("email", "");
            editor.putString("pass", "");
        }
        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        temp = sharedPreferences.getString("email", "");
        mEmailField.setText(temp);

        temp = sharedPreferences.getString("pass", "");
        mPasswordField.setText(temp);
    }

}
