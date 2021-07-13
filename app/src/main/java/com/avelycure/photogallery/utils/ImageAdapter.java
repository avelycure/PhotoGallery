package com.avelycure.photogallery.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private ImageAdapterParameter context;
    private List<CardModel> cards;
    private AppDatabase db;
    private ImageDao imageDao;
    private static String ALBUM_LIKED = "liked";

    public ImageAdapter(ImageAdapterParameter imageAdapterParameter, List<CardModel> cards) {
        this.context = imageAdapterParameter;
        this.cards = cards;
        db = App.getInstance().getDatabase();
        imageDao = db.imageDao();
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
        private ImageButton saveButton;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            likeButton = itemView.findViewById(R.id.iv_like);
            saveButton = itemView.findViewById(R.id.iv_save);
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
                        imageDao.insert(new Image(ALBUM_LIKED, cards.get(position).getUrl()));
                        likeButton.setImageResource(R.drawable.heart);
                        cards.get(position).setLiked(true);
                    }
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context.getContext());

                    alert.setTitle("Save picture");
                    alert.setMessage("Choose album");

                    final ListView input = new ListView(context.getContext());
                    ArrayList<String> albums = new ArrayList<>();
                    albums.add("Liked");
                    albums.add("Nice");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context.getContext(), android.R.layout.simple_list_item_1, albums);
                    input.setAdapter(adapter);
                    input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("tag", "" + position);
                        }
                    });
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                    alert.show();
                }
            });

            Picasso.with(context.getContext()).load(cards.get(position).getUrl()).into(imageView);
        }

    }

}
