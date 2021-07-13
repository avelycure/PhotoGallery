package com.avelycure.photogallery.albums;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.utils.ImageAdapter;
import com.avelycure.photogallery.utils.ImageAdapterParameter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumAdapterViewHolder> {
    private List<AlbumListModel> list;
    private AlbumAdapterParameter context;

    public AlbumAdapter(List<AlbumListModel> list, AlbumAdapterParameter context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(context.getContext())
                .inflate(R.layout.album_card, parent, false);
        return new ImageAdapter.ImagesViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(AlbumAdapterViewHolder holder, int position) {

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
