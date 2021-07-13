package com.avelycure.photogallery.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private ImageAdapterParameter context;
    private List<CardModel> cards;

    public ImageAdapter(ImageAdapterParameter imageAdapterParameter, List<CardModel> cards) {
        this.context = imageAdapterParameter;
        this.cards = cards;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(context.getContext())
                .inflate(R.layout.image_card, parent, false);
        return new ImagesViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageButton likeButton;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            likeButton = itemView.findViewById(R.id.iv_like);
        }

        void bind(int position) {
            if (cards.get(position).isLiked())
                likeButton.setImageResource(R.drawable.heart);
            else
                likeButton.setImageResource(R.drawable.heart1);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cards.get(position).isLiked()) {
                        likeButton.setImageResource(R.drawable.heart1);
                        cards.get(position).setLiked(false);
                    } else {
                        likeButton.setImageResource(R.drawable.heart);
                        cards.get(position).setLiked(true);
                    }
                }
            });

            Picasso.with(context.getContext()).load(cards.get(position).getUrl()).into(imageView);
        }

    }

}
