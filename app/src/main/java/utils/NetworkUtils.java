package utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    private static int shownPhotos;
    String request;
    JSONArray photo;
    private static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
    private static final String API_KEY_STRING = "17c6829dc9c675db355315a1cab4e9b4";
    private static final String FORMAT_STRING = "json";
    private static final String FLICKR_GET_PHOTO_METHOD = "flickr.photos.search";
    private final int IMPORTED_PHOTOS_PER_REQUEST = 20;
    PhotoGalleryDatabaseHelper photoGalleryDatabaseHelper;
    //SQLiteDatabase databaseWrite;

    public NetworkUtils(Context context, PhotoGalleryDatabaseHelper dbHelper) {
        shownPhotos = 15;
        photoGalleryDatabaseHelper = dbHelper;
    }

    public void updateJSONArray(final String tag, final int pageNum) throws JSONException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String webAddress = createURLToGetJSON(tag, IMPORTED_PHOTOS_PER_REQUEST, pageNum);
                    request = makeRequest(webAddress);

                    //Serializing json, first remove trash from flickr, then taking object photos, then array photo
                    photo = new JSONObject(request.substring(14, request.length() - 2)).getJSONObject("photos").getJSONArray("photo");

                    JSONObject jsonObject;
                    for(int i = 0; i < photo.length(); i++){
                        jsonObject = photo.getJSONObject(i);
                        photoGalleryDatabaseHelper.insertAddress("https://farm" + jsonObject.get("farm") + ".staticflickr.com/" + jsonObject.get("server") +
                                "/" + jsonObject.get("id") + "_" + jsonObject.get("secret") + ".jpg");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Creates url to Flickr to get json array
    public String createURLToGetJSON(String tag, int photosNum, int pageNum) {
        return FLICKR_BASE_URL + FLICKR_GET_PHOTO_METHOD + "&api_key=" + API_KEY_STRING + "&tags=" + tag + "&per_page=" + photosNum
                + "&format=" + FORMAT_STRING + "&page=" + pageNum;
    }

    //Creates url to flickr to get picture
    public String createURLToGetImage(int index) throws JSONException {
        JSONObject jsonObject = photo.getJSONObject(index);
        return ("https://farm" + jsonObject.get("farm") + ".staticflickr.com/" + jsonObject.get("server") +
                "/" + jsonObject.get("id") + "_" + jsonObject.get("secret") + ".jpg");
    }

    public String makeRequest(String webAddress) {
        String result = null;
        try {
            URL url = new URL(webAddress);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            result = FormatEditor.convertStreamToString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}