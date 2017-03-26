package thesis.uom.pikedia.ui.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.factswizard.FeatureInformationActivity;

/**
 * Created by SterlingRyan on 3/23/2017.
 */

public class FeaturePhotographPreview extends AppCompatActivity {

    private ImageView mPreviewImageView;
    private ImageButton mFinishImageView;
    private ImageView mBackImageButton;
    private String mPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        hideSystemUI();

        mPhotoPath = getIntent().getExtras().getString("imagepath");
        mPreviewImageView = (ImageView) findViewById(R.id.imageViewPhotographPreview);
        Glide.with(this).load(new File(mPhotoPath)).centerCrop().into(mPreviewImageView);

        mFinishImageView = (ImageButton) findViewById(R.id.imageViewAccept);
        mFinishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeatureInformationActivity.class);
                startActivity(intent);
            }
        });

        mBackImageButton = (ImageView) findViewById(R.id.imageViewBack);
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPhotoPath = getIntent().getExtras().getString("imagepath");
        Glide.with(this).load(new File(mPhotoPath)).centerCrop().into(mPreviewImageView);
    }

    private void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |  View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    @Override
    public void finish() {
        super.finish();
        File file = new File(mPhotoPath);
        file.delete();
    }
}
