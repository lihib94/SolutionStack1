package com.example.myapplicationtemp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManagersVsUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers_vs_users);

        load();

        Button mainActivityBtn = (Button) findViewById(R.id.userBtn);
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        Button managerBtn = (Button) findViewById(R.id.managerBtn);
        managerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClass();
            }
        });
    }

    public void openMainActivity() {
        String isManager = "no";
        Bundle bundle = new Bundle();
        bundle.putString("manager?", isManager);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void dialogClass() {
        DialogClass uploadDialog = new DialogClass(3);
        uploadDialog.show(getSupportFragmentManager(), "login managers");
    }

    public void load(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String text = sharedPreferences.getString("name", " ");

        TextView nameTxt = (TextView) findViewById(R.id.nameTxt);
        nameTxt.setText(text);
    }
}
