package com.avelycure.photogallery.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.App;
import com.avelycure.photogallery.R;
import com.avelycure.photogallery.room.AppDatabase;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private ImageAdapterParameter context;
    private List<CardModel> cards;
    private AppDatabase db;
    private ImageDao imageDao;
    private static String ALBUM_LIKED = "liked";
    private AlertDialog ad;
    private AlertDialog adAlbumName;

    public ImageAdapter(ImageAdapterParameter imageAdapterParameter, List<CardModel> cards) {
        this.context = imageAdapterParameter;
        this.cards = cards;
        db = App.getInstance().getDatabase();
        imageDao = db.imageDao();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater.from(context.getContext())
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
        private ImageView imageView;
        private ImageButton likeButton;
        private ImageButton saveButton;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            likeButton = itemView.findViewById(R.id.iv_like);
            saveButton = itemView.findViewById(R.id.iv_save);
        }

        void bind(int position) {
            if (cards.get(position).isLiked())
                likeButton.setImageResource(R.drawable.heart);
            else
                likeButton.setImageResource(R.drawable.heart1);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cards.get(position).isLiked()) {
                        likeButton.setImageResource(R.drawable.heart1);
                        cards.get(position).setLiked(false);
                    } else {
                        imageDao.insert(new Image(ALBUM_LIKED, cards.get(position).getUrl()));
                        likeButton.setImageResource(R.drawable.heart);
                        cards.get(position).setLiked(true);
                    }
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context.getContext());
                    LayoutInflater inflater = ((Activity) (context.getContext())).getLayoutInflater();
                    View view = inflater.inflate(R.layout.activity_choose_album, null);
                    builder.setView(view);

                    builder.setTitle("Save picture");
                    builder.setMessage("Choose album");

                    ListView lv = view.findViewById(R.id.dialog_lv);
                    List<String> albumsList = imageDao.getAlbumsInDB();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context.getContext(),
                            android.R.layout.simple_list_item_multiple_choice, albumsList);
                    lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lv.setAdapter(adapter);

                    TextView tvOk = view.findViewById(R.id.dialog_ok);
                    tvOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SparseBooleanArray checked = lv.getCheckedItemPositions();
                            for (int i = 0; i < albumsList.size(); i++) {
                                if (checked.get(i))
                                    imageDao.insert(new Image(albumsList.get(i), cards.get(position).getUrl()));
                            }
                            if (ad != null)
                                ad.dismiss();
                        }
                    });
                    TextView tvAddAlbum = view.findViewById(R.id.dialog_tv);
                    tvAddAlbum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final EditText input = new EditText(context.getContext());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            input.setLayoutParams(lp);
                            adAlbumName = new AlertDialog.Builder(context.getContext())
                                    .setTitle("Album name")
                                    .setMessage("Input name")
                                    .setView(input)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String albumName = input.getText().toString();
                                            Log.d("tag",albumName);
                                            albumsList.add(albumName);
                                            adapter.notifyDataSetChanged();
                                            adAlbumName.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                    ad = builder.show();
                }

            });
            Picasso.with(context.getContext()).load(cards.get(position).getUrl()).into(imageView);
        }

    }

}
