package com.example.food_waste_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class login extends AppCompatActivity {
Button loginbtn;
ProgressBar progressBar;
EditText login_email,password;
FirebaseAuth mAuth;


TextView gotoregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn = findViewById(R.id.button);
        gotoregister = findViewById(R.id.button2);
        login_email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        loginbtn = findViewById(R.id.button);
        progressBar=findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(view -> {
            loginUser();
        });

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, Register.class);
                startActivity(intent);
            }
        });
    }
        private void loginUser() {
            String lo_email =login_email.getText().toString();
            String lo_password = password.getText().toString();
            if(TextUtils.isEmpty(lo_email)){
                login_email.setError("fill the email");
            }else if(TextUtils.isEmpty(lo_password)){
                password.setError("fill the password");
            }
            else{
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(lo_email,lo_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            Toast.makeText(login.this, "logged successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this, MainActivity.class));
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(login.this, "login failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}
}