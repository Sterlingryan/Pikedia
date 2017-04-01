package thesis.uom.pikedia.ui.factswizard;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.rengwuxian.materialedittext.MaterialEditText;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/19/2017.
 */

public class OpenEndedQuestionsActivity extends AppCompatActivity{


    private MaterialSpinner mNamesSpinner;
    private MaterialEditText mPurposeEditText;
    private MaterialEditText mDescriptionEditText;

    private ArrayAdapter<String> mNamesAdapter;
    private ArrayAdapter<String> mTypeOfArchitectureAdapter;

    private ProgressBar mProgressBar;

    public CaseStudy mCaseStudy = new CaseStudy();
    public String mParticipantID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_one);

        initialization();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    private void initialization(){

        mParticipantID = getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY);
        String[] caseStudies = {"Christ The King Statue", "Valletta City Gate", "New Parliament Building", "Pjazza Teatru Rjal", "Saint John's Co-Cathedral", "University Of Malta"};
        String[] architectureTypes = {"Statue", "Gate", "University", "Theatre", "Parliament", "Cathedral"};

        /* Initialize view */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNamesSpinner= (MaterialSpinner) findViewById(R.id.spinnerName);
        mPurposeEditText = (MaterialEditText) findViewById(R.id.materialEditTextPurpose);
        mDescriptionEditText = (MaterialEditText) findViewById(R.id.materialEditTextDescription);

        mDescriptionEditText.setVisibility(View.GONE);
        mPurposeEditText.setVisibility(View.GONE);
        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Initialize view functions*/

        mNamesAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, caseStudies);
        mTypeOfArchitectureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, architectureTypes);

        mNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeOfArchitectureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mNamesSpinner.setAdapter(mNamesAdapter);

        mNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mNamesSpinner.getSelectedItem() != getResources().getString(R.string.poi_name)){
                    mPurposeEditText.setVisibility(View.VISIBLE);
                    mDescriptionEditText.setVisibility(View.VISIBLE);
                } else {
                    mPurposeEditText.setVisibility(View.GONE);
                    mDescriptionEditText.setVisibility(View.GONE);
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
                    Intent intent = new Intent(getApplicationContext(), OneAnswerQuestionsActivity.class);
                    mCaseStudy.setName(mNamesSpinner.getSelectedItem().toString());
                    mCaseStudy.setParticipantID(mParticipantID);
                    intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                    startActivity(intent);
                }
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
            }
        });
    }
}
