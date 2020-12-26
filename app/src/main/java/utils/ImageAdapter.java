package utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    public static int getCurrentPage() {
        return currentPage;
    }

    public static void addPage(){
        currentPage++;
    }

    public static void addRecyclerViewSize(){
        recyclerViewSize += 20;
    }

    public ImageAdapter(NetworkUtils networkUtils, Context context) {
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
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return recyclerViewSize;
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        private int n;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            Random random = new Random();
            n = random.nextInt(20);
        }

        void bind() {
            String request = null;
            try {
                //count++;
                //Log.d("mytag", "" + count);
                request = networkUtils.createURLToGetImage(n);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Picasso.with(context).load(request).into(imageView);
            /*if (count == 15) {
                count = 0;
                recyclerViewSize += 20;
            }*/
        }

    }

}
