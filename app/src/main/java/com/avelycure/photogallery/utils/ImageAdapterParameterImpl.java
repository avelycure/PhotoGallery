package com.avelycure.photogallery.utils;

import android.content.Context;

public class ImageAdapterParameterImpl implements ImageAdapterParameter {
    Context context;

    public ImageAdapterParameterImpl(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
