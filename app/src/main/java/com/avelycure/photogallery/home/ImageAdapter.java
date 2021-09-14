package com.avelycure.photogallery.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for recyclerView in HomeActivity
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private ImageAdapterParameter imageAdapterParameter;
    private List<HomeCardModel> cards;
    private final AppDatabase db;
    private final ImageDao imageDao;
    private static String ALBUM_LIKED = "liked";
    private AlertDialog ad;
    private AlertDialog adAlbumName;

    public ImageAdapter(ImageAdapterParameter imageAdapterParameter, List<HomeCardModel> cards) {
        this.imageAdapterParameter = imageAdapterParameter;
        this.cards = cards;
        db = App.getInstance().getDatabase();
        imageDao = db.imageDao();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater
                .from(imageAdapterParameter.getContext())
                .inflate(R.layout.home__rv_card, parent, false);
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
        private TextView tvAuthor;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_card);
            likeButton = itemView.findViewById(R.id.iv_like);
            saveButton = itemView.findViewById(R.id.iv_save);
            tvAuthor = itemView.findViewById(R.id.tv_username);
        }

        void bind(int position) {
            if (cards.get(position).isLiked())
                likeButton.setImageResource(R.drawable.heart);
            else
                likeButton.setImageResource(R.drawable.heart1);

            tvAuthor.setText(cards.get(position).getUserName());

            /**
             * This function is called when user clicks like
             */
            likeButton.setOnClickListener(v -> {
                if (cards.get(position).isLiked()) {
                    likeButton.setImageResource(R.drawable.heart1);
                    cards.get(position).setLiked(false);
                } else {
                    imageDao.insert(new Image(ALBUM_LIKED, cards.get(position).getUrl(), cards.get(position).getUserName()));//??
                    likeButton.setImageResource(R.drawable.heart);
                    cards.get(position).setLiked(true);
                }
            });

            /**
             * This function is called when user wants to save picture to existing album or create
             * a new album
             */
            saveButton.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(imageAdapterParameter.getContext());
                LayoutInflater inflater = ((Activity) (imageAdapterParameter.getContext())).getLayoutInflater();
                View view = inflater.inflate(R.layout.home__dialog_choose_album_activity, null);
                builder.setView(view);

                builder.setTitle("Save picture");
                builder.setMessage("Choose album");

                ListView lv = view.findViewById(R.id.dialog_lv);
                List<String> albumsList = imageDao.getAlbumsInDB();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(imageAdapterParameter.getContext(),
                        android.R.layout.simple_list_item_multiple_choice, albumsList);
                lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                lv.setAdapter(adapter);

                TextView tvOk = view.findViewById(R.id.dialog_ok);
                tvOk.setOnClickListener(v12 -> {
                    SparseBooleanArray checked = lv.getCheckedItemPositions();
                    for (int i = 0; i < albumsList.size(); i++) {
                        if (checked.get(i))
                            imageDao.insert(new Image(albumsList.get(i), cards.get(position).getUrl(), cards.get(position).getUserName()));//??
                    }
                    if (ad != null)
                        ad.dismiss();
                });
                TextView tvAddAlbum = view.findViewById(R.id.dialog_tv);
                tvAddAlbum.setOnClickListener(v1 -> {
                    final EditText input = new EditText(imageAdapterParameter.getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    adAlbumName = new AlertDialog.Builder(imageAdapterParameter.getContext())
                            .setTitle("Album name")
                            .setMessage("Input name")
                            .setView(input)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String albumName = input.getText().toString();
                                    albumsList.add(albumName);
                                    adapter.notifyDataSetChanged();
                                    adAlbumName.dismiss();
                                }
                            })
                            .show();
                });
                ad = builder.show();
            });
            Picasso.with(imageAdapterParameter.getContext()).load(cards.get(position).getUrl()).into(imageView);
        }

    }

}
