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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
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
    private ArrayList<String> mNamesList;
    private ArrayList<String> mTypeOfArchitectureList;
    private MaterialSpinner mNamesSpinner;
    private MaterialSpinner mTypeOfArchitectureSpinner;
    private DatabaseReference mCaseStudiesDatabase;
    private ImageButton mAddNameButton;
    private ImageButton mAddTypeOfArchitectureButton;
    private ArrayAdapter<String> mNamesAdapter;
    private ArrayAdapter<String> mTypeOfArchitectureAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_one);

        initialization();
        retrieveData();
    }


    private void showAddListDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddNewElementDialogFragment.newInstance();
        dialog.show(BasicInformationOneActivity.this.getFragmentManager(), "AddListDialogFragment");
    }

    private void initialization(){

        /* Initialize view */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNamesSpinner= (MaterialSpinner) findViewById(R.id.spinnerName);
        mTypeOfArchitectureSpinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        mTypeOfArchitectureSpinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mBackButton = (ImageView) findViewById(R.id.backBtn);
        mAddNameButton = (ImageButton) findViewById(R.id.addNameImageButton);
        mAddTypeOfArchitectureButton = (ImageButton) findViewById(R.id.addTypeOfArchitectureImageButton);

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Initialize view functions*/
        mNamesList = new ArrayList<>();
        mTypeOfArchitectureList= new ArrayList<>();
        mNamesAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mNamesList);
        mTypeOfArchitectureAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mTypeOfArchitectureList);
        mNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeOfArchitectureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNamesSpinner.setAdapter(mNamesAdapter);
        mTypeOfArchitectureSpinner.setAdapter(mTypeOfArchitectureAdapter);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasicInformationTwoActivity.class);
                startActivity(intent);
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
                mGenderSpinner.setDropDownVerticalOffset(measurement);
                mAgeSpinner.setDropDownVerticalOffset(measurement);
                mEmploymentSpinner.setDropDownVerticalOffset(measurement);
            }
        });
    }

    private void retrieveData(){
        mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_CASE_STUDIES_NAMES_LIST);
        mCaseStudiesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNamesAdapter.clear();
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
