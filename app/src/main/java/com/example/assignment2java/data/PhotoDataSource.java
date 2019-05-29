package com.example.assignment2java.data;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface PhotoDataSource {
    interface PhotoLoadedCallback{
        void onPhotosLoaded(MutableLiveData<List<Photo>> photos);
    }
}
