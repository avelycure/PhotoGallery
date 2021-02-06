package utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {

    private Context context;
    private static int recyclerViewSize;
    private static int currentPage;
    ImageButton imageButton;
    PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    Set<Long> likedPhotos;

    public static int getCurrentPage() {
        return currentPage;
    }

    public static void addPage() {
        currentPage++;
    }

    public static void addRecyclerViewSize() {
        recyclerViewSize += 20;
    }

    public ImageAdapter( Context context, PhotoGalleryDatabaseHelper dbHelper, Set<Long> likedPhotos, ImageAdapter imageAdapter) {
        photoGalleryDatabaseHelper = dbHelper;
        this.context = context;
        recyclerViewSize = 20;
        currentPage = 1;
        this.likedPhotos = likedPhotos;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.image_card, parent, false);

        ImagesViewHolder viewHolder = new ImagesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recyclerViewSize;
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            imageButton = itemView.findViewById(R.id.iv_like);
        }

        void bind(int position) {
            //TODO think what you write here
            Cursor query = photoGalleryDatabaseHelper.getAllUsers();
            //for (int i = 0; i <= position; i++) {
            //    query.moveToNext();
            //}
            query.moveToPosition(position);
            Picasso.with(context).load(query.getString(1)).into(imageView);
            /*

            recyclerview like button android studio
            https://www.youtube.com/watch?v=U0snyuZWlyc

            Long pictureId = query.getLong(2);
            if (likedPhotos.contains(pictureId)){
                imageButton.setBackground(context.getDrawable(R.drawable.heart));
            }else{
                imageButton.setBackground(context.getDrawable(R.drawable.heart1));
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likedPhotos.add(pictureId);
                    imageButton.setBackground(context.getDrawable(R.drawable.heart));
                    ImageAdapter.super.notifyItemChanged(ImagesViewHolder.super.getAdapterPosition());
                    Log.d("mytag", likedPhotos+"");
                    }
            });*/
            query.close();
        }

    }

}
