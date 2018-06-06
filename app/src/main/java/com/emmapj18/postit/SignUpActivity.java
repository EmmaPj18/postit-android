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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();

        CardView signupButton = findViewById(R.id.SignUpButton);
        signupButton.setOnClickListener(this);
        TextView goToLogin = findViewById(R.id.GoLogin);
        goToLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignUpButton: {
                EditText mEditUser = findViewById(R.id.editUser);
                EditText mEditPass = findViewById(R.id.editPass);
                EditText mEditConfirm = findViewById(R.id.editPassConfirm);

                createUser(mEditUser.getText().toString(), mEditPass.getText().toString(), mEditConfirm.getText().toString());
                break;
            }
            case R.id.GoLogin:{
                startLoginActivity();
                break;
            }
        }
    }

    private void createUser(final String email, final String password, final String confirmPassword) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("AUTH", "Sign Up not Succesfull with this email" + email + " and password" + password);
                    Toast.makeText(SignUpActivity.this, "Error trying to Sign Up. Try Again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Successfully.", Toast.LENGTH_SHORT).show();
                    startLoginActivity();
                }
            }
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
