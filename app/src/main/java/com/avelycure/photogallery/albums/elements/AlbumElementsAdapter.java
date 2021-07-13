package com.avelycure.photogallery.albums.elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumElementsAdapter extends RecyclerView.Adapter<AlbumElementsAdapter.AlbumElementsViewHolder> {

    private List<Image> list;
    private ImageAdapterParameter context;

    public AlbumElementsAdapter(List<Image> list, ImageAdapterParameter context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AlbumElementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(context.getContext())
                .inflate(R.layout.album_element_card, parent, false);
        return new AlbumElementsAdapter.AlbumElementsViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(AlbumElementsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AlbumElementsViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public void bind(int position) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Picasso.with(context.getContext()).load(list.get(position).getUrl()).into(iv);
        }

        public AlbumElementsViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.album_element_card_iv);
        }
    }
}
