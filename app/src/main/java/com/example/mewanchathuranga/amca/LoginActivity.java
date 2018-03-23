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
    private Button loginCustBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mAuth = FirebaseAuth.getInstance();
        final EditText emailF = (EditText) findViewById(R.id.lEmailText);
        final EditText passF = (EditText) findViewById(R.id.lPassText);
        Button loginRyderBtn = (Button) findViewById(R.id.loginButtonRyder);
        regBtn = (Button) findViewById(R.id.regButton);
        loginCustBtn = (Button) findViewById(R.id.loginButtonCust);


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

        loginRyderBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.email = emailF.getText().toString();
                LoginActivity.this.pass = passF.getText().toString();
                LoginActivity.this.signInRyder();
            }
        });

        loginCustBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.email = emailF.getText().toString();
                LoginActivity.this.pass = passF.getText().toString();
                LoginActivity.this.signInCust();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        this.mAuth.addAuthStateListener(this.mAuthListener);
    }

    private void signInRyder() {
        if (TextUtils.isEmpty(this.email) || TextUtils.isEmpty(this.pass)) {
            Toast.makeText(this, "Please fill in the Email and Password field.", Toast.LENGTH_LONG).show();
        } else {
            this.mAuth.signInWithEmailAndPassword(this.email, this.pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
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
    private void signInCust(){
        if (TextUtils.isEmpty(this.email) || TextUtils.isEmpty(this.pass)) {
            Toast.makeText(this, "Please fill in the Email and Password field.", Toast.LENGTH_LONG).show();
        } else {
            this.mAuth.signInWithEmailAndPassword(this.email, this.pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Successful!", Toast.LENGTH_LONG).show();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, OrderHome1.class));
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Sign in Failed!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
