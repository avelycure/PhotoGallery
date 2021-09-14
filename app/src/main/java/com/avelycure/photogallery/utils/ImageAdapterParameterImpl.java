package com.avelycure.photogallery.utils;

import android.content.Context;

/**
 * This class is used to provide context to adapters and anywhere else, but without straight calling
 * to it
 */
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
