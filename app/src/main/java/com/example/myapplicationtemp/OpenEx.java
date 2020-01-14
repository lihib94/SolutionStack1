package com.example.myapplicationtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class OpenEx extends AppCompatActivity {

    public static TextView myAwesomeTextView = null;
    String faculty,degree,subject,pageSend,exSend,bookSend,booktestflag,year,semester,moed;
    private DatabaseReference db;
    private DataSnapshot ds;
    private String path;
    private ArrayList<String> pic=new ArrayList<String>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_ex);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        myAwesomeTextView = (TextView)findViewById(R.id.openExtxt);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        booktestflag = bundle.getString("booktestflag");

        if(booktestflag.equals("book")) {
            faculty = bundle.getString("FACULTY");
            degree = bundle.getString("DEGREE");
            subject = bundle.getString("SEBJECT");
            pageSend = bundle.getString("PAGESEND");
            exSend = bundle.getString("EXSEND");
            bookSend = bundle.getString("BOOKSEND");
            path = "Book: "+bookSend+" Page: "+pageSend+" Ex: "+exSend;
            myAwesomeTextView.setText(path);
        }
        else {
            faculty = bundle.getString("FACULTY");
            degree = bundle.getString("DEGREE");
            subject = bundle.getString("SEBJECT");
            year = bundle.getString("YEAR");
            semester = bundle.getString("SEMESTER");
            moed = bundle.getString("MOED");
            path = "Test: "+subject+" Year: "+year+" Semester: "+semester+" Moad: "+moed;
            myAwesomeTextView.setText(path);
        }

        if(booktestflag.equals("book")) {
            DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Ex");
            Query query = collection;

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.child("bookName").getValue().equals(bookSend) && child.child("ex").getValue().equals(exSend) && child.child("page").getValue().equals(pageSend)) {
                            pic.add((String) child.child("image").getValue());
                        }
                    }
                    if (pic.size() == 1) {
                        Picasso.get().load(pic.get(0)).into(imageView);
                    } else if (pic.size() > 1) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("picArray", pic);
                        bundle.putInt("myArrayIndex", i);
                        Intent intent = new Intent(OpenEx.this, OpenExs.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else
                        myAwesomeTextView.setText(path + "   THERE ARE NO SOLUTIONS FOR THIS EXERCISE");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else{
            DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Tests");
            Query query = collection;

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        System.out.println(child.child("subjuct").getValue()+ " " +subject + " " + child.child("moed").getValue()+" " +moed + " " + child.child("semester").getValue()+" " +semester + " " + child.child("year").getValue()+" " +year );
                        if (child.child("subjuct").getValue().equals(subject) && child.child("moed").getValue().equals(moed) && child.child("semester").getValue().equals(semester) && child.child("year").getValue().equals(year)) {
                            pic.add((String) child.child("image").getValue());
                        }
                    }
                    if (pic.size() == 1) {
                        Picasso.get().load(pic.get(0)).into(imageView);
                    } else if (pic.size() > 1) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("picArray", pic);
                        bundle.putInt("myArrayIndex", i);
                        Intent intent = new Intent(OpenEx.this, OpenExs.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else
                        myAwesomeTextView.setText(path + "   THERE ARE NO SOLUTIONS FOR THIS TEST");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
}