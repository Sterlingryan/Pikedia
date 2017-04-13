package thesis.uom.pikedia.ui.crowdsourcingmodule;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.camera.CameraActivity;
import thesis.uom.pikedia.ui.camera.PhotographPreview;
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

        TextView textView = (TextView) findViewById(R.id.mainTextView);
        ImageView imageView = (ImageView) findViewById(R.id.mainImageView);
        textView.setText(R.string.expwzd_feature_description);
        imageView.setImageResource(R.drawable.ic_edit_location_white_48dp);

        mNextButton = (ImageView) findViewById(R.id.imageViewNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotographPreview.class);
                intent.putExtra("imagepath",getIntent().getExtras().getString("imagepath"));
                intent.putExtra(Constants.PARTICIPANT_ID_KEY, getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY));
                intent.putExtra(Constants.CASE_STUDY, getIntent().getExtras().getString(Constants.CASE_STUDY));
                startActivity(intent);
            }
        });
    }
}
