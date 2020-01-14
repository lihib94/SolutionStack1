package com.example.myapplicationtemp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BooksPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String [] book;

    String faculty,degree,subject,bookSend,booktestflag;
    private String isManager = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //intent to get data from mainpage
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        faculty = bundle.getString("FACULTY");
        degree = bundle.getString("DEGREE");
        subject = bundle.getString("SEBJECT");
        isManager = bundle.getString("manager?");
        //Toast.makeText(get ApplicationContext(), faculty+""+degree+""+subject, Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_page);

        //profile
        Button openBtn = (Button) findViewById(R.id.openBtn) ;
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenExclass();
            }
        });
        Button uploadBtn = (Button) findViewById(R.id.nextBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClass();
            }
        });


        Spinner spinnerBooks = findViewById(R.id.bookTestSpin);
        ArrayAdapter<CharSequence> adapterBooks = ArrayAdapter.createFromResource(this,
                R.array.booksComputerSciense, android.R.layout.simple_spinner_item);
        adapterBooks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBooks.setAdapter(adapterBooks);
        spinnerBooks.setOnItemSelectedListener(this);
        book=getResources().getStringArray(R.array.booksComputerSciense);
        spinnerBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                bookSend=book[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void OpenExclass() {
        EditText page = (EditText) findViewById(R.id.pageNoInput);
        EditText ex = (EditText) findViewById(R.id.ExInput);
        final String pageSend = page.getText().toString();
        final String exSend = ex.getText().toString();
        booktestflag="book";
        Intent intent = new Intent(this, OpenEx.class);
        Bundle bundle = new Bundle();
        bundle.putString("booktestflag", booktestflag);
        bundle.putString("FACULTY", faculty);
        bundle.putString("DEGREE", degree);
        bundle.putString("SEBJECT", subject);
        bundle.putString("PAGESEND", pageSend);
        bundle.putString("EXSEND", exSend);
        bundle.putString("BOOKSEND", bookSend);
        intent.putExtras(bundle);
        startActivity(intent);


    }
    public void dialogClass() {
        Intent intent = new Intent(this, OpenEx.class);
        EditText page = (EditText) findViewById(R.id.pageNoInput);
        EditText ex = (EditText) findViewById(R.id.ExInput);
        final String pageSend = page.getText().toString();
        final String exSend = ex.getText().toString();
        booktestflag="book";
        String [] passInfo =  new String [7];
        passInfo[0] = bookSend ;passInfo[1] = faculty;passInfo[2] = degree ;
        passInfo[3] = subject ;passInfo[4] = pageSend  ;passInfo[5] = exSend;passInfo[6] = booktestflag;
        DialogClass uploadDialog = new DialogClass(2, passInfo);
        uploadDialog.show(getSupportFragmentManager(), "Take a pic / upload a photo");

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
                    Intent intent3 = new Intent(this, RequestsManager.class);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                    return true;
                case R.id.Remove_item:
                    Intent intent6 = new Intent(this, RemovePhotos.class);
                    intent6.putExtras(bundle);
                    startActivity(intent6);
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
        }}}