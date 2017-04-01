package thesis.uom.pikedia.ui.crowdsourcingmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.camera.FeatureCaptureActivity;

/**
 * Created by SterlingRyan on 3/25/2017.
 */

public class FeaturePhotographyTipsActivity extends AppCompatActivity {
    private ImageView mNextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photography_tips);

        mNextButton = (ImageView) findViewById(R.id.imageViewNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeatureCaptureActivity.class);
                startActivity(intent);
            }
        });
    }
}
