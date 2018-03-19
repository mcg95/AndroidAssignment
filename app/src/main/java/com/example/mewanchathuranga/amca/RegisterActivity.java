package com.example.mewanchathuranga.amca;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText eMailText;
    private EditText fNameText;
    private EditText lNameText;
    private FirebaseFirestore mFirestore;
    private EditText pass2Text;
    private EditText passText;
    private Button saveBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.mFirestore = FirebaseFirestore.getInstance();
        final EditText fNameText = (EditText) findViewById(R.id.firstNameText);
        final EditText lNameText = (EditText) findViewById(R.id.lastNameText);
        final EditText eMailText = (EditText) findViewById(R.id.emailText);
        final EditText passText = (EditText) findViewById(R.id.passText);
        EditText pass2Text = (EditText) findViewById(R.id.pass2Text);
        ((Button) findViewById(R.id.regButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String fName = fNameText.getText().toString();
                String lName = lNameText.getText().toString();
                String eMail = eMailText.getText().toString();
                String pass = passText.getText().toString();
                Object userMap = new HashMap();
                userMap.put("First name", fName);
                userMap.put("Last name", lName);
                userMap.put("Email ID", eMail);
                userMap.put("Password", pass);
                RegisterActivity.this.mFirestore.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegisterActivity.this, "User Succesfully Registered", 0).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), 0).show();
                    }
                });
            }
        });
    }
}

