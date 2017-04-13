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
    private MaterialSpinner mLanguageSpinner;
    private MaterialSpinner mBuiltBySpinner;
    private MaterialSpinner mArchitectSpinner;
    private MaterialSpinner mReligionSpinner;

    private ArrayAdapter<String> mLanguageAdapter;
    private ArrayAdapter<String> mBuiltByAdapter;
    private ArrayAdapter<String> mArchitectAdapter;
    private ArrayAdapter<String> mReligionAdapter;

    private ArrayList<String> mLanguageList;
    private ArrayList<String> mBuiltByList;
    private ArrayList<String> mArchitectList;
    private ArrayList<String> mReligionList;

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
        mBuiltBySpinner = (MaterialSpinner) findViewById(R.id.spinnerBuiltBy);
        mArchitectSpinner = (MaterialSpinner) findViewById(R.id.spinnerArchitect);
        mReligionSpinner = (MaterialSpinner) findViewById(R.id.spinnerReligion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        ImageView mBackButton = (ImageView) findViewById(R.id.backBtn);
        FloatingActionButton mAddBuiltByButton = (FloatingActionButton) findViewById(R.id.addBuiltByImageButton);
        FloatingActionButton mAddArchitectButton = (FloatingActionButton) findViewById(R.id.addArchitectImageButton);
        FloatingActionButton mReligionButton = (FloatingActionButton) findViewById(R.id.addReligionImageButton);
        FloatingActionButton mLanguageButton = (FloatingActionButton) findViewById(R.id.addLanguageImageButton);

        mBuiltByList = new ArrayList<>();
        mArchitectList = new ArrayList<>();
        mReligionList = new ArrayList<>();
        mLanguageList = new ArrayList<>();

        mLanguageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mLanguageList);
        mBuiltByAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mBuiltByList);
        mArchitectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mArchitectList);
        mReligionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mReligionList);

        mLanguageSpinner.setAdapter(mLanguageAdapter);
        mBuiltBySpinner.setAdapter(mBuiltByAdapter);
        mArchitectSpinner.setAdapter(mArchitectAdapter);
        mReligionSpinner.setAdapter(mReligionAdapter);

        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultipleAnswerQuestionsActivity.class);
                mCaseStudy.setBuiltBy(mBuiltBySpinner.getSelectedItem().toString());
                mCaseStudy.setArchitect(mArchitectSpinner.getSelectedItem().toString());
                mCaseStudy.setReligion(mReligionSpinner.getSelectedItem().toString());
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

        mAddArchitectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add Architect",mCaseStudy.getName(), Constants.CASE_STUDY_ARCHITECT);
            }
        });

        mAddBuiltByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add Builder",mCaseStudy.getName(), Constants.CASE_STUDY_BUILT_BY);
            }
        });

        mReligionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add A Religion",mCaseStudy.getName(), Constants.CASE_STUDY_RELIGION);
            }
        });

        mLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add Language",mCaseStudy.getName(), Constants.CASE_STUDY_LANGUAGES);
            }
        });

        toolbar.setTitle(R.string.ttl_background_information);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void retrieveData(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mCaseStudy.getName());
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
