package com.avelycure.photogallery.albums;

import android.content.Context;

public class AlbumAdapterParameterImpl implements AlbumAdapterParameter{
    private Context context;

    public AlbumAdapterParameterImpl(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
