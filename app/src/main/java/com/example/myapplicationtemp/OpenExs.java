package com.example.myapplicationtemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class OpenExs extends AppCompatActivity {


    private ArrayList<String> urls=new java.util.ArrayList<String>();
    private ArrayList<Image> img=new java.util.ArrayList<Image>();
    private ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_exs);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        urls = bundle.getStringArrayList("picArray");
        Image imOb=new Image("","");
        for(int i=0;i<urls.size();i++){

            imOb=new Image(urls.get(i));
            img.add(imOb);
        }
        adapter=new ImageAdapter(this,img);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }
}
