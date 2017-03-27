package thesis.uom.pikedia.ui.factswizard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.camera.FeatureCaptureActivity;
import thesis.uom.pikedia.ui.experimentwizard.FeaturePhotographyTipsActivity;

/**
 * Created by SterlingRyan on 3/21/2017.
 */

public class HistoricalEventsListActivity extends AppCompatActivity {

    private LinearLayout mNextButton;
    private ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_events);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.ttl_historical_events);
        toolbar.setTitleTextColor(Color.WHITE);

        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeaturePhotographyTipsActivity.class);
                startActivity(intent);
            }
        });

        mBackButton = (ImageView) findViewById(R.id.backBtn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
