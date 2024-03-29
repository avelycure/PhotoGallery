package com.avelycure.photogallery.album_elements;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.albums.AlbumsActivity;
import com.avelycure.photogallery.data.FlickrApi;
import com.avelycure.photogallery.data.user_info.FlickrResponsePerson;
import com.avelycure.photogallery.home.HomeViewModel;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.utils.ImageAdapterParameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Adapter for recyclerView in AlbumsElementsActivity
 */
public class AlbumElementsAdapter extends RecyclerView.Adapter<AlbumElementsAdapter.AlbumElementsViewHolder> {
    /**
     * Base URL for retrofit
     */
    private final String BASE_URL = "https://api.flickr.com";
    private Retrofit mRetrofit;
    private List<AlbumElementListModel> list;
    private AlbumsElementViewModel albumsElementViewModel;

    /**
     * Parameter to save context
     */
    private ImageAdapterParameter imageAdapterParameter;

    /**
     * Is needed to determine if user enabled Editor Mode
     */
    private boolean chbIsVisible = false;

    public boolean isChbIsVisible() {
        return chbIsVisible;
    }

    public AlbumElementsAdapter(List<AlbumElementListModel> list, ImageAdapterParameter context, AlbumsElementViewModel albumsElementViewModel) {
        this.list = list;
        this.imageAdapterParameter = context;
        this.albumsElementViewModel = albumsElementViewModel;
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

    /**
     * Number of elements in recyclerView equals to number of albums
     *
     * @return
     */
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

            iv.setOnLongClickListener(v -> {
                switchSelection(true);
                ((AlbumElementsActivity) (imageAdapterParameter.getContext())).switchActionDeleteVisibility(true);
                return true;
            });

            chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setChecked(isChecked);
                }
            });

            /**
             * This function is called when user wants to get additional information about picture
             * in the album
             */
            iv.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(imageAdapterParameter.getContext());
                LayoutInflater inflater = ((Activity) (imageAdapterParameter.getContext())).getLayoutInflater();
                View view = inflater.inflate(R.layout.album_elements__show_image_details_activity, null);
                builder.setView(view);

                ImageView iv_details = view.findViewById(R.id.sid_iv);
                Picasso.with(imageAdapterParameter.getContext()).load(list.get(position).getUrl()).into(iv_details);

                //todo delegate this function to NetworkUtils
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                mRetrofit
                        .create(FlickrApi.class)
                        .getPersonInfo(list.get(position).getAuthor())
                        .enqueue(new Callback<FlickrResponsePerson>() {
                            @Override
                            public void onResponse(Call<FlickrResponsePerson> call, Response<FlickrResponsePerson> response) {
                                FlickrResponsePerson responsePerson = response.body();
                                albumsElementViewModel.showElement(responsePerson, view, builder);
                            }

                            @Override
                            public void onFailure(Call<FlickrResponsePerson> call, Throwable t) {}
                        });
            });
            Picasso.with(imageAdapterParameter.getContext()).load(list.get(position).getUrl()).into(iv);
        }

    }

    /**
     * This method is called when user enables/disables Editor Mode. We enable checkboxes to choose which
     * albums should be deleted
     * @param visibility is needed to choose if we need to enable/disable visibility of checkboxes
     */
    public void switchSelection(boolean visibility) {
        chbIsVisible = visibility;
        notifyDataSetChanged();
    }

}
