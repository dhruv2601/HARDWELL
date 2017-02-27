package datapole.dbmsdhruvrathi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dhruv on 18/2/17.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "songsDB";   //database name
    public static final String TABLE_SONGS = "songs";         //table name

    public static final String KEY_ID = "id";
    public static final String KEY_SONG_ID = "songID";
    private static final String TAG = "DataBaseHandler";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONGS_TABLE = "CREATE TABLE " + TABLE_SONGS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_SONG_ID + " INTEGER" + ")";
        db.execSQL(CREATE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public void clearDataBase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, null, null);
        db.close();
    }

    public void addSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SONG_ID, id);


        db.insert(TABLE_SONGS, null, values);
        db.close();
    }

    public ArrayList<Integer> getSongsList() {
        Log.d(TAG, "afterArr");
        ArrayList<Integer> allSongsID = new ArrayList<>();
        String selectQuery = "SELECT "+KEY_SONG_ID+" FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(TAG, "selectQ:: " + selectQuery);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Log.d(TAG,"songsList:: "+id);
                allSongsID.add(id);
            } while (cursor.moveToNext());
        }
        return allSongsID;
    }

    public ArrayList<Integer> getSongsQueue() {
        Log.d(TAG, "afterArr");
        ArrayList<Integer> allSongsID = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(TAG, "selectQ:: " + selectQuery);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(1);
                allSongsID.add(id);
            } while (cursor.moveToNext());
        }
        return allSongsID;
    }

//    public void deleteContact(int songID) {
//        int id = 1; // we dont have a method to get the number at which it is to be updated yet, vo mil jaye to table mn deletion ho jayega aaram se
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SONGS, KEY_ID + " = ?",
//                new String[]{String.valueOf(id)});
//        db.close();
//    }
}
