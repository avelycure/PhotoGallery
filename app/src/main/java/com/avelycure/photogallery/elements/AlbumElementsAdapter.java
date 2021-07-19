package com.avelycure.photogallery.elements;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
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
    private static String URL = "url";

    public AlbumElementsAdapter(List<Image> list, ImageAdapterParameter context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AlbumElementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(context.getContext())
                .inflate(R.layout.album_elements__card, parent, false);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context.getContext());
                    LayoutInflater inflater = ((Activity)(context.getContext())).getLayoutInflater();
                    View view = inflater.inflate(R.layout.album_elements__show_image_details_activity, null);
                    builder.setView(view);

                    builder.setTitle("Picture details");
                    ImageView iv_details = view.findViewById(R.id.sid_iv);
                    Picasso.with(context.getContext()).load(list.get(position).getUrl()).into(iv_details);

                    builder.show();
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
