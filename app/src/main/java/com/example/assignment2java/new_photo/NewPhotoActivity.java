package com.example.assignment2java.new_photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.assignment2java.R;

import java.util.Date;

public class NewPhotoActivity extends AppCompatActivity {

    private EditText mCommentView;
    private NewPhotoViewModel mNewPhotoViewModel;
    private static final  int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);

        mCommentView = findViewById(R.id.show_riComment);
        mNewPhotoViewModel = ViewModelProviders.of(this).get(NewPhotoViewModel.class);

        Button btnCancel = findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, replyIntent);
                finish();
            }
        });

        Button btnSnap = findViewById(R.id.button_snap);
        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        Button btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                // no photo
                if (mNewPhotoViewModel.getPhoto().getPath() == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Photo not saved because no photo taken",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    TextView txtComment = findViewById(R.id.show_riComment);
                    mNewPhotoViewModel.setComment(txtComment.getText().toString());
                    mNewPhotoViewModel.save();
                    setResult(Activity.RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.show_imageView);
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(imageBitmap);
            mNewPhotoViewModel.setImage(imageBitmap);
            mNewPhotoViewModel.setDate(new Date().toString());
        }
    }
}
