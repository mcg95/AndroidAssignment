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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth$AuthStateListener;

public class LoginActivity extends AppCompatActivity {
    private String email;
    private EditText emailF;
    private Button loginBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth$AuthStateListener mAuthListener;
    private String pass;
    private EditText passF;
    private Button regBtn;

    private void signIn() {
        if(!TextUtils.isEmpty(this.email) && !TextUtils.isEmpty(this.pass)) {
            this.mAuth.signInWithEmailAndPassword(this.email, this.pass).addOnCompleteListener(new OnCompleteListener() {
                public void onComplete(@NonNull Task var1) {
                    if(!var1.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in Failed!", 1).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sign in Successful!", 1).show();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please fill in the Email and Password field.", 1).show();
        }
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(2131361820);
        this.mAuth = FirebaseAuth.getInstance();
        final EditText var2 = (EditText)this.findViewById(2131230855);
        final EditText var3 = (EditText)this.findViewById(2131230856);
        Button var4 = (Button)this.findViewById(2131230868);
        Button var5 = (Button)this.findViewById(2131230925);
        this.mAuthListener = new FirebaseAuth$AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth var1) {
                if(var1.getCurrentUser() == null) {
                    ;
                }

            }
        };
        var5.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        var4.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                LoginActivity.this.email = var2.getText().toString();
                LoginActivity.this.pass = var3.getText().toString();
                LoginActivity.this.signIn();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        this.mAuth.addAuthStateListener(this.mAuthListener);
    }
}
