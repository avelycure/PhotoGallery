package utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {

    private Context context;
    private static int recyclerViewSize;
    private static int currentPage;
    PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;

    public static int getCurrentPage() {
        return currentPage;
    }

    public static void addPage() {
        currentPage++;
    }

    public static void addRecyclerViewSize() {
        recyclerViewSize += 20;
    }

    public ImageAdapter( Context context, PhotoGalleryDatabaseHelper dbHelper) {
        photoGalleryDatabaseHelper = dbHelper;
        this.context = context;
        recyclerViewSize = 20;
        currentPage = 1;
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
        }

        void bind(int position) {
            Cursor query = photoGalleryDatabaseHelper.getAllUsers();
            for (int i = 0; i <= position; i++) {
                query.moveToNext();
            }
            Picasso.with(context).load(query.getString(1)).into(imageView);
            query.close();
        }

    }

}
