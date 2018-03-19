package com.example.mewanchathuranga.amca;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

public class LoginActivity extends AppCompatActivity {
    private String email;
    private EditText emailF;
    private Button loginBtn;
    private FirebaseAuth mAuth;
    private AuthStateListener mAuthListener;
    private String pass;
    private EditText passF;
    private Button regBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mAuth = FirebaseAuth.getInstance();
        final EditText emailF = (EditText) findViewById(R.id.lEmailText);
        final EditText passF = (EditText) findViewById(R.id.lPassText);
        Button loginBtn = (Button) findViewById(R.id.loginButton);
        Button regBtn = (Button) findViewById(R.id.regButton);
        this.mAuthListener = new AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                }
            }
        };
        regBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        loginBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.email = emailF.getText().toString();
                LoginActivity.this.pass = passF.getText().toString();
                LoginActivity.this.signIn();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        this.mAuth.addAuthStateListener(this.mAuthListener);
    }

    private void signIn() {
        if (TextUtils.isEmpty(this.email) || TextUtils.isEmpty(this.pass)) {
            Toast.makeText(this, "Please fill in the Email and Password field.", Toast.LENGTH_LONG).show();
        } else {
            this.mAuth.signInWithEmailAndPassword(this.email, this.pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Successful!", Toast.LENGTH_LONG).show();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Sign in Failed!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
