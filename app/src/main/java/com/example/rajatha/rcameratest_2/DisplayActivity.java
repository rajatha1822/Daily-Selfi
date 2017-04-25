package com.example.rajatha.rcameratest_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ImageView display=(ImageView)findViewById(R.id.displayImage);
        Intent intent= getIntent();
        PhotoDetails photo=intent.getParcelableExtra("Selfie");
        String photoPath=photo.getmPhotoPath();
        String PhotoName=photo.getmPhotoName();
        Bitmap image=photo.getMdata();
        display.setImageBitmap(image);
    }
}
