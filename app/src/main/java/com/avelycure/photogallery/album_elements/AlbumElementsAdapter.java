package com.avelycure.photogallery.album_elements;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    private ImageAdapterParameter imageAdapterParameter;
    private boolean chbIsVisible = false;

    public AlbumElementsAdapter(List<Image> list, ImageAdapterParameter context) {
        this.list = list;
        this.imageAdapterParameter = context;
    }

    @Override
    public AlbumElementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(imageAdapterParameter.getContext())
                .inflate(R.layout.album_elements__rv_card, parent, false);
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
        private CheckBox chb;

        public AlbumElementsViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.album_element_card_iv);
            chb = itemView.findViewById(R.id.album_elements_chb);
        }
        public void bind(int position) {
            if (chbIsVisible)
                chb.setVisibility(View.VISIBLE);
            else
                chb.setVisibility(View.GONE);

            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    chbIsVisible = true;
                    chb.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    return true;
                }
            });

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(imageAdapterParameter.getContext());
                    LayoutInflater inflater = ((Activity)(imageAdapterParameter.getContext())).getLayoutInflater();
                    View view = inflater.inflate(R.layout.album_elements__show_image_details_activity, null);
                    builder.setView(view);

                    builder.setTitle("Picture details");
                    ImageView iv_details = view.findViewById(R.id.sid_iv);
                    Picasso.with(imageAdapterParameter.getContext()).load(list.get(position).getUrl()).into(iv_details);

                    builder.show();
                }
            });
            Picasso.with(imageAdapterParameter.getContext()).load(list.get(position).getUrl()).into(iv);
        }

    }
}
