package com.avelycure.photogallery.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

//TODO recyclerview is only a view of data whoch should be in adapter. change adapter -> change data->need to control clicks with adapter and variables of class
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {

    private Context context;
    private static int recyclerViewSize;
    private static int currentPage;
    PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    //Set<Long> likedPhotos;

    private List<CardModel> cards;

    public static int getCurrentPage() {
        return currentPage;
    }

    public static void addPage() {
        currentPage++;
    }

    public static void addRecyclerViewSize() {
        recyclerViewSize += 20;
    }

    public ImageAdapter(Context context, PhotoGalleryDatabaseHelper dbHelper, List<CardModel> cards) {
        photoGalleryDatabaseHelper = dbHelper;
        this.context = context;
        //recyclerViewSize = 20;
        this.cards = cards;
        recyclerViewSize = cards.size();
        currentPage = 1;
        //this.likedPhotos = likedPhotos;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(parent.getContext())
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
        ImageView imageView;
        ImageButton likeButton;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            likeButton = itemView.findViewById(R.id.iv_like);
        }

        void bind(int position) {
            //TODO think what you write here, how to optimize memory
            //Cursor query = photoGalleryDatabaseHelper.getAllUsers();
            //query.moveToPosition(position);
            //Picasso.with(context).load(query.getString(1)).into(imageView);
            Picasso.with(context).load(cards.get(position).getUrl()).into(imageView);
            Log.d("mytag", "entered picasso");
            //Long pictureId = query.getLong(2);

            /*if (likedPhotos.contains(pictureId)) {
                likeButton.setImageResource(R.drawable.heart);
            } else {
                likeButton.setImageResource(R.drawable.heart1);
            }*/

            //String imageAddress = query.getString(1);
            //TODO when click on this button recyclerview moves to top, it can make user irrigate
            /*likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //unlike
                    if (String.valueOf(likeButton.getTag()).equals("Liked")) {
                        //delete from database

                        likeButton.setImageResource(R.drawable.heart1);
                        likeButton.setTag("Unliked");
                        //likedPhotos.remove(pictureId);
                        //like
                    } else if (String.valueOf(likeButton.getTag()).equals("Unliked")) {
                        //add to database
                        //likedPhotos.add(pictureId);
                        //photoGalleryDatabaseHelper.addLikedPhoto(imageAddress);

                        likeButton.setImageResource(R.drawable.heart);
                        likeButton.setTag("Liked");
                        likedPhotos.add(pictureId);
                    }
                }
            });*/
            //query.close();
        }

    }

}
