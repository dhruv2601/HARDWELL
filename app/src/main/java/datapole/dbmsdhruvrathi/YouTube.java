package datapole.dbmsdhruvrathi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thefinestartist.finestwebview.FinestWebView;
import com.thefinestartist.finestwebview.FinestWebViewActivity;

public class YouTube extends AppCompatActivity {

    public String songsYoutube[] = {
            "https://www.youtube.com/watch?v=eyGm-dpJWn0",
            "https://www.youtube.com/watch?v=K5pRVyLaIZ8",
            "https://www.youtube.com/watch?v=G5Mv2iV0wkU",
            "https://www.youtube.com/watch?v=sVzvRsl4rEM",
            "https://www.youtube.com/watch?v=oOnXN43yqeE"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

//        getSupportActionBar().hide();

        Intent i = getIntent();
        int songID = i.getIntExtra("songID", 0);

        FinestWebView.Builder builder = new FinestWebView.Builder(this);
        builder.titleDefault("PEACE IS THE MISSION");
        builder.show(songsYoutube[songID]);
        builder.backPressToClose(true);
        builder.webViewJavaScriptEnabled(true);

        Intent intent = new Intent(this, FinestWebViewActivity.class);
        intent.putExtra("builder",builder);
        this.startActivity(intent);

//        new FinestWebView.Builder(this).show(songsYoutube[songID]);
    }
}
