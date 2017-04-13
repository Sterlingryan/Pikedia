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
    private MaterialSpinner mBuiltBySpinner;
    private MaterialSpinner mArchitectSpinner;
    private MaterialSpinner mReligionSpinner;

    private ArrayAdapter<String> mReligionAdapter;
    private ArrayAdapter<String> mBuiltInAdapter;
    private ArrayAdapter<String> mBuiltByAdapter;
    private ArrayAdapter<String> mArchitectAdapter;
    private ArrayAdapter<String> mTypeOfArchitectureAdapter;

    private ArrayList<String> mBuiltInList;
    private ArrayList<String> mReligionList;
    private ArrayList<String> mBuiltByList;
    private ArrayList<String> mArchitectList;
    private ArrayList<String> mTypeOfArchitectureList;

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
        mBuiltBySpinner = (MaterialSpinner) findViewById(R.id.spinnerBuiltBy);
        mArchitectSpinner = (MaterialSpinner) findViewById(R.id.spinnerArchitect);
        mReligionSpinner = (MaterialSpinner) findViewById(R.id.spinnerReligion);
        mTypeOfArchitectureSpinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        ImageView mBackButton = (ImageView) findViewById(R.id.backBtn);
        FloatingActionButton mAddBuiltByButton = (FloatingActionButton) findViewById(R.id.addBuiltByImageButton);
        FloatingActionButton mAddBuiltInButton = (FloatingActionButton) findViewById(R.id.addBuiltInImageButton);
        FloatingActionButton mAddArchitectButton = (FloatingActionButton) findViewById(R.id.addArchitectImageButton);
        FloatingActionButton mAddTypeOfArchitectureButton = (FloatingActionButton) findViewById(R.id.addTypeOfArchitectureImageButton);
        FloatingActionButton mReligionButton = (FloatingActionButton) findViewById(R.id.addReligionImageButton);

        mBuiltByList = new ArrayList<>();
        mReligionList = new ArrayList<>();
        mBuiltByList = new ArrayList<>();
        mBuiltInList = new ArrayList<>();
        mArchitectList = new ArrayList<>();
        mTypeOfArchitectureList = new ArrayList<>();

        mReligionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mReligionList);
        mBuiltByAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mBuiltByList);
        mBuiltInAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mBuiltInList);
        mArchitectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mArchitectList);
        mTypeOfArchitectureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mTypeOfArchitectureList);

        mReligionSpinner.setAdapter(mReligionAdapter);
        mBuiltBySpinner.setAdapter(mBuiltByAdapter);
        mBuiltInSpinner.setAdapter(mBuiltInAdapter);
        mArchitectSpinner.setAdapter(mArchitectAdapter);
        mTypeOfArchitectureSpinner.setAdapter(mTypeOfArchitectureAdapter);

        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OneAnswerQuestionsTwoActivity.class);
                mCaseStudy.setArchitect(mArchitectSpinner.getSelectedItem().toString());
                mCaseStudy.setBuiltBy(mBuiltBySpinner.getSelectedItem().toString());
                mCaseStudy.setBuiltIn(mBuiltInSpinner.getSelectedItem().toString());
                mCaseStudy.setTypesOfArchitecture(mTypeOfArchitectureSpinner.getSelectedItem().toString());
                mCaseStudy.setReligion(mReligionSpinner.getSelectedItem().toString());
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

        mAddArchitectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add architect",mCaseStudy.getName(), Constants.CASE_STUDY_ARCHITECT);
            }
        });

        mAddBuiltByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add entity",mCaseStudy.getName(), Constants.CASE_STUDY_BUILT_BY);
            }
        });

        mAddBuiltInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add year",mCaseStudy.getName(), Constants.CASE_STUDY_BUILT_IN);
            }
        });

        mAddTypeOfArchitectureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add building Type",mCaseStudy.getName(), Constants.CASE_STUDY_ARCHITECTURE_TYPE);
            }
        });

        mReligionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAdded = true;
                showAddAttributeDialog("Add religion",mCaseStudy.getName(), Constants.CASE_STUDY_RELIGION);
            }
        });


        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Set spinner dropdown top at the foot of the its view*/
        ViewTreeObserver vto = mBuiltBySpinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBuiltBySpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measurement = mBuiltBySpinner.getMeasuredHeight() - 10;
                mBuiltBySpinner.setDropDownVerticalOffset(measurement);
                mReligionSpinner.setDropDownVerticalOffset(measurement);
                mArchitectSpinner.setDropDownVerticalOffset(measurement);
                mBuiltInSpinner.setDropDownVerticalOffset(measurement);
                mTypeOfArchitectureSpinner.setDropDownVerticalOffset(measurement);
            }
        });
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
                if(mIsAdded){
                    mBuiltInSpinner.setSelection(mBuiltByList.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_BUILT_BY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBuiltByAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mBuiltByList.add(AttributeList.get("element"));
                    mBuiltBySpinner.setAdapter(mBuiltByAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_ARCHITECT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArchitectAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mArchitectList.add(AttributeList.get("element"));
                    mArchitectSpinner.setAdapter(mArchitectAdapter);
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

        mCaseStudiesDatabase.child(Constants.CASE_STUDY_RELIGION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReligionAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mReligionList.add(AttributeList.get("element"));
                    mReligionSpinner.setAdapter(mReligionAdapter);
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
