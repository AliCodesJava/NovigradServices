package com.example.servicesnovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class DocumentCreation extends AppCompatActivity {
    private static final int IMAGE_RESULT = 0;
    private File img;

    {
        try {
            img = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StorageReference imageStorage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_creation);
    }
    public void btnAddImg(View view){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_RESULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_RESULT && resultCode == RESULT_OK && data !=null){
            Uri image = data.getData();
            StorageReference ref = imageStorage.child("images/" + LoginForm.user.getUsername() + "/dingdongNomDuService.jpg");
            ref.putFile(image);
        }
    }
    public void btnShowImg(View view) {
        StorageReference ref = imageStorage.child("images/" + LoginForm.user.getUsername() + "/dingdongNomDuService.jpg");
        ref.getFile(img).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageURI(Uri.fromFile(img));
            }
        });
    }
}