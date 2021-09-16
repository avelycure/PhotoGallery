package com.avelycure.photogallery.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
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
import com.avelycure.photogallery.data.user_info.FlickrResponsePerson;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;
import com.avelycure.photogallery.utils.AlertDialogCreator;
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.avelycure.photogallery.utils.NetworkUtils;
import com.avelycure.photogallery.utils.OnGotResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for recyclerView in HomeActivity
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    //todo когда пользователь быстро скроллит, лента не успевает обноваляться, поэтому в каждый \
    // вью холдер после обновления начинают приходить ответы, и он начинает меняться, и через него \
    // проходит вся очередь, надо как то отменить
    private static final String ALBUM_LIKED = "Liked";

    private List<HomeCardModel> cards;
    private final ImageDao imageDao;
    private final NetworkUtils networkUtils;

    private final ImageAdapterParameter homeActivity;
    private final AlertDialogCreator alertDialogCreator;

    public ImageAdapter(ImageAdapterParameter homeActivity, List<HomeCardModel> cards,
                        NetworkUtils networkUtils) {
        this.homeActivity = homeActivity;
        this.cards = cards;
        this.networkUtils = networkUtils;
        imageDao = App.getInstance().getDatabase().imageDao();
        alertDialogCreator = new AlertDialogCreator();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView iv = (CardView) LayoutInflater
                .from(homeActivity.getContext())
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

    class ImagesViewHolder extends RecyclerView.ViewHolder implements OnGotResponse {
        private final ImageView imageView;
        private final ImageView userPhoto;
        private final ImageButton likeButton;
        private final ImageButton saveButton;
        private final TextView tvAuthor;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.home_rv_avatar);
            imageView = itemView.findViewById(R.id.iv_card);
            likeButton = itemView.findViewById(R.id.iv_like);
            saveButton = itemView.findViewById(R.id.iv_save);
            tvAuthor = itemView.findViewById(R.id.tv_username);
        }

        void bind(int position) {
            if (cards.get(position).isLiked())
                likeButton.setImageResource(R.drawable.heart);
            else
                likeButton.setImageResource(R.drawable.empty_heart);

            networkUtils.getUserNameAndPhoto(cards.get(position).getUserName(), this);

            /*
              This function is called when user clicks like
             */
            likeButton.setOnClickListener(v -> {
                if (cards.get(position).isLiked()) {
                    likeButton.setImageResource(R.drawable.empty_heart);
                    cards.get(position).setLiked(false);
                } else {
                    imageDao.insert(new Image(ALBUM_LIKED, cards.get(position).getUrl(), cards.get(position).getUserName()));
                    likeButton.setImageResource(R.drawable.heart);
                    cards.get(position).setLiked(true);
                }
            });

            saveButton.setOnClickListener(v -> {
                alertDialogCreator.createDialogToChooseAlbum(position, homeActivity,
                        imageDao, cards);
            });
            Picasso.with(homeActivity.getContext())
                    .load(cards.get(position).getUrl())
                    .into(imageView);
        }

        @Override
        public void gotResponse(Object response) {
            FlickrResponsePerson res = (FlickrResponsePerson) response;
            tvAuthor.setText(res.getPerson().getUserName().getName());
            Picasso.with(homeActivity.getContext())
                    .load("https://farm" + res.getPerson().getIconFarm() +
                            ".staticflickr.com/" + res.getPerson().getIconServer() +
                            "/buddyicons/" + res.getPerson().getNsid() + ".jpg")
                    .into(userPhoto);
        }
    }
}
