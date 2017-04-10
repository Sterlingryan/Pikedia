package thesis.uom.pikedia.ui.participantsinformation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/21/2017.
 */

public class ParticipantsBasicInformationOne extends AppCompatActivity {
    private LinearLayout mNextButton;
    private MaterialSpinner mGenderSpinner;
    private MaterialSpinner mAgeSpinner;
    private MaterialSpinner mEmploymentSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_personal_information_one);

        initialize();
    }

    public void initialize(){

        /* Initialize Views */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mGenderSpinner = (MaterialSpinner) findViewById(R.id.spinnerGender);
        mAgeSpinner = (MaterialSpinner) findViewById(R.id.spinnerAge);
        mEmploymentSpinner = (MaterialSpinner) findViewById(R.id.spinnerEmployment);

        /* Initialize Functionality */
        toolbar.setTitle(R.string.ttl_basic_information);
        toolbar.setTitleTextColor(Color.WHITE);

        String[] gender = {"Male", "Female"};
        String[] age = {"16-20", "20-25","25-30", "30-35","35-40", "40+"};
        String[] employment = {"Full-time Employment", "Part-Time Employment","Student", "UnEmployed"};

        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gender);
        ArrayAdapter<String> ageSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, age);
        ArrayAdapter<String> employmentSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employment);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employmentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mAgeSpinner.setAdapter(ageSpinnerAdapter);
        mEmploymentSpinner.setAdapter(employmentSpinnerAdapter);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isStudent = false;
                if(mGenderSpinner.getSelectedItemPosition() == 0 || mAgeSpinner.getSelectedItemPosition() == 0 || mEmploymentSpinner.getSelectedItemPosition() == 0 )
                {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else{
                    if(mEmploymentSpinner.getSelectedItemPosition() == 3){
                        isStudent = true;
                    }
                    Intent intent = new Intent(getApplicationContext(), ParticipantsBasicInformationTwo.class);
                    intent.putExtra(Constants.PARTICIPANT_GENDER_KEY, mGenderSpinner.getSelectedItem().toString());
                    intent.putExtra(Constants.PARTICIPANT_AGE_KEY, mAgeSpinner.getSelectedItem().toString());
                    intent.putExtra(Constants.PARTICIPANT_EMPLOYMENT_KEY, mEmploymentSpinner.getSelectedItem().toString());
                    intent.putExtra("isStudent", isStudent);
                    startActivity(intent);
                }
            }
        });

        /* Set spinner dropdown top at the foot of the its view*/
        ViewTreeObserver vto = mGenderSpinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGenderSpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measurement = mGenderSpinner.getMeasuredHeight() - 10;
                mGenderSpinner.setDropDownVerticalOffset(measurement);
                mAgeSpinner.setDropDownVerticalOffset(measurement);
                mEmploymentSpinner.setDropDownVerticalOffset(measurement);
            }
        });
    }

}
