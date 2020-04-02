package com.example.resumebuilder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_signin;
    private EditText et_login_email, et_login_password;
    private TextView tv_signup;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        btn_signin = findViewById(R.id.btn_signin);
        tv_signup = findViewById(R.id.tv_signup);
        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);

        btn_signin.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_signin){
            loginUser();
        }
        if (view == tv_signup){
            finish();
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        }
    }

    private void loginUser(){
        String email = et_login_email.getText().toString().trim();
        String password = et_login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter an Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter a Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to login...Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}