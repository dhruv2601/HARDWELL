package datapole.dbmsdhruvrathi;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import io.github.skyhacker2.sqliteonweb.SQLiteOnWeb;

public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    private static final String TAG = "MainAct1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Business Card Scanner");
        toolbar.setSubtitle("");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addTab(tabLayout.newTab().setText("MEDIA"));    //0
        tabLayout.addTab(tabLayout.newTab().setText("PLAYLISTS"));      //1
        tabLayout.addTab(tabLayout.newTab().setText("DATABASE"));      //2

        Log.d(TAG, "initAdapter");
        viewPager = (ViewPager) findViewById(R.id.pager);
        Log.d(TAG, "initAdapter1");

        Log.d(TAG, "getSupport: " + this.getSupportFragmentManager() + "\n" + tabLayout.getTabCount());
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Log.d(TAG, "setAdapter");

        viewPager.setCurrentItem(0);
        tabLayout.getTabAt(0).setText("MEDIA");
        tabLayout.getTabAt(1).setText("PLAYLISTS");
        tabLayout.getTabAt(2).setText("DATABASE");

        SQLiteOnWeb.init(this,6969).start();
    }
}