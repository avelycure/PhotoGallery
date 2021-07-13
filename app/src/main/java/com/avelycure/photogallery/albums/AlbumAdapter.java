package com.avelycure.photogallery.albums;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumAdapterViewHolder> {
    private List<AlbumListModel> list;


    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AlbumAdapterViewHolder extends RecyclerView.ViewHolder {

        public AlbumAdapterViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
