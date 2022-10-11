package com.example.food_waste_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class myprofile extends AppCompatActivity {
    Button logout, btn_upload;
    ImageView profile_pic;
    private Uri imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        logout = findViewById(R.id.logout);
        btn_upload = findViewById(R.id.btn_upload_img);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myprofile.this, login.class);
                startActivity(intent);
                finish();
                Toast.makeText(myprofile.this, "Successfully logout", Toast.LENGTH_SHORT).show();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
            }
        });
        profile_pic = findViewById(R.id.profile_image);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo_intent = new Intent(Intent.ACTION_PICK);
                photo_intent.setType("image/*");

                startActivityForResult(photo_intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            imagepath = data.getData();
            getImageInImageView();

        }
    }

    private void getImageInImageView() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile_pic.setImageBitmap(bitmap);
    }

    private void uploadimage() {
        final ProgressDialog processDialog = new ProgressDialog(this);
        processDialog.setTitle("Uploading...");
        processDialog.show();

        FirebaseStorage.getInstance().getReference("images/" + UUID.randomUUID().toString()).putFile(imagepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(myprofile.this, "image uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Task failed", "Task failed");
                    Toast.makeText(myprofile.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                processDialog.dismiss();
            }
        });


    }
}