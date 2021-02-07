package utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhotoGalleryDatabaseHelper{

    public static final String DB_NAME = "photoGallery";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public PhotoGalleryDatabaseHelper(Context context) {
        db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE  IF NOT EXISTS images_addresses (_id INTEGER PRIMARY KEY AUTOINCREMENT, ADDRESS TEXT, FLICKR_ID INTEGER);");
        db.execSQL("CREATE TABLE  IF NOT EXISTS liked_photos (_id INTEGER PRIMARY KEY AUTOINCREMENT, ADDRESS TEXT);");
    }

    public void insertAddress(String address, Long flickrId){
        db.execSQL("INSERT INTO images_addresses(ADDRESS, FLICKR_ID) VALUES ('" + address + "'," + flickrId + ");");
    }

    public void addLikedPhoto(String address){
        db.execSQL("INSERT INTO liked_photos(ADDRESS) VALUES ('" + address + "',);");
    }

    public Cursor getAllUsers(){
        return db.rawQuery("SELECT * FROM images_addresses;", null);
    }

    public void clearDatabase(){
        db.execSQL("DROP TABLE 'images_addresses'");
        db.execSQL("CREATE TABLE  IF NOT EXISTS images_addresses (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ADDRESS TEXT, " + "FLICKR_ID INTEGER);");
    }



}
