package com.example.myapplicationtemp;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

public class TestsPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String [] yearTxt,moedTxt,semesterTxt;

    String faculty,degree,subject,semester,moed,year,booktestflag;

    private String isManager = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_page);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        faculty = bundle.getString("FACULTY");
        degree = bundle.getString("DEGREE");
        subject = bundle.getString("SEBJECT");
        isManager = bundle.getString("manager?");

        //profile

        Spinner spinnerYear = findViewById(R.id.yearSpin);
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.year, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setOnItemSelectedListener(this);
        yearTxt=getResources().getStringArray(R.array.year);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year=yearTxt[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerSemester = findViewById(R.id.semesterSpin);
        ArrayAdapter<CharSequence> adapterSemester = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapterSemester);
        spinnerSemester.setOnItemSelectedListener(this);
        semesterTxt=getResources().getStringArray(R.array.semester);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester=semesterTxt[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerMoed = findViewById(R.id.moedSpin);
        ArrayAdapter<CharSequence> adapterMoed = ArrayAdapter.createFromResource(this,
                R.array.moed, android.R.layout.simple_spinner_item);
        adapterMoed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMoed.setAdapter(adapterMoed);
        spinnerMoed.setOnItemSelectedListener(this);
        moedTxt=getResources().getStringArray(R.array.moed);
        spinnerMoed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moed=moedTxt[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void OpenExclass(){
        Intent intent = new Intent(this, OpenEx.class);
        Bundle bundle = new Bundle();
        booktestflag="test";
        bundle.putString("booktestflag", booktestflag);
        bundle.putString("FACULTY", faculty);
        bundle.putString("DEGREE", degree);
        bundle.putString("SEBJECT", subject);
        bundle.putString("YEAR", year);
        bundle.putString("SEMESTER", semester);
        bundle.putString("MOED", moed);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void dialogClass() {
        Intent intent = new Intent(this, OpenEx.class);
        String [] passInfo =  new String [7];
        booktestflag="test";
        passInfo[0] = year ;passInfo[1] = faculty; passInfo[2] = degree ;
        passInfo[3] = subject ;passInfo[4] = semester  ;passInfo[5] = moed ; passInfo[6] = booktestflag;
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
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
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