package thesis.uom.pikedia.ui.factswizard;

import com.google.firebase.appindexing.FirebaseAppIndex;
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
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.jorgecastilloprz.FABProgressCircle;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/19/2017.
 */

public class BasicInformationOneActivity extends AppCompatActivity{

    private DatabaseReference mCaseStudiesDatabase;
    private ArrayList<String> mArtisticStyleList;
    private MaterialSpinner mNamesSpinner;
    private MaterialSpinner mTypeOfArchitectureSpinner;
    private MaterialSpinner mArtisticStyleSpinner;
//    private TextView mArchitectureTypeTextView;
//    private TextView mArtisticStyleTextView;
    private ImageView mAddArtisticStyleButton;
    private ArrayAdapter<String> mNamesAdapter;
    private ArrayAdapter<String> mTypeOfArchitectureAdapter;
    private ArrayAdapter<String> mArtisticStyleAdapter;
    private String[] artisticStyle = {"Baroque", "Modern", "Gothic", "Medieval"};

    private LinearLayout mTypeOfArchitectureLayout;
    private LinearLayout mArtisticStyleLayout;
    private ProgressBar mProgressBar;

    public CaseStudy mCaseStudy = new CaseStudy();
    public String mParticipantID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_one);

        initialization();
    }


    private void showAddAttributeDialog(String title, String caseStudyName, String attribute) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddNewElementDialogFragment.newInstance(title, caseStudyName, attribute, mParticipantID);
        dialog.show(getFragmentManager(), "AddAttributeDialogFragment");
    }

    private void initialization(){

        mParticipantID = getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY);
        String[] caseStudies = {"Christ The King Statue", "Valletta City Gate", "New Parliament Building", "Pjazza Teatru Rjal", "Saint John's Co-Cathedral"};
        String[] architectureTypes = {"Statue", "Gate", "University", "Theatre", "Parliament", "Cathedral"};

        /* Initialize view */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNamesSpinner= (MaterialSpinner) findViewById(R.id.spinnerName);
        mTypeOfArchitectureSpinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        mTypeOfArchitectureLayout = (LinearLayout) findViewById(R.id.linearLayoutTypeOfArchitecture);
        mTypeOfArchitectureLayout.setVisibility(View.GONE);
        mArtisticStyleSpinner = (MaterialSpinner) findViewById(R.id.spinnerArtisticStyle);
        mArtisticStyleLayout = (LinearLayout) findViewById(R.id.linearLayoutArtisticStyle);
        mArtisticStyleLayout.setVisibility(View.GONE);
        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mAddArtisticStyleButton = (ImageView) findViewById(R.id.addArtisticStyleImageButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Initialize view functions*/
        mArtisticStyleList = new ArrayList<>();

        mNamesAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, caseStudies);
        mTypeOfArchitectureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, architectureTypes);
        mArtisticStyleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mArtisticStyleList);

        mNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeOfArchitectureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mArtisticStyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mNamesSpinner.setAdapter(mNamesAdapter);
        mTypeOfArchitectureSpinner.setAdapter(mTypeOfArchitectureAdapter);
        mArtisticStyleSpinner.setAdapter(mArtisticStyleAdapter);

        mNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mNamesSpinner.getSelectedItem() != getResources().getString(R.string.poi_name)){
                    mTypeOfArchitectureLayout.setVisibility(View.VISIBLE);
                    mArtisticStyleLayout.setVisibility(View.VISIBLE);
                    retrieveData();
                } else {
                    mTypeOfArchitectureLayout.setVisibility(View.GONE);
                    mArtisticStyleLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNamesSpinner.getSelectedItem() == getResources().getString(R.string.poi_name)){
                    Toast.makeText(getApplicationContext(), "Choose a name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), BasicInformationTwoActivity.class);
                    mCaseStudy.setParticipantID(mParticipantID);
                    mCaseStudy.setArtisticStyle(mArtisticStyleSpinner.getSelectedItem().toString());
                    mCaseStudy.setTypesOfArchitecture(mTypeOfArchitectureSpinner.getSelectedItem().toString());
                    intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                    startActivity(intent);
                }
            }
        });

        mAddArtisticStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add Artistic Style", mNamesSpinner.getSelectedItem().toString(), Constants.CASE_STUDY_ARTISTIC_STYLE);
            }
        });

        /* Set spinner dropdown top at the foot of the its view*/
        ViewTreeObserver vto = mNamesSpinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mNamesSpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measurement = mNamesSpinner.getMeasuredHeight() - 10;
                mNamesSpinner.setDropDownVerticalOffset(measurement);
                mTypeOfArchitectureSpinner.setDropDownVerticalOffset(measurement);
                mArtisticStyleSpinner.setDropDownVerticalOffset(measurement);
            }
        });
    }

    private void retrieveData(){
        mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mNamesSpinner.getSelectedItem().toString());
        mCaseStudiesDatabase.child(Constants.CASE_STUDY_ARTISTIC_STYLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressBar.setVisibility(View.VISIBLE);
                mArtisticStyleAdapter.clear();
                for(String style: artisticStyle){
                    mArtisticStyleList.add(style);
                }
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mArtisticStyleList.add(AttributeList.get("element"));
                    mArtisticStyleSpinner.setAdapter(mArtisticStyleAdapter);
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
