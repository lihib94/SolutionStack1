package com.example.myapplicationtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Requests extends AppCompatActivity {

    private String isManager = "no";

    private EditText requestTxt;
    //private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID;
    private long cTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isManager = bundle.getString("manager?");


        requestTxt= (EditText) findViewById(R.id.requestTxt);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        Button mainActivityBtn = (Button) findViewById(R.id.sendBtn);
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequet();
            }
        });
    }

    private void sendRequet() {
        final String request = requestTxt.getText().toString();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cTime= Calendar.getInstance().getTimeInMillis();
        if (request.isEmpty()) {
            requestTxt.setError("You must fill Request");
            requestTxt.requestFocus();
            return;
        }
        RequestOfUser req=new RequestOfUser (userID,request,cTime);
        FirebaseDatabase.getInstance().getReference("Requests").push().setValue(req)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Requests.this, "Request was send to manager", Toast.LENGTH_SHORT).show();
                            openMainActivity();
                        } else {
                            Toast.makeText(Requests.this, "Error, couldn't send the request", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void openMainActivity () {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    //creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //if manager -
        if  (isManager.equals("yes")){inflater.inflate(R.menu.menu_file_manager, menu);return true;}
        inflater.inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("manager?", isManager);
        //if manager
        if (isManager.equals("yes")){
            switch (item.getItemId()) {
                case R.id.Profile_item:
                    Intent intent5 = new Intent(this, Profile.class);
                    intent5.putExtras(bundle);
                    startActivity(intent5);
                    return true;
                case R.id.ManagerP_item:
                    Intent intent2 = new Intent(this, ManagerProfile.class);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                    return true;
                case R.id.Requests_item:
                    Intent intent3 = new Intent(this, Requests.class);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                    return true;
                case R.id.LogOut_item:
                    Intent intent4 = new Intent(this, MainActivity.class);
                    intent4.putExtras(bundle);
                    startActivity(intent4);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }}

        //if user:
        else {
            switch (item.getItemId()) {

                case R.id.Profile_item:
                    Intent intent5 = new Intent(this, Profile.class);
                    intent5.putExtras(bundle);
                    startActivity(intent5);
                    return true;
                case R.id.Request_item:
                    Intent intent4 = new Intent(this, Requests.class);
                    intent4.putExtras(bundle);
                    startActivity(intent4);
                    return true;
                case R.id.LogOut_item:
                    Intent intent3 = new Intent(this, MainActivity.class);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                    return true;

                default:
                    return true;
            }
        }}
}