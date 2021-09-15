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
import com.avelycure.photogallery.data.FlickrApi;
import com.avelycure.photogallery.data.images.FlickrResponseImage;
import com.avelycure.photogallery.data.user_info.FlickrResponsePerson;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.avelycure.photogallery.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Adapter for recyclerView in HomeActivity
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private static final String ALBUM_LIKED = "Liked";
    private final String BASE_URL = "https://api.flickr.com";

    private final ImageAdapterParameter imageAdapterParameter;
    private List<HomeCardModel> cards;
    private final ImageDao imageDao;

    private AlertDialog alertDialog;
    private AlertDialog alertDialogAlbumName;

    public ImageAdapter(ImageAdapterParameter imageAdapterParameter, List<HomeCardModel> cards) {
        this.imageAdapterParameter = imageAdapterParameter;
        this.cards = cards;
        imageDao = App.getInstance().getDatabase().imageDao();
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

            tvAuthor.setText(cards.get(position).getUserName());

            makeRequest(cards.get(position).getUserName());

            /*
              This function is called when user clicks like
             */
            likeButton.setOnClickListener(v -> {
                if (cards.get(position).isLiked()) {
                    likeButton.setImageResource(R.drawable.empty_heart);
                    cards.get(position).setLiked(false);
                } else {
                    imageDao.insert(new Image(ALBUM_LIKED, cards.get(position).getUrl(), cards.get(position).getUserName()));//??
                    likeButton.setImageResource(R.drawable.heart);
                    cards.get(position).setLiked(true);
                }
            });

            /*
              This function is called when user wants to save picture to existing album or create
              a new album
             */
            saveButton.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(imageAdapterParameter.getContext());
                List<String> albumsList = imageDao.getAlbumsInDB();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(imageAdapterParameter.getContext(),
                        android.R.layout.simple_list_item_multiple_choice, albumsList);

                View view = createDialogToChooseAlbum(builder, albumsList, adapter, position);

                TextView tvAddAlbum = view.findViewById(R.id.dialog_tv);

                tvAddAlbum.setOnClickListener(v1 -> {
                    alertDialogAlbumName = createAlertDialogToChooseAlbumName(albumsList, adapter);
                });
                alertDialog = builder.show();
            });
            Picasso.with(imageAdapterParameter.getContext()).load(cards.get(position).getUrl()).into(imageView);
        }

        private View createDialogToChooseAlbum(AlertDialog.Builder builder, List<String> albumsList,
                                               ArrayAdapter<String> adapter, int position) {
            LayoutInflater inflater = ((Activity) (imageAdapterParameter.getContext())).getLayoutInflater();
            View view = inflater.inflate(R.layout.home__dialog_choose_album_activity, null);
            builder.setView(view);

            builder.setTitle("Save picture");
            builder.setMessage("Choose album");

            ListView lv = view.findViewById(R.id.dialog_lv);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            lv.setAdapter(adapter);

            TextView tvOk = view.findViewById(R.id.dialog_ok);

            tvOk.setOnClickListener(v -> {
                SparseBooleanArray checked = lv.getCheckedItemPositions();
                for (int i = 0; i < albumsList.size(); i++)
                    if (checked.get(i))
                        imageDao.insert(new Image(albumsList.get(i), cards.get(position).getUrl(), cards.get(position).getUserName()));
                if (alertDialog != null)
                    alertDialog.dismiss();
            });

            return view;
        }

        private AlertDialog createAlertDialogToChooseAlbumName(List<String> albumsList,
                                                               ArrayAdapter<String> adapter) {
            final EditText input = new EditText(imageAdapterParameter.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            return new AlertDialog.Builder(imageAdapterParameter.getContext())
                    .setTitle("Album name")
                    .setMessage("Input name")
                    .setView(input)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String albumName = input.getText().toString();
                            albumsList.add(albumName);
                            adapter.notifyDataSetChanged();
                            alertDialogAlbumName.dismiss();
                        }
                    }).show();
        }

        private void makeRequest(String authorId) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mRetrofit
                    .create(FlickrApi.class)
                    .getPersonInfo(authorId)
                    .enqueue(new Callback<FlickrResponsePerson>() {
                        @Override
                        public void onResponse(Call<FlickrResponsePerson> call, Response<FlickrResponsePerson> response) {
                            FlickrResponsePerson res = response.body();
                            tvAuthor.setText(res.getPerson().getUserName().getName());
                            Picasso.with(imageAdapterParameter.getContext())
                                    .load("https://farm" + res.getPerson().getIconFarm() +
                                            ".staticflickr.com/" + res.getPerson().getIconServer() +
                                            "/buddyicons/" + res.getPerson().getNsid() + ".jpg")
                                    .into(userPhoto);
                        }

                        @Override
                        public void onFailure(Call<FlickrResponsePerson> call, Throwable t) {}
                    });
        }
    }
}
