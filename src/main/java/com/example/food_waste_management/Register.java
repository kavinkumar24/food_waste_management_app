package com.example.food_waste_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
Button registerbtn;
private FirebaseAuth mAuth;
EditText username, email_id, password, mobile_no,address;
Button register;
ImageView profile_pic;
ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerbtn=findViewById(R.id.button);

        username = findViewById(R.id.editTextTextPersonName);
        email_id = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPersonName3);
        mobile_no = findViewById(R.id.editTextTextPersonName5);
        address = findViewById(R.id.editTextTextPersonName6);
        register = (Button) findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        registerbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.button2:
                        Intent intent = new Intent(Register.this, login.class);
                        startActivity(intent);

                    case R.id.button:
                        Sign_up();
                        break;
                }
            }

            private void Sign_up() {

                String name = username.getText().toString();
                String mail = email_id.getText().toString();
                String pass = password.getText().toString();
                String mobile = mobile_no.getText().toString();
                String Address = address.getText().toString();
                if(name.isEmpty()){
                    username.setError("Name is required");
                    username.requestFocus();
                    return;
                }
                if(mail.isEmpty()){
                    email_id.setError("Mail_id is required");
                    email_id.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    password.setError("password is required");
                    password.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email_id.setError("please provide valid email");
                    email_id.requestFocus();
                    return;
                }
                if(mobile.isEmpty()){
                    mobile_no.setError("mobile number is required");
                    mobile_no.requestFocus();
                    return;
                }

                if(pass.length()<6){
                    password.setError("minimum length should be 6 characters");
                    password.requestFocus();
                    return;

                }
                if(Address.isEmpty()){
                    address.setError("Address is required");
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(name, mail, pass,mobile,Address);

                                    FirebaseDatabase.getInstance().getReference("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register.this, "user has been registered Successfully", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                        Intent intent = new Intent(Register.this, login.class);
                                                        startActivity(intent);

                                                    } else {
                                                        Toast.makeText(Register.this, "failed to register", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(Register.this, "failed to register", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }
}