package thesis.uom.pikedia.ui.camera;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.factswizard.OpenEndedQuestionsActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/23/2017.
 */

public class PhotographPreview extends AppCompatActivity implements AddFeatureDialogFragment.OnCompleteListener {

    private ImageView mPreviewImageView;
    private ImageButton mFinishImageView;
    private ImageView mBackImageButton;
    private String mPhotoPath;
    private boolean mIsSuccessful = true;
    private int counter = 100;

    private ImageView mCurrentLocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        hideSystemUI();
        getWindow().setStatusBarColor(Color.BLACK);

        mPhotoPath = getIntent().getExtras().getString("imagepath");
        mPreviewImageView = (ImageView) findViewById(R.id.imageViewPhotographPreview);
        Glide.with(this).load(new File(mPhotoPath)).centerCrop().into(mPreviewImageView);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    mCurrentLocation= new ImageView(getApplicationContext());
                    lp.setMargins(x, y, 0, 0);
                    mCurrentLocation.setLayoutParams(lp);
                    mCurrentLocation.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_location_on_black_24dp));
                    ((ViewGroup) v).addView(mCurrentLocation);
                    showAddFeatureDialogue(getIntent().getExtras().getString(Constants.CASE_STUDY), getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY));
                }
                return false;
            }
        });

        mFinishImageView = (ImageButton) findViewById(R.id.imageViewAccept);
        mFinishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpenEndedQuestionsActivity.class);
                intent.putExtra(Constants.PARTICIPANT_ID_KEY, getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY));
                intent.putExtra(Constants.CASE_STUDY, getIntent().getExtras().getString(Constants.CASE_STUDY));
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
        hideSystemUI();
    }

    private void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |  View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    private void showAddFeatureDialogue(String name, String participantID) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddFeatureDialogFragment.newInstance(name, participantID);
        dialog.show(getFragmentManager(), "AddFeatureDialogFragment");
        hideSystemUI();
    }

    @Override
    public void finish() {
        super.finish();
        File file = new File(mPhotoPath);
        file.delete();
    }

    @Override
    public void onComplete(boolean isSuccessful) {
        if(!mIsSuccessful){

        }
    }

    public void removeView(){
        ((ViewGroup) mCurrentLocation.getParent()).removeView(mCurrentLocation);
    }

}
