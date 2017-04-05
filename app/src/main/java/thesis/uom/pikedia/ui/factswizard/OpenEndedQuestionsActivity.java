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


    private MaterialEditText mPurposeEditText;
    private MaterialEditText mDescriptionEditText;

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
        String[] architectureTypes = {"Statue", "Gate", "University", "Theatre", "Parliament", "Cathedral"};

        /* Initialize view */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mPurposeEditText = (MaterialEditText) findViewById(R.id.materialEditTextPurpose);
        mDescriptionEditText = (MaterialEditText) findViewById(R.id.materialEditTextDescription);

        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Initialize view functions*/
        mTypeOfArchitectureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, architectureTypes);

        mTypeOfArchitectureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDescriptionEditText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Description is empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), OneAnswerQuestionsActivity.class);
                    mCaseStudy.setName(getIntent().getExtras().getString(Constants.CASE_STUDY));
                    mCaseStudy.setParticipantID(mParticipantID);
                    mCaseStudy.setDescription(mDescriptionEditText.getText().toString());
                    intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                    startActivity(intent);
                }

            }
        });
    }
}
