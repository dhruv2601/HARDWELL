package datapole.dbmsdhruvrathi;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import datapole.dbmsdhruvrathi.model.Playlist;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

public class PlaylistActivity extends Fragment {

    public View view;
    private FloatingActionButton createNew;
    public DataBaseHandler db;
    private static final String TAG = "PlaylistActivity";

    private static final String[][] NO_DATA = {{"There is nothing to show "}, {"in database"}};
    private static final String[][] DATA_TO_SHOW = new String[50][3];

    public String songList[] = {
            "onedance",
            "landosky",
            "roses",
            "howyoulike",
            "tvf"
    };

    final int[] songNo = {0};


    public PlaylistActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.actvity_playlist, container, false);

            db = new DataBaseHandler(getContext());

            ArrayList<Playlist> playlistList = new ArrayList<>();

            playlistList = db.getPlaylistList();

            for (int i = 0; i < playlistList.size(); i++) {
                DATA_TO_SHOW[i][0] = String.valueOf(playlistList.get(i).getPid());
                DATA_TO_SHOW[i][1] = playlistList.get(i).getName();
            }

            final TableView<String[]> tableView = (TableView<String[]>) view.findViewById(R.id.tableView);
            tableView.setDataAdapter(new SimpleTableDataAdapter(view.getContext(), DATA_TO_SHOW));
            tableView.addDataClickListener(new CarClickListener());
            tableView.setHeaderBackground(R.drawable.backgroundone);

            createNew = (FloatingActionButton) view.findViewById(R.id.fab_add_playlist);
            createNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("New PLaylist");
                    alert.setMessage("Enter playlist Name Here");
                    final EditText input = new EditText(getContext());
                    alert.setView(input);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String srt = input.getText().toString();
                            db.addPlaylist(srt);
                        }
                    });
                    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            });
        }
        return view;
    }

    public class CarClickListener implements TableDataClickListener<String[]> {

        @Override
        public void onDataClicked(int rowIndex, String[] clickedCar) {
            ArrayList<Integer> allSongsID = db.getPlaylistSongs(clickedCar[0]);

//            Toast.makeText(getContext(), "Current Playlist playing...", Toast.LENGTH_SHORT).show();
            SharedPreferences pref = getContext().getSharedPreferences("currPlayL",0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("pID",rowIndex);
            editor.commit();

            Log.d(TAG, "insidePl");

            final MediaPlayer[] mPlayer = {MediaPlayer.create(getContext(), R.raw.onedance)};
            if (mPlayer[0].isPlaying() == true) {
                mPlayer[0].stop();
            }

            if (allSongsID.size() == 0) {
                Toast.makeText(getContext(), "No Songs in Database, please add some songs!!", Toast.LENGTH_LONG).show();
            } else {
                final int first = allSongsID.get(0);
                String firstSong = songList[first];
                switch (first) {
                    case 0:
                        mPlayer[0] = MediaPlayer.create(getContext(), R.raw.onedance);
                        break;
                    case 1:
                        mPlayer[0] = MediaPlayer.create(getContext(), R.raw.landosky);
                        break;
                    case 2:
                        mPlayer[0] = MediaPlayer.create(getContext(), R.raw.roses);
                        break;
                    case 3:
                        mPlayer[0] = MediaPlayer.create(getContext(), R.raw.howyoulike);
                        break;
                    case 4:
                        mPlayer[0] = MediaPlayer.create(getContext(), R.raw.tvf);
                        break;
                }
                mPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer[0].start();

                final ArrayList<Integer> finalAllSongsID = allSongsID;
                mPlayer[0].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Code to start the next audio in the sequence
                        songNo[0] = ++songNo[0];
                        int currID = finalAllSongsID.get(songNo[0]);
                        switch (currID) {
                            case 0:
                                mPlayer[0] = MediaPlayer.create(getContext(), R.raw.onedance);
                                break;
                            case 1:
                                mPlayer[0] = MediaPlayer.create(getContext(), R.raw.landosky);
                                break;
                            case 2:
                                mPlayer[0] = MediaPlayer.create(getContext(), R.raw.roses);
                                break;
                            case 3:
                                mPlayer[0] = MediaPlayer.create(getContext(), R.raw.howyoulike);
                                break;
                            case 4:
                                mPlayer[0] = MediaPlayer.create(getContext(), R.raw.tvf);
                                break;
                        }
                        mPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mPlayer[0].start();
                    }
                });
            }
        }
    }
}