package datapole.dbmsdhruvrathi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MediaActivity extends Fragment {

    private static final String TAG = "MediaAct";
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public FloatingActionButton play;
    public FloatingActionButton next;
    public FloatingActionButton previous;
    public FloatingActionButton youtube;
    public DataBaseHandler db;
    public String songList[] = {
            "onedance",
            "landosky",
            "roses",
            "howyoulike",
            "tvf"
    };

    public String songsYoutube[] = {
            "https://www.youtube.com/watch?v=eyGm-dpJWn0",
            "https://www.youtube.com/watch?v=K5pRVyLaIZ8",
            "https://www.youtube.com/watch?v=G5Mv2iV0wkU",
            "https://www.youtube.com/watch?v=sVzvRsl4rEM",
            "https://www.youtube.com/watch?v=oOnXN43yqeE"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_media, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.all_cards_list_rv);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new allCardRecyclerViewAdapter(getDataSet(), view.getContext());

            play = (FloatingActionButton) view.findViewById(R.id.fab_play);
            next = (FloatingActionButton) view.findViewById(R.id.fab_next);
            previous = (FloatingActionButton) view.findViewById(R.id.fab_previous);
            youtube = (FloatingActionButton) view.findViewById(R.id.fab_youtube);

            db = new DataBaseHandler(getContext());

            final int[] songNo = {0};
            final MediaPlayer[] mPlayer = {MediaPlayer.create(getContext(), R.raw.onedance)};

            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),YouTube.class);
                    startActivity(i);
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ArrayList<Integer> allSongsID = new ArrayList<>();
                    allSongsID = db.getSongsQueue();
                    for (int i = 0; i < allSongsID.size(); i++) {
                        Log.d(TAG, "ids:: " + allSongsID.get(i));
                    }

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
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.onedance);
                                break;
                            case 1:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.landosky);
                                break;
                            case 2:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.roses);
                                break;
                            case 3:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.howyoulike);
                                break;
                            case 4:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.tvf);
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
                                        mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.onedance);
                                        break;
                                    case 1:
                                        mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.landosky);
                                        break;
                                    case 2:
                                        mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.roses);
                                        break;
                                    case 3:
                                        mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.howyoulike);
                                        break;
                                    case 4:
                                        mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.tvf);
                                        break;
                                }
                                mPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mPlayer[0].start();
                            }
                        });
                    }
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayer[0].stop();
                    songNo[0] = ++songNo[0];
                    ArrayList<Integer> allSongsID = new ArrayList<>();
                    allSongsID = db.getSongsQueue();
                    final ArrayList<Integer> finalAllSongsID = allSongsID;
                    if (songNo[0] >= finalAllSongsID.size()) {
                        Toast.makeText(v.getContext(), "Songs in DataBase have ended, need to select new songs!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        int currID = finalAllSongsID.get(songNo[0]);
                        switch (currID) {
                            case 0:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.onedance);
                                break;
                            case 1:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.landosky);
                                break;
                            case 2:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.roses);
                                break;
                            case 3:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.howyoulike);
                                break;
                            case 4:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.tvf);
                                break;
                        }
                        mPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mPlayer[0].start();
                    }
                }
            });

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songNo[0] = --songNo[0];
                    mPlayer[0].stop();
                    ArrayList<Integer> allSongsID = new ArrayList<>();
                    allSongsID = db.getSongsQueue();
                    final ArrayList<Integer> finalAllSongsID = allSongsID;
                    if (songNo[0] < 0) {
                        Toast.makeText(v.getContext(), "No Previous song in Database!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        int currID = finalAllSongsID.get(songNo[0]);
                        switch (currID) {
                            case 0:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.onedance);
                                break;
                            case 1:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.landosky);
                                break;
                            case 2:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.roses);
                                break;
                            case 3:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.howyoulike);
                                break;
                            case 4:
                                mPlayer[0] = MediaPlayer.create(v.getContext(), R.raw.tvf);
                                break;
                        }
                        mPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mPlayer[0].start();
                    }
                }
            });

            mRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((allCardRecyclerViewAdapter) mAdapter).setOnItemClickListener(new allCardRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // handle on item click on each card item i.e. open each card to show details
//                db.addSong(position);
            }
        });
    }

    public ArrayList<CardObject1> getDataSet() {
        String names[] = {
                "One Dance",
                "Lando Sky High",
                "Roses",
                "How you Like..",
                "Humorously yours"
        };

        ArrayList<CardObject1> songs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CardObject1 card = new CardObject1(names[i]);
            songs.add(i, card);
        }
        return songs;
    }
}