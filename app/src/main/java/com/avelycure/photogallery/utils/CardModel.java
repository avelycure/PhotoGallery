package com.avelycure.photogallery.utils;

public class CardModel {
    private String url;
    private boolean liked;
    private String userName;
    private Long id;

    public CardModel(String url, boolean liked, String author) {
        this.url = url;
        this.liked = liked;
        this.userName = author;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
