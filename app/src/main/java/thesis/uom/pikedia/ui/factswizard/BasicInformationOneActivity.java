package thesis.uom.pikedia.ui.factswizard;

import com.google.android.gms.vision.text.Line;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/19/2017.
 */

public class BasicInformationOneActivity extends AppCompatActivity{

    private LinearLayout mNextButton;
    private ImageView mBackButton;
    private DatabaseReference mCaseStudiesDatabase;
    private ArrayList<String> mNamesList;
    private ArrayList<String> mTypeOfArchitectureList;
    private ArrayList<String> mArtisticStyleList;
    private MaterialSpinner mNamesSpinner;
    private MaterialSpinner mTypeOfArchitectureSpinner;
    private MaterialSpinner mArtisticStyleSpinner;
    private ImageButton mAddNameButton;
    private ImageButton mAddTypeOfArchitectureButton;
    private ImageButton mAddArtisticStyleButton;
    private ArrayAdapter<String> mNamesAdapter;
    private ArrayAdapter<String> mTypeOfArchitectureAdapter;
    private ArrayAdapter<String> mArtisticStyleAdapter;

    private LinearLayout mTypeOfArchitectureLayout;
    private LinearLayout mArtisticStyleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_one);

        initialization();
        retrieveData();
    }


    private void showAddListDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddNewElementDialogFragment.newInstance("Add Name", "", "");
        dialog.show(BasicInformationOneActivity.this.getFragmentManager(), "AddListDialogFragment");
    }

    private void initialization(){

        /* Initialize view */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNamesSpinner= (MaterialSpinner) findViewById(R.id.spinnerName);
        mTypeOfArchitectureSpinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        mTypeOfArchitectureLayout = (LinearLayout) findViewById(R.id.linearLayoutTypeOfArchitecture);
        mTypeOfArchitectureLayout.setVisibility(View.GONE);
        mArtisticStyleSpinner = (MaterialSpinner) findViewById(R.id.spinnerArtisticStyle);
        mArtisticStyleLayout = (LinearLayout) findViewById(R.id.linearLayoutArtisticStyle);
        mArtisticStyleLayout.setVisibility(View.GONE);
        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mBackButton = (ImageView) findViewById(R.id.backBtn);
        mAddNameButton = (ImageButton) findViewById(R.id.addNameImageButton);
        mAddTypeOfArchitectureButton = (ImageButton) findViewById(R.id.addTypeOfArchitectureImageButton);
        mAddArtisticStyleButton = (ImageButton) findViewById(R.id.addArtisticStyleImageButton);

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Initialize view functions*/
        mNamesList = new ArrayList<>();
        mNamesList.add("");
        mTypeOfArchitectureList= new ArrayList<>();
        mArtisticStyleList= new ArrayList<>();

        mNamesAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mNamesList);
        mTypeOfArchitectureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mTypeOfArchitectureList);
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
                if(position != 0){
                    mTypeOfArchitectureLayout.setVisibility(View.VISIBLE);
                    mArtisticStyleLayout.setVisibility(View.VISIBLE);
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
                if(mNamesSpinner.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Choose a name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), BasicInformationTwoActivity.class);
                    startActivity(intent);
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListDialog();
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
        mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_CASE_STUDIES_NAMES_LIST);
        mCaseStudiesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNamesAdapter.clear();
                mNamesList.add("");
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> name = (HashMap<String,String>)child.getValue();
                    mNamesList.add(name.get("name"));
                    mNamesSpinner.setAdapter(mNamesAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error retreiving data " + databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
