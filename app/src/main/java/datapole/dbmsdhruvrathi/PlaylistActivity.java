package datapole.dbmsdhruvrathi;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import datapole.dbmsdhruvrathi.model.Playlist;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

public class PlaylistActivity extends Fragment {

    public View view;
    private FloatingActionButton createNew;
    public DataBaseHandler db;
    private static final String TAG = "PlaylistActivity";

    private static final String[][] NO_DATA = {{"There is nothing to show "}, {"in database"}};
    private static final String[][] DATA_TO_SHOW = new String[50][3];

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

            TableView<String[]> tableView = (TableView<String[]>) view.findViewById(R.id.tableView);
            tableView.setDataAdapter(new SimpleTableDataAdapter(view.getContext(), DATA_TO_SHOW));


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

}
