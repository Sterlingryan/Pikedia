package thesis.uom.pikedia.ui.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/23/2017.
 */

public class PhotographPreview extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        hideSystemUI();

        String imagePath = getIntent().getExtras().getString("imagepath");
        mImageView = (ImageView) findViewById(R.id.imageViewPhotographPreview);
        Glide.with(this).load(new File(imagePath)).centerCrop().into(mImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String imagePath = getIntent().getExtras().getString("imagepath");
        mImageView = (ImageView) findViewById(R.id.imageViewPhotographPreview);
        Glide.with(this).load(new File(imagePath)).centerCrop().into(mImageView);
    }

    private void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |  View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }
}
