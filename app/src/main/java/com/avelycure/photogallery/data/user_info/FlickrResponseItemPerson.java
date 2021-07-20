package com.avelycure.photogallery.data.user_info;


import com.google.gson.annotations.SerializedName;

/*
{"person":{
"id":"154611951@N04",
"nsid":"154611951@N04",
"ispro":0,
"can_buy_pro":1,
"iconserver":"4638",
"iconfarm":5,
"path_alias":"andreipostolache",
"has_stats":0,
"username":{"_content":"andreipostolache"},
"realname":{"_content":"Andrei Postolache"},
"location":{"_content":""},
"description":{"_content":""},
"photosurl":{"_content":"https:\/\/www.flickr.com\/photos\/andreipostolache\/"},
"profileurl":{"_content":"https:\/\/www.flickr.com\/people\/andreipostolache\/"},
"mobileurl":{"_content":"https:\/\/m.flickr.com\/photostream.gne?id=154579812"},
"photos":{"firstdatetaken":{"_content":"2016-09-22 15:39:47"},"firstdate":{"_content":"1514845181"},"count":{"_content":46}},
"has_adfree":0,
"has_free_standard_shipping":0,
"has_free_educational_resources":0},
"stat":"ok"}
 */
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
