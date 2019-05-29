package com.example.assignment2java.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2java.R;
import com.example.assignment2java.data.Photo;
import com.example.assignment2java.new_photo.NewPhotoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private static final int NEW_PHOTO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//                setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PhotoListAdapter adapter = new PhotoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedPhotos.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mainViewModel.getAllPhotos().observe(this, new Observer<List<Photo>>() {
                    @Override
                    public void onChanged(List<Photo> photos) {
                        adapter.setPhotos(photos);
                    }
                });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewPhotoActivity.class);
                startActivityForResult(intent, NEW_PHOTO_REQUEST_CODE);
            }
        });
    }
}
