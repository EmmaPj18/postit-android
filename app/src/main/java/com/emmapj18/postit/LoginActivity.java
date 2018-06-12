package com.emmapj18.postit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mAuth = FirebaseAuth.getInstance();

        CardView mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(this);
        TextView GoToSignUp = findViewById(R.id.GoSignUp);
        GoToSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) startMainActivity(user);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            EditText mEditUser = findViewById(R.id.editUserLogin);
            EditText mEditPass = findViewById(R.id.editPassLogin);

            appLogin(mEditUser.getText().toString(), mEditPass.getText().toString());
        }
        else if (view.getId() == R.id.GoSignUp) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    public void appLogin(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("AUTH", "Login not Succesfull with this email" + email + " and password" + password);
                    Toast.makeText(LoginActivity.this, "Authtentication Failed", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    startMainActivity(user);
                }
            }
        });
    }

    private void startMainActivity(FirebaseUser user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
