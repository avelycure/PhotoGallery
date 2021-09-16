package com.avelycure.photogallery.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.avelycure.photogallery.R;
import com.avelycure.photogallery.home.HomeCardModel;
import com.avelycure.photogallery.room.Image;
import com.avelycure.photogallery.room.ImageDao;

import java.util.List;

public class AlertDialogCreator {
    private AlertDialog alertDialog;

    /**
     * This function is called when user wants to save picture to existing album or create
     * a new album
     */
    public void createDialogToChooseAlbum(int position, ImageAdapterParameter homeActivity,
                                          ImageDao imageDao, List<HomeCardModel> cards) {
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.getContext());
        List<String> albumsList = imageDao.getAlbumsInDB();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(homeActivity.getContext(),
                android.R.layout.simple_list_item_multiple_choice, albumsList);

        LayoutInflater inflater = ((Activity) (homeActivity.getContext())).getLayoutInflater();
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

        TextView tvAddAlbum = view.findViewById(R.id.dialog_tv);

        tvAddAlbum.setOnClickListener(v1 -> {
            createAlertDialogToChooseAlbumName(
                    albumsList,
                    adapter,
                    homeActivity);
        });
        alertDialog = builder.show();
    }

    /**
     * This function is called to create new album
     */
    private void createAlertDialogToChooseAlbumName(List<String> albumsList,
                                                    ArrayAdapter<String> adapter,
                                                    ImageAdapterParameter homeActivity) {
        final EditText input = new EditText(homeActivity.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        new AlertDialog.Builder(homeActivity.getContext())
                .setTitle("Album name")
                .setMessage("Input name")
                .setView(input)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String albumName = input.getText().toString();
                        albumsList.add(albumName);
                        adapter.notifyDataSetChanged();
                        if (dialog != null)
                            dialog.dismiss();
                    }
                }).show();
    }
}
