package datapole.dbmsdhruvrathi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import datapole.dbmsdhruvrathi.model.Playlist;

/**
 * Created by dhruv on 18/2/17.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "songsDB";   //database name
    public static final String TABLE_SONGS = "songs";//songs' table name
    public static final String TABLE_PLAYLIST = "playlist";//playlists' table name
    public static final String TABLE_CONTAINS = "contains";//relationship table name

    //SONGS TABLE COLUMNS
    public static final String KEY_ID = "id";
    public static final String KEY_SONG_ID = "songID";
    //PLAYLIST TABLE COLUMNS
    public static final String KEY_PLAYLIST_ID = "playlistID";
    public static final String KEY_PLAYLIST_NAME = "NAME";
//    //CONTAINS TABLE COLUMNS
//    public static final String KEY_PLAY_ID="playID";
//    public static final String KEY_SON_ID


    private static final String TAG = "DataBaseHandler";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SONGS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SONGS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_SONG_ID + " INTEGER" + ")";

        String CREATE_PLAYLIST_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_PLAYLIST + "(" +
                KEY_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_PLAYLIST_NAME + " TEXT UNIQUE" + ")";

        String CREATE_CONTAINS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTAINS + "(" +
                KEY_PLAYLIST_ID + " INTEGER," + KEY_SONG_ID + " INTEGER," +
                "FOREIGN KEY(" + KEY_PLAYLIST_ID + ") REFERENCES " + TABLE_PLAYLIST + "(" + KEY_PLAYLIST_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY(" + KEY_SONG_ID + ") REFERENCES " + TABLE_SONGS + "(" + KEY_SONG_ID + "));";

        db.execSQL(CREATE_SONGS_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
        db.execSQL(CREATE_CONTAINS_TABLE);
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

    public void addPlaylist(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYLIST_NAME, name);

        db.insert(TABLE_PLAYLIST, null, values);
        db.close();
    }

    public void addSongToPlaylist(int songID, int playlistID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYLIST_ID, playlistID);
        values.put(KEY_SONG_ID, songID);

        db.insert(TABLE_CONTAINS, null, values);
        db.close();
    }

    public ArrayList<Integer> getPlaylistSongs(String varPass) {
        ArrayList<Integer> allSongsID = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_SONG_ID + " FROM " + TABLE_CONTAINS + " where " + KEY_PLAYLIST_ID.equals(varPass);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(TAG, "selectQ:: " + selectQuery);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Log.d(TAG, "songsList:: " + id);
                allSongsID.add(id);
            } while (cursor.moveToNext());
        }
        return allSongsID;
    }


    public ArrayList<Integer> getSongsList() {
        Log.d(TAG, "afterArr");
        ArrayList<Integer> allSongsID = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_SONG_ID + " FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(TAG, "selectQ:: " + selectQuery);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Log.d(TAG, "songsList:: " + id);
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

    public ArrayList<Playlist> getPlaylistList() {
        ArrayList<Playlist> mList = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_PLAYLIST_ID + "," + KEY_PLAYLIST_NAME + " FROM " + TABLE_PLAYLIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(TAG, "selectQ:: " + selectQuery);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Log.d(TAG, "songsList:: " + id);
                mList.add(new Playlist(id, name));
            } while (cursor.moveToNext());
        }

        String a = "select * from contains";
        cursor = db.rawQuery(a, null);
        if (cursor.moveToFirst()) {
            do {
                int playid = cursor.getInt(0);
                int sid = cursor.getInt(1);
                Log.d(TAG, "haha:: " + playid + "   " + sid);

            } while (cursor.moveToNext());
        }
        //return array list of all available playlists
        return mList;
    }

//    public void deleteContact(int songID) {
//        int id = 1; // we dont have a method to get the number at which it is to be updated yet, vo mil jaye to table mn deletion ho jayega aaram se
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SONGS, KEY_ID + " = ?",
//                new String[]{String.valueOf(id)});
//        db.close();
//    }
}