package com.example.assignment2java.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.assignment2java.data.Photo;

import java.util.List;

@Dao
public interface PhotoDao {
    @Insert
    void insert(Photo photo);

    @Query("select * from photo where date between :minDate and :maxDate and comment like :keyword")
    List<Photo> getPhotos(String minDate, String maxDate, String keyword);

    @Query("select * from photo")
    List<Photo> getPhotos();
}
