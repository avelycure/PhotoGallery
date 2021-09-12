package com.avelycure.photogallery.data.user_info;


import com.google.gson.annotations.SerializedName;

public class FlickrResponseItemPerson {

    @SerializedName("id")
    private String id;

    @SerializedName("nsid")
    private String nsid;

    @SerializedName("ispro")
    private boolean isPro;

    @SerializedName("can_buy_pro")
    private boolean canBuyPro;

    @SerializedName("iconserver")
    private String iconServer;

    @SerializedName("iconfarm")
    private int iconFarm;

    @SerializedName("path_alias")
    private String pathAlias;

    @SerializedName("has_stats")
    private boolean hasStats;

    @SerializedName("username")
    private UserName userName;

    @SerializedName("realname")
    private RealName realName;

    @SerializedName("location")
    private UserLocation location;

    @SerializedName("description")
    private UserDescription description;

    @SerializedName("photosurl")
    private PhotosUrl photosUrl;

    @SerializedName("mobileurl")
    private MobileUrl mobileUrl;

    @SerializedName("profileurl")
    private ProfileUrl profileUrl;

    @SerializedName("photos")
    private Photos photos;

    @SerializedName("has_adfree")
    private boolean hasAddFree;

    @SerializedName("has_free_standard_shipping")
    private boolean hasFreeStandardShipping;

    @SerializedName("has_free_educational_resources")
    private boolean hasFreeEducationalResources;
}
