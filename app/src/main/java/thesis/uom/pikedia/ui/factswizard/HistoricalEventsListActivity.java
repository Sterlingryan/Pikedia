package thesis.uom.pikedia.ui.factswizard;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/21/2017.
 */

public class HistoricalEventsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_events);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.historical_events);
        toolbar.setTitleTextColor(Color.WHITE);
    }

}
