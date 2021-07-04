package com.avelycure.photogallery.utils;

import android.content.Context;

public class ImageAdapterImpl implements ImageAdapterParameter {
    Context context;

    public ImageAdapterImpl(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
