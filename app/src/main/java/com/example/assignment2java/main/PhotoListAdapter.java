package com.example.assignment2java.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2java.R;
import com.example.assignment2java.data.Photo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>{

    private LayoutInflater inflater;
    private MutableLiveData<List<Photo>> mPhotos = new MutableLiveData<>();

    public PhotoListAdapter(Context context){
        mPhotos.setValue(new ArrayList<Photo>());
        inflater = LayoutInflater.from(context);
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView commentView;
        TextView dateView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.riImage);
            commentView = itemView.findViewById(R.id.show_riComment);
            dateView = itemView.findViewById(R.id.show_riDate);
        }
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo current = mPhotos.getValue().get(position);
        holder.commentView.setText(current.getComment());
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(current.getPath()));
        holder.dateView.setText(current.getDate());
//        holder.locationView.setText(current.getLocation());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Photo photo = photos.get(position);
//                Intent intent = new Intent(v.getContext(), ShowPhotoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("photo", (Serializable) photo);
//                intent.putExtras(bundle);
//                v.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(mPhotos.getValue() == null){
            return 0;
        }
        return mPhotos.getValue().size();
    }

    void setPhotos(List<Photo> photos) {
        mPhotos.setValue(photos);
        notifyDataSetChanged();
    }
}
