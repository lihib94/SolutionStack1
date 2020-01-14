package com.example.myapplicationtemp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DialogClass extends AppCompatDialogFragment {

    int WhereDidComeFrom = 0;
    private EditText email, mEmailField, mPasswordField;
    private FirebaseAuth mAuth;
    private String [] passInfo = new String [7];
    public DialogClass(int WhereDidComeFrom) {
        this.WhereDidComeFrom = WhereDidComeFrom;
    }

    public DialogClass(int WhereDidComeFrom, String [] passInfo) {
        for (int i=0;i<7;i++){
            this.passInfo[i] = passInfo[i];
        }
        this.WhereDidComeFrom = WhereDidComeFrom;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        //forgot your password
        if (this.WhereDidComeFrom == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_forgot_your_pass, null);
            email = view.findViewById(R.id.email);
            builder.setView(view)
                    .setTitle("Don't worry we're here to help you")
                    .setMessage("Please enter your email address")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //take the pass from the DB and send an email
                            String pass = "123456478";
                            Intent intert = new Intent(Intent.ACTION_VIEW, Uri.parse("mailyo:" + email.getText().toString()));
                            intert.putExtra(intert.EXTRA_SUBJECT, "Reset Password For SolutionStack");
                            intert.putExtra(intert.EXTRA_TEXT, "Your password is:" + pass);
                            startActivity(intert);
                            Toast.makeText(getActivity(), "The password was sent to your E-mail", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            return builder.create();
        } else if (this.WhereDidComeFrom == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("What is your choice?")
                    .setMessage("Please select:")
                    .setPositiveButton("Take a Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String yes = "yes";
                            Bundle bundle = new Bundle();
                            bundle.putString("cameraOrUpload", yes);
                            bundle.putStringArray("passInfo", passInfo);
                            Intent intent = new Intent(DialogClass.this.getActivity(), StoragePage.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Upload a file", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String no = "no";
                            Bundle bundle = new Bundle();
                            bundle.putString("cameraOrUpload", no);
                            bundle.putStringArray("passInfo", passInfo);
                            Intent intent = new Intent(DialogClass.this.getActivity(), StoragePage.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
            return builder.create();
        } else if (this.WhereDidComeFrom == 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_login_manager, null);
            mEmailField = view.findViewById(R.id.edit_username);
            mPasswordField = view.findViewById(R.id.edit_password);
            builder.setView(view)
                    .setTitle("Login Managers")
                    .setMessage("Please enter your email address& password")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String mail = mEmailField.getText().toString().trim();
                            String password = mPasswordField.getText().toString().trim();
                            String isManager = "yes";
                            Bundle bundle = new Bundle();
                            bundle.putString("email", mail);
                            bundle.putString("pass", password);
                            bundle.putString("manager?", isManager);
                            if (!(mail.equals("SolutionStack@gmail.com")&&(password.equals("12345678")))){
                                Toast.makeText(getActivity(), "You are trying to access a manager interface! you dont have permission", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DialogClass.this.getActivity(), MainActivity.class);
                                isManager = "no";
                                bundle.putString("manager?", isManager);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            else{
                            Intent intent = new Intent(DialogClass.this.getActivity(), MainPage.class);
                                intent.putExtras(bundle);
                            startActivity(intent);}
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            return builder.create();
        } else {
            return null;
        }
    }
}