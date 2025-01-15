package com.chestnut_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chestnut_java.Entities.CustomMarker;

import java.util.ArrayList;
import java.util.List;

public class MarkerDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MarkerDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MARKERS = "markers";

    private static final String KEY_ID = "id";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIMESTAMP = "timestamp";

    public MarkerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MARKERS_TABLE = "CREATE TABLE " + TABLE_MARKERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LATITUDE + " REAL,"
                + KEY_LONGITUDE + " REAL,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_TIMESTAMP + " INTEGER" + ")";
        db.execSQL(CREATE_MARKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKERS);
        onCreate(db);
    }

    public void addMarker(CustomMarker marker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_LATITUDE, marker.getLatitude());
        values.put(KEY_LONGITUDE, marker.getLongitude());
        values.put(KEY_TITLE, marker.getTitle());
        values.put(KEY_DESCRIPTION, marker.getDescription());
        values.put(KEY_TIMESTAMP, marker.getTimestamp());

        db.insert(TABLE_MARKERS, null, values);
        db.close();
    }

    public List<CustomMarker> getAllMarkers() {
        List<CustomMarker> markerList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MARKERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int latIndex = cursor.getColumnIndex(KEY_LATITUDE);
                int lonIndex = cursor.getColumnIndex(KEY_LONGITUDE);
                int titleIndex = cursor.getColumnIndex(KEY_TITLE);
                int descriptionIndex = cursor.getColumnIndex(KEY_DESCRIPTION);

                if (latIndex != -1 && lonIndex != -1 && titleIndex != -1 && descriptionIndex != -1) {
                    CustomMarker marker = new CustomMarker(
                            cursor.getDouble(latIndex),
                            cursor.getDouble(lonIndex),
                            cursor.getString(titleIndex),
                            cursor.getString(descriptionIndex)
                    );
                    markerList.add(marker);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return markerList;
    }
}