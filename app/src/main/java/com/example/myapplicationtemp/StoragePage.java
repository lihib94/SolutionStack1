package com.example.myapplicationtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Ref;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StoragePage extends AppCompatActivity implements View.OnClickListener {

    //Buttons
    private boolean dontFall = false;
    private Button buttonChoose;
    private Button buttonUpload;
    private String cameraOrUpload = "";
    private String [] passInfo = new String [7];
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    StorageReference mStorageRef;
    //ImageView
    private ImageView imageView;
    //a Uri object to store file path
    public Uri imguri,uri;

    //For Lihi
    private String userID;
    private Books book;
    private Tests test;
    private DatabaseReference db;
    private String booktestflag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cameraOrUpload = bundle.getString("cameraOrUpload");
        passInfo = bundle.getStringArray("passInfo");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        book = new Books(passInfo[0],passInfo[1],passInfo[2],passInfo[3]);
        booktestflag=passInfo[6];

        if(cameraOrUpload .equals("yes")){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        imageView = (ImageView) findViewById(R.id.imageView);

        //attaching listener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == buttonChoose) {
            dontFall = true;
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == buttonUpload && dontFall == true) {
            fileuploader();

        }
        if(view == buttonUpload && dontFall == false){

            Toast.makeText(this, "You must choose photo before uploading", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(cameraOrUpload.equals("yes")){
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imguri = getImageUri(photo, Bitmap.CompressFormat.JPEG ,1);
                imageView.setImageURI(imguri);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imguri = data.getData();
                imageView.setImageURI(imguri);
            }
        }
    }

    public Uri getImageUri(Bitmap src, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), src, "title", null);
        return Uri.parse(path);
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private String getEx(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mim = MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileuploader() {
        final Image image = new Image(userID,"");
        final StorageReference ref = mStorageRef.child(System.currentTimeMillis() + "." + getEx(imguri));
        ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (booktestflag.equals("book")){
                                    db =  FirebaseDatabase.getInstance().getReference("Books");
                                    db.child(book.getBookName()).setValue(book);
                                    ExBook eb = new ExBook(passInfo[0],passInfo[4],passInfo[5],uri.toString(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    FirebaseDatabase.getInstance().getReference("Ex").push().setValue(eb);
                                }
                                else if(booktestflag.equals("test")){
                                        test = new Tests(passInfo[0],passInfo[4],passInfo[1],passInfo[2],passInfo[3],passInfo[5], uri.toString(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        FirebaseDatabase.getInstance().getReference("Tests").push().setValue(test);
                                }
                            }
                        });
                        Toast.makeText(StoragePage.this, "Image uploaded", Toast.LENGTH_LONG).show();
                    }
                });
    }
}