package com.example.assignment2java.data;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment2java.util.AppExecutors;

import java.util.List;

public class PhotoRepository {
    private static volatile PhotoRepository INSTANCE;
    private PhotoDao mPhotoDao;
    private AppExecutors mAppExecutors;

    MutableLiveData<List<Photo>> mPhotos;

    private PhotoRepository(@NonNull AppExecutors appExecutors, @NonNull PhotoDao photoDao){
        mAppExecutors = appExecutors;
        mPhotoDao = photoDao;
    }

    public static PhotoRepository getInstance(@NonNull AppExecutors appExecutors, @NonNull PhotoDao photoDao){
        if(INSTANCE == null){
            synchronized(PhotoRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new PhotoRepository(appExecutors, photoDao);
                }
            }
        }
        return INSTANCE;
    }

//    public void getPhotos(final Date minDate, final Date maxDate, final String keyword, @NonNull final PhotoDataSource.PhotoLoadedCallback callback){
//        mAppExecutors.diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                List<Photo> photos = mPhotoDao.getPhotos("", "", keyword);
//                callback.onPhotosLoaded(photos);
//            }
//        });
//    }

    public void getPhotos(@NonNull final PhotoDataSource.PhotoLoadedCallback callback){
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mPhotos == null){
                    mPhotos = new MutableLiveData<>();
                }
                callback.onPhotosLoaded(mPhotos);
//                callback.onPhotosLoaded(mPhotoDao.getPhotos());
            }
        });
    }
//
    public void insert(final Photo photo){
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mPhotoDao.insert(photo);
            }
        });
    }
}
