package thesis.uom.pikedia.ui.camera;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.ui.factswizard.FeaturesDescriptionActivity;
import thesis.uom.pikedia.ui.factswizard.OpenEndedQuestionsActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/23/2017.
 */

public class CameraIntentPhotoPreview extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mPreviewImageView;
    private ImageButton mFinishImageView;
    private ImageView mBackImageButton;
    private int counter = 0;
    private ImageView mCurrentLocation;
    private CaseStudy mCaseStudy;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        initialize();
        hideSystemUI();
        dispatchTakePictureIntent();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(this).load(stream.toByteArray())
                    .asBitmap().into(mPreviewImageView);
        }
    }

    private void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |  View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    private void initialize(){
        mPreviewImageView = (ImageView) findViewById(R.id.imageViewPhotographPreview);
        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    counter++;
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    mCurrentLocation = new ImageView(getApplicationContext());
                    lp.setMargins(x, y, 0, 0);
                    mCurrentLocation.setLayoutParams(lp);
                    mCurrentLocation.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_location_searching_black_24dp));
                    ((ViewGroup) v).addView(mCurrentLocation);
                    showAddFeatureDialogue(mCaseStudy.getName(),mCaseStudy.getParticipantID() );
                }
                return false;
            }
        });

        mFinishImageView = (ImageButton) findViewById(R.id.imageViewAccept);
        mFinishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 0){
                    Toast.makeText(getApplicationContext(), "Tag the photo preview for building features", Toast.LENGTH_LONG).show();
                } else {
                TagReadyDialogIntentFragment tagReadyDialogIntentFragment = new TagReadyDialogIntentFragment();
                tagReadyDialogIntentFragment.show(getFragmentManager(), "TagReadyDialogueIntentFragment");
            }
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

    private void showAddFeatureDialogue(String name, String participantID) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddIntentFeatureDialogFragment.newInstance(name, participantID);
        dialog.show(getFragmentManager(), "AddFeatureDialogFragment");
        hideSystemUI();
    }

    public void removeView(){
        counter--;
        ((ViewGroup) mCurrentLocation.getParent()).removeView(mCurrentLocation);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public void continueExperiment(){
        Intent intent = new Intent(getApplicationContext(), FeaturesDescriptionActivity.class);
        intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
        intent.putExtra(Constants.CASE_STUDY_TIME_IN_MILISECONDS, getIntent().getExtras().getLong(Constants.CASE_STUDY_TIME_IN_MILISECONDS));
        startActivity(intent);
    }

}
