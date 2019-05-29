package com.example.assignment2java.new_photo;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment2java.data.Photo;
import com.example.assignment2java.data.PhotoDao;
import com.example.assignment2java.data.PhotoDatabase;
import com.example.assignment2java.data.PhotoRepository;
import com.example.assignment2java.util.AppExecutors;
import com.example.assignment2java.util.ImageUtil;

import java.io.File;
import java.io.IOException;

public class NewPhotoViewModel extends AndroidViewModel {
    private PhotoRepository repository;
    // Using LiveData and caching what getAlphabetizedPhotos returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private MutableLiveData<Photo> newPhoto = new MutableLiveData<Photo>();

    public NewPhotoViewModel(Application application) {
        super(application);
        PhotoDao photosDao = PhotoDatabase.getInstance(application).photoDao();
        repository = PhotoRepository.getInstance(new AppExecutors(), photosDao);
        newPhoto.setValue(new Photo());
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    public void save() {
        repository.insert(newPhoto.getValue());
    }

    public Photo getPhoto(){
        return newPhoto.getValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setImage(Bitmap image) {
        try {
            File img = ImageUtil.createImageFile(image);
            newPhoto.getValue().setPath(img.getAbsolutePath());
        } catch (IOException e) {
            //
        }
    }
    public void setComment(String comment) {
        newPhoto.getValue().setComment(comment);
    }
    public void setDate(String date) {
        newPhoto.getValue().setDate(date);
    }
}
