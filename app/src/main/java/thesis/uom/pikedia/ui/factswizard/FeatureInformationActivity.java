package thesis.uom.pikedia.ui.factswizard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.crowdsourcingmodule.PointOfInterestShowCaseActivity;

/**
 * Created by SterlingRyan on 3/25/2017.
 */

public class FeatureInformationActivity extends AppCompatActivity {
    private TextView mFinishButton;
    private ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_information);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.ttl_feature_description);
        toolbar.setTitleTextColor(Color.WHITE);

        mFinishButton = (TextView) findViewById(R.id.finishBtn);
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PointOfInterestShowCaseActivity.class);
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
