package com.example.assignment2java.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment2java.data.Photo;
import com.example.assignment2java.data.PhotoDao;
import com.example.assignment2java.data.PhotoDataSource;
import com.example.assignment2java.data.PhotoDatabase;
import com.example.assignment2java.data.PhotoRepository;
import com.example.assignment2java.util.AppExecutors;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Photo>> allPhotos;

    public MainViewModel(Application application){
        super(application);
        final AppExecutors appExecutors = new AppExecutors();
        PhotoDao photoDao = PhotoDatabase.getInstance(application.getApplicationContext()).photoDao();
        PhotoRepository photoRepository = PhotoRepository.getInstance(appExecutors, photoDao);
        photoRepository.getPhotos(new PhotoDataSource.PhotoLoadedCallback() {
            @Override
            public void onPhotosLoaded(final MutableLiveData<List<Photo>> photos) {
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        ((MutableLiveData<List<Photo>>)getAllPhotos()).setValue(photos.getValue());
//                        setAllPhotos(photos.getValue());
                    }
                });
            }
        });
    }

    public LiveData<List<Photo>> getAllPhotos() {
        if(allPhotos == null){
            allPhotos = new MutableLiveData<>();
        }
        return allPhotos;
    }

//    public void setAllPhotos(List<Photo> photos) {
//        if(allPhotos == null){
//            allPhotos = new MutableLiveData<>();
//        }
//        allPhotos.setValue(photos);
//    }
}
