package datapole.dbmsdhruvrathi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import datapole.dbmsdhruvrathi.model.Playlist;

/**
 * Created by dhruv on 29/12/16.
 */

public class allCardRecyclerViewAdapter
        extends RecyclerView
        .Adapter<allCardRecyclerViewAdapter
        .DataObjectHolder> {

    public static final String TAG = "myRecViewAdapter";
    private ArrayList<CardObject1> mCardSet;
    private static MyClickListener myClickListener;
    private Context context;
    public DataBaseHandler db;

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView imageDrawable;
        TextView txtSongName;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageDrawable = (ImageView) itemView.findViewById(R.id.img_card);
            txtSongName = (TextView) itemView.findViewById(R.id.txt_song_name);

            Log.d(TAG, "dataObjHolderALLCARDS");
            itemView.setOnClickListener(this);
        }

        int selected = 0;
        ArrayList<String> playNames=new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();

        @Override
        public void onClick(View v) {
            //display dialog
            db = new DataBaseHandler(context);
            playNames.clear();
            map.clear();
            selected=0;

            ArrayList<Playlist> playlistList = new ArrayList<>();
            playlistList=db.getPlaylistList();

            for (int i = 0; i < playlistList.size(); i++) {
                playNames.add(playlistList.get(i).getName());
                map.put(playlistList.get(i).getName(), playlistList.get(i).getPid());
            }
            final String [] plays = playNames.toArray(new String[playNames.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Set the dialog title
            builder.setTitle("Choose playlists to add to").
                    setSingleChoiceItems(plays, selected,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selected = map.get(plays[which]);
                                }
                            })
                    // Set the action buttons
                    .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            db.addSongToPlaylist(getAdapterPosition(), selected);
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            builder.create().show();


            //db.addSong(getAdapterPosition());
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public allCardRecyclerViewAdapter(ArrayList<CardObject1> myCardSet, Context context) {
        mCardSet = myCardSet;
        for (int i = 0; i < myCardSet.size(); i++) {
            Log.d(TAG, "mCardSetVal: " + mCardSet.get(i).getTxtName());
        }
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_file, parent, false);

        db = new DataBaseHandler(parent.getContext());
        Log.d(TAG, "onCrateViewHolderOfAllCArds");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.txtSongName.setText(mCardSet.get(position).getTxtName());
        Log.d(TAG, "mCardValS: " + mCardSet.get(position).getTxtName());
        holder.imageDrawable.setImageResource(R.drawable.headone);
    }

    public void addItem(CardObject1 cardObject, int index) {
        mCardSet.add(index, cardObject);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mCardSet.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        int x = 0;
        if (mCardSet == null) {
            x = 0;
        } else {
            x = mCardSet.size();
        }
        return x;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}