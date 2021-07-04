package com.avelycure.photogallery.utils;

public class CardModel {
    private String url;
    private boolean liked;

    public CardModel(String url, boolean liked) {
        this.url = url;
        this.liked = liked;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
