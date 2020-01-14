package com.example.myapplicationtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestsManager extends AppCompatActivity {

    private String isManager = "no";

    private ArrayList<RequestOfUser> req=new java.util.ArrayList<RequestOfUser>();
    private ArrayList<String> ReqS=new java.util.ArrayList<String>();
    private RequestAdapter adapter;
    String deleteReq="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_manager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isManager = bundle.getString("manager?");

        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Requests");
        Query query = collection;
        final RequestOfUser rou=new RequestOfUser("","",0);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                req.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    rou.setUserId((String) child.child("userId").getValue());
                    rou.setCreationTime((long) child.child("creationTime").getValue());
                    rou.setRequestText((String) child.child("requestText").getValue());
                    ReqS.add((String) child.child("requestText").getValue());
                    req.add(rou);
                }
                if(req.size()>1){
                    fillListView();
                }
                else {
                    //myAwesomeTextView.setText(path + "   THERE ARE NO SOLUTIONS FOR THIS EXERCISE");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });



    }

    public void fillListView(){
        //adapter=new RequestAdapter(this,req);
        final ListView lv = (ListView) findViewById(R.id.lvReq);
        //lv.setAdapter(ArrayAdapter<Stirng>);
        final ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ReqS);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(RequestsManager.this,ReqS.get(position),Toast.LENGTH_SHORT).show();
                deleteReq=""+ReqS.get(position);
                ReqS.remove(position);
                adapter.notifyDataSetChanged();

                final DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Requests");
                final Query query = collection;
                final RequestOfUser rou=new RequestOfUser("","",0);

                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        req.clear();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if(deleteReq.equals((String) child.child("requestText").getValue())) {
                                collection.child(child.getKey()).removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

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
        }}
}