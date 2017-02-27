package datapole.dbmsdhruvrathi;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

public class ShowDB extends Fragment {

    private FloatingActionButton refresh;
    private static final String TAG = "ShowDB";
    private View view;
    private AppCompatButton clearDB;
    public DataBaseHandler db;
    public String songList[] = {
            "One Dance",
            "Lando Sky",
            "Roses",
            "How You Like",
            "TVF"
    };

    private static final String[][] NO_DATA = {{"There is nothing to show "}, {"in database"}};
    private static final String[][] DATA_TO_SHOW = new String[50][2];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_show_db, container, false);

            db = new DataBaseHandler(getContext());
            ArrayList<Integer> allSongsID = new ArrayList<>();
            allSongsID = db.getSongsList();
            for (int i = 0; i < allSongsID.size(); i++) {
                DATA_TO_SHOW[i][0] = String.valueOf(i + 1);
                DATA_TO_SHOW[i][1] = songList[allSongsID.get(i)];
            }

            TableView<String[]> tableView = (TableView<String[]>) view.findViewById(R.id.tableView);
            tableView.setDataAdapter(new SimpleTableDataAdapter(view.getContext(), DATA_TO_SHOW));
            tableView.setHeaderBackground(R.drawable.backgroundone);

            TableView<String[]> tablePlaylist = (TableView<String[]>) view.findViewById(R.id.tablePlaylist);
            tablePlaylist.setDataAdapter(new SimpleTableDataAdapter(view.getContext(), DATA_TO_SHOW));
            tablePlaylist.setHeaderBackground(R.drawable.backgroundone);

            clearDB = (AppCompatButton) view.findViewById(R.id.clear_db);
            db = new DataBaseHandler(getContext());
            clearDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.clearDataBase();
                    Toast.makeText(view.getContext(), "Database Cleared", Toast.LENGTH_SHORT).show();
                    // show visuals also
                }
            });
        }
        return view;
    }
}