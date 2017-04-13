package thesis.uom.pikedia.ui.factswizard;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.ui.MainActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/20/2017.
 */

public class OneAnswerQuestionsTwoActivity extends AppCompatActivity {
    private MaterialSpinner mArtisticStyleSpinner;
    private MaterialSpinner mLanguageSpinner;
    private MaterialSpinner mEntranceFeesSpinner;
    private MaterialSpinner mDepictionSpinner;

    private ArrayAdapter<String> mLanguageAdapter;
    private ArrayAdapter<String> mEntranceFeesAdapter;
    private ArrayAdapter<String> mDepictionAdapter;
    private ArrayAdapter<String> mArtisticStyleAdapter;

    private ArrayList<String> mEntranceFeesList;
    private ArrayList<String> mDepictionList;
    private ArrayList<String> mArtisticStyleList;
    private ArrayList<String> mLanguageList;

    private CaseStudy mCaseStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_three);

        initialize();
        retrieveData();
    }

    /**
     * Initialize views
     */
    private void initialize(){
        mLanguageSpinner = (MaterialSpinner) findViewById(R.id.spinnerLanguage);
        mEntranceFeesSpinner = (MaterialSpinner) findViewById(R.id.spinnerEntranceFees);
        mDepictionSpinner = (MaterialSpinner) findViewById(R.id.spinnerDepiction);
        mArtisticStyleSpinner = (MaterialSpinner) findViewById(R.id.spinnerArtisticStyle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        ImageView mBackButton = (ImageView) findViewById(R.id.backBtn);
        FloatingActionButton mAddDepictionButton = (FloatingActionButton) findViewById(R.id.addDepictionImageButton);
        FloatingActionButton mAddEntranceFeesButton = (FloatingActionButton) findViewById(R.id.addEntranceFeeImageButton);
        FloatingActionButton mArtisticStyleButton = (FloatingActionButton) findViewById(R.id.addArtisticStyleImageButton);
        FloatingActionButton mLanguageButton = (FloatingActionButton) findViewById(R.id.addLanguageImageButton);

        mEntranceFeesList = new ArrayList<>();
        mDepictionList = new ArrayList<>();
        mArtisticStyleList = new ArrayList<>();
        mLanguageList = new ArrayList<>();

        mLanguageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mLanguageList);
        mEntranceFeesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mEntranceFeesList);
        mDepictionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mDepictionList);
        mArtisticStyleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mArtisticStyleList);

        mLanguageSpinner.setAdapter(mLanguageAdapter);
        mEntranceFeesSpinner.setAdapter(mEntranceFeesAdapter);
        mDepictionSpinner.setAdapter(mDepictionAdapter);
        mArtisticStyleSpinner.setAdapter(mArtisticStyleAdapter);

        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultipleAnswerQuestionsActivity.class);
                mCaseStudy.setEntranceFees(mEntranceFeesSpinner.getSelectedItem().toString());
                mCaseStudy.setDepiction(mDepictionSpinner.getSelectedItem().toString());
                mCaseStudy.setArtisticStyle(mArtisticStyleSpinner.getSelectedItem().toString());
                mCaseStudy.setLanguageOf(mLanguageSpinner.getSelectedItem().toString());
                intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                intent.putExtra(Constants.CASE_STUDY_ATTTRIBUTE, Constants.CASE_STUDY_MATERIALS);
                startActivity(intent);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddEntranceFeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add fee",mCaseStudy.getName(), Constants.CASE_STUDY_ENTRANCE_FEE);
            }
        });

        mAddDepictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add depiction",mCaseStudy.getName(), Constants.CASE_STUDY_DEPICTION);
            }
        });

        mArtisticStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add an artistic style",mCaseStudy.getName(), Constants.CASE_STUDY_ARTISTIC_STYLE);
            }
        });

        mLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add language",mCaseStudy.getName(), Constants.CASE_STUDY_LANGUAGES);
            }
        });

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Set spinner dropdown top at the foot of the its view*/
        ViewTreeObserver vto = mDepictionSpinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDepictionSpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measurement = mDepictionSpinner.getMeasuredHeight() - 10;
                mDepictionSpinner.setDropDownVerticalOffset(measurement);
                mLanguageSpinner.setDropDownVerticalOffset(measurement);
                mEntranceFeesSpinner.setDropDownVerticalOffset(measurement);
            }
        });
    }

    private void retrieveData(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mCaseStudy.getName());
        mCaseStudiesDatabase.child(Constants.CASE_STUDY_DEPICTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDepictionAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mDepictionList.add(AttributeList.get("element"));
                    mDepictionSpinner.setAdapter(mDepictionAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_ENTRANCE_FEE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEntranceFeesAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mEntranceFeesList.add(AttributeList.get("element"));
                    mEntranceFeesSpinner.setAdapter(mEntranceFeesAdapter);
                }
                mEntranceFeesSpinner.setSelection(mEntranceFeesList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_LANGUAGES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLanguageAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mLanguageList.add(AttributeList.get("element"));
                    mLanguageSpinner.setAdapter(mLanguageAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_ARTISTIC_STYLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArtisticStyleAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mArtisticStyleList.add(AttributeList.get("element"));
                    mArtisticStyleSpinner.setAdapter(mArtisticStyleAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAddAttributeDialog(String title, String caseStudyName, String attribute) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddNewElementDialogFragment.newInstance(title, caseStudyName, attribute, mCaseStudy.getParticipantID());
        dialog.show(getFragmentManager(), "AddAttributeDialogFragment");
    }
}
