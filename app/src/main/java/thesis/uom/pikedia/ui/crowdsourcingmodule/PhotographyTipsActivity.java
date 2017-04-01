package thesis.uom.pikedia.ui.crowdsourcingmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.camera.CameraActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/25/2017.
 */

public class PhotographyTipsActivity extends AppCompatActivity {
    private ImageView mNextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photography_tips);

        mNextButton = (ImageView) findViewById(R.id.imageViewNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra(Constants.PARTICIPANT_ID_KEY, getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY));
                startActivity(intent);
            }
        });
    }
}
