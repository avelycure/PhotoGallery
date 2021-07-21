package com.avelycure.photogallery.albums;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.album_elements.AlbumElementsActivity;
import com.avelycure.photogallery.utils.ImageAdapterParameter;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumAdapterViewHolder> {
    private List<AlbumListModel> list;
    private ImageAdapterParameter imageAdapterParameter;
    private static String ALBUM = "Album";
    private boolean chbIsVisible = false;

    public boolean isChbIsVisible() {
        return chbIsVisible;
    }

    public void setChbIsVisible(boolean chbIsVisible) {
        this.chbIsVisible = chbIsVisible;
    }

    public AlbumAdapter(List<AlbumListModel> list, ImageAdapterParameter imageAdapterParameter) {
        this.list = list;
        this.imageAdapterParameter = imageAdapterParameter;
    }

    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(imageAdapterParameter.getContext())
                .inflate(R.layout.album__rv_card, parent, false);
        return new AlbumAdapter.AlbumAdapterViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(AlbumAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AlbumAdapterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv;
        private final TextView tv;
        private final CheckBox chb;

        public AlbumAdapterViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.album_card_iv);
            tv = itemView.findViewById(R.id.album_card_tv);
            chb = itemView.findViewById(R.id.album_rv_chb);
        }

        //todo search for bug in this method and in analog in elements
        public void bind(int position) {
            tv.setText(list.get(position).getName());
            iv.setBackgroundResource(R.drawable.flowers);

            chb.setChecked(false);
            list.get(position).setChecked(false);

            if (chbIsVisible)
                chb.setVisibility(View.VISIBLE);
            else
                chb.setVisibility(View.GONE);

            chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setChecked(isChecked);
                }
            });

            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switchSelection(true);
                    ((AlbumsActivity) (imageAdapterParameter.getContext())).switchActionDeleteVisibility(true);
                    return true;
                }
            });

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(imageAdapterParameter.getContext(), AlbumElementsActivity.class);
                    intent.putExtra(ALBUM, list.get(position).getName());
                    imageAdapterParameter.getContext().startActivity(intent);
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(imageAdapterParameter.getContext());

                    alert.setTitle("Renaming album");
                    alert.setMessage("Input album name");

                    final EditText input = new EditText(imageAdapterParameter.getContext());
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (value != null && !value.equals(""))
                                tv.setText(value);
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                    alert.show();
                }
            });
        }
    }

    public void switchSelection(boolean visibility) {
        chbIsVisible = visibility;
        notifyDataSetChanged();
    }
}
