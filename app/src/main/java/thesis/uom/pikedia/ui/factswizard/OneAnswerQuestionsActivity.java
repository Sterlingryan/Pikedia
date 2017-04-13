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
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/20/2017.
 */

public class OneAnswerQuestionsActivity extends AppCompatActivity {
    private MaterialSpinner mBuiltInSpinner;
    private MaterialSpinner mTypeOfArchitectureSpinner;
    private MaterialSpinner mArtisticStyleSpinner;
    private MaterialSpinner mDepictionSpinner;


    private ArrayAdapter<String> mBuiltInAdapter;
    private ArrayAdapter<String> mTypeOfArchitectureAdapter;
    private ArrayAdapter<String> mArtisticStyleAdapter;
    private ArrayAdapter<String> mDepictionAdapter;

    private ArrayList<String> mBuiltInList;
    private ArrayList<String> mTypeOfArchitectureList;
    private ArrayList<String> mArtisticStyleList;
    private ArrayList<String> mDepictionList;

    private boolean mIsAdded = false;

    private CaseStudy mCaseStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_two);

        initialize();
        retrieveData();
    }

    /**
     * Initialize views
     */
    private void initialize(){
        mBuiltInSpinner = (MaterialSpinner) findViewById(R.id.spinnerBuiltIn);
        mArtisticStyleSpinner = (MaterialSpinner) findViewById(R.id.spinnerArtisticStyle);
        mDepictionSpinner = (MaterialSpinner) findViewById(R.id.spinnerDepiction);
        mTypeOfArchitectureSpinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        ImageView mBackButton = (ImageView) findViewById(R.id.backBtn);
        FloatingActionButton mAddBuiltInButton = (FloatingActionButton) findViewById(R.id.addBuiltInImageButton);
        FloatingActionButton mAddDepictionButton = (FloatingActionButton) findViewById(R.id.addDepictionImageButton);
        FloatingActionButton mAddArtisticStyleButton = (FloatingActionButton) findViewById(R.id.addArtisticStyleImageButton);
        FloatingActionButton mAddTypeOfArchitectureButton = (FloatingActionButton) findViewById(R.id.addTypeOfArchitectureImageButton);

        mDepictionList = new ArrayList<>();
        mArtisticStyleList = new ArrayList<>();
        mBuiltInList = new ArrayList<>();
        mTypeOfArchitectureList = new ArrayList<>();

        mDepictionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mDepictionList);
        mArtisticStyleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mArtisticStyleList);
        mBuiltInAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mBuiltInList);
        mTypeOfArchitectureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mTypeOfArchitectureList);

        mDepictionSpinner.setAdapter(mDepictionAdapter);
        mArtisticStyleSpinner.setAdapter(mArtisticStyleAdapter);
        mBuiltInSpinner.setAdapter(mBuiltInAdapter);
        mTypeOfArchitectureSpinner.setAdapter(mTypeOfArchitectureAdapter);

        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OneAnswerQuestionsTwoActivity.class);
                mCaseStudy.setDepiction(mDepictionSpinner.getSelectedItem().toString());
                mCaseStudy.setArtisticStyle(mArtisticStyleSpinner.getSelectedItem().toString());
                mCaseStudy.setBuiltIn(mBuiltInSpinner.getSelectedItem().toString());
                mCaseStudy.setTypesOfArchitecture(mTypeOfArchitectureSpinner.getSelectedItem().toString());
                intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                startActivity(intent);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddArtisticStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add Artistic Style",mCaseStudy.getName(), Constants.CASE_STUDY_ARTISTIC_STYLE);
            }
        });

        mAddBuiltInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add Build Year",mCaseStudy.getName(), Constants.CASE_STUDY_BUILT_IN);
            }
        });

        mAddDepictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add Depiction",mCaseStudy.getName(), Constants.CASE_STUDY_DEPICTION);
            }
        });

        mAddTypeOfArchitectureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add Building Type",mCaseStudy.getName(), Constants.CASE_STUDY_ARCHITECTURE_TYPE);
            }
        });

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void retrieveData(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mCaseStudy.getName());
        mCaseStudiesDatabase.child(Constants.CASE_STUDY_BUILT_IN).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBuiltInAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mBuiltInList.add(AttributeList.get("element"));
                    mBuiltInSpinner.setAdapter(mBuiltInAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_ARCHITECTURE_TYPE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTypeOfArchitectureAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mTypeOfArchitectureList.add(AttributeList.get("element"));
                    mTypeOfArchitectureSpinner.setAdapter(mTypeOfArchitectureAdapter);
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
