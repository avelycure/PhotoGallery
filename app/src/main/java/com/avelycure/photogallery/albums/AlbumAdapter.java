package com.avelycure.photogallery.albums;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.elements.AlbumElementsActivity;
import com.avelycure.photogallery.utils.ImageAdapterParameter;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumAdapterViewHolder> {
    private List<AlbumListModel> list;
    private ImageAdapterParameter context;
    private static String ALBUM = "Album";

    public AlbumAdapter(List<AlbumListModel> list, ImageAdapterParameter context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(context.getContext())
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
        private ImageView iv;
        private TextView tv;

        public AlbumAdapterViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.album_card_iv);
            tv = itemView.findViewById(R.id.album_card_tv);
        }

        public void bind(int position) {
            tv.setText(list.get(position).getName());
            iv.setBackgroundResource(R.drawable.flowers);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getContext(), AlbumElementsActivity.class);
                    intent.putExtra(ALBUM, list.get(position).getName());
                    context.getContext().startActivity(intent);
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context.getContext());

                    alert.setTitle("Renaming album");
                    alert.setMessage("Input album name");

                    final EditText input = new EditText(context.getContext());
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
}
