package com.example.newfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

public class PostActivity extends AppCompatActivity {
    private ImageView close;
    private ImageView imageAdded;
    private TextView post;
    private Uri imageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close =findViewById(R.id.close_ID);
        post = findViewById(R.id.post_ID);
        imageAdded = findViewById(R.id.image_add_ID);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity.this,MainScreen.class));
                finish();
            }
        });
        CropImage.activity().start(PostActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && requestCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageAdded.setImageURI(imageUri);
        }else {
            Toast.makeText(this, "Error, Please try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this,MainScreen.class));
            finish();
        }
    }
}