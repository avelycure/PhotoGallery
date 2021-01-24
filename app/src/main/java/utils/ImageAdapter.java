package utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Random;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private NetworkUtils networkUtils;
    private Context context;
    private static int count;
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

    public ImageAdapter(NetworkUtils networkUtils, Context context, PhotoGalleryDatabaseHelper dbHelper) {
        photoGalleryDatabaseHelper = dbHelper;
        this.networkUtils = networkUtils;
        this.context = context;
        count = 0;
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
            int i = -1;
            Cursor query = photoGalleryDatabaseHelper.getAllUsers();
            while (i != position) {
                query.moveToNext();
                i++;
            }
            Picasso.with(context).load(query.getString(1)).into(imageView);
            query.close();
        }

    }

}
