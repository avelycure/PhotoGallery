package com.avelycure.photogallery.album_elements;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.albums.AlbumsActivity;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumElementsAdapter extends RecyclerView.Adapter<AlbumElementsAdapter.AlbumElementsViewHolder> {

    private List<AlbumElementListModel> list;
    private ImageAdapterParameter imageAdapterParameter;
    private boolean chbIsVisible = false;

    public boolean isChbIsVisible() {
        return chbIsVisible;
    }

    public AlbumElementsAdapter(List<AlbumElementListModel> list, ImageAdapterParameter context) {
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
            chb.setChecked(false);
            list.get(position).setChecked(false);

            if (chbIsVisible)
                chb.setVisibility(View.VISIBLE);
            else
                chb.setVisibility(View.GONE);

            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    chbIsVisible = true;
                    ((AlbumElementsActivity) (imageAdapterParameter.getContext())).switchActionDeleteVisibility(true);
                    notifyDataSetChanged();
                    return true;
                }
            });

            chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setChecked(isChecked);
                }
            });

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(imageAdapterParameter.getContext());
                    LayoutInflater inflater = ((Activity) (imageAdapterParameter.getContext())).getLayoutInflater();
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

    public void setChbIsVisible(boolean chbIsVisible) {
        this.chbIsVisible = chbIsVisible;
    }

    public void switchSelection(boolean visibility) {
        chbIsVisible = visibility;
        notifyDataSetChanged();
    }

}
