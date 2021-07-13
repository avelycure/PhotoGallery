package com.avelycure.photogallery.albums;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumElementsAdapter extends RecyclerView.Adapter<AlbumElementsAdapter.AlbumElementsViewHolder> {

    private List<ImageInAlbum> list;
    private AlbumAdapterParameter context;

    public AlbumElementsAdapter(List<ImageInAlbum> list, AlbumAdapterParameter context) {
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
                    Log.d("tag", "clicked");
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
