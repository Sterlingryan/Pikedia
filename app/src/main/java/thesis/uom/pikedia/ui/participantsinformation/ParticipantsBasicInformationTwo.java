package thesis.uom.pikedia.ui.participantsinformation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.Participant;
import thesis.uom.pikedia.ui.MainActivity;
import thesis.uom.pikedia.ui.crowdsourcingmodule.PhotographyTipsActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/21/2017.
 */

public class ParticipantsBasicInformationTwo extends AppCompatActivity {
    private LinearLayout mNextButton;
    private ImageView mBackButton;
    private MaterialEditText mCourseEditText;
    private MaterialSpinner mLocationSpinner;
    private MaterialSpinner mSmartPhoneOwnershipSpinner;
    private MaterialSpinner mSmartPhoneYearsofOwnerShipSpinner;

    private String mParticipantID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_personal_information_two);
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        initialize();
        Log.e("See Here",((Integer)mCourseEditText.getUnderlineColor()).toString());

    }

    public void initialize(){
        String[] locations = {"Attard", "Balzan", "Birgu", "Birkirkara","Birzebbuga", "Bormla", "Dingli", "Fgura","Floriana","Fontana","Ghajnsielem", "Gharb", "Gharghur", "Ghasri", "Ghaxaq",
                "Gudja","Gzira","Hamrun","Iklin","Imdina","Imgarr","Imqabba","Imsida","Imtarfa","Isla","Kalkara","Kercem","Kirkop","Lija","Luqa","Marsa","Marsaskala","Marsaxlokk","Mellieha","Mosta"
                ,"Munxar","Nadur","Naxxar","Paola","Pembroke","Pieta","Qala","Qormi","Qrendi","Rabat","Rabat","Safi","San Gwann","San Giljan","San Lawrenz","Saint Lucia","Saint Paul/'s Bay","Saint Venera"
                ,"Sannat","Siggiewi","Sliema","Swieqi","Tarxien","Ta/' Xbiex","Valletta","Xaghra","Xewkija","Xghajra","Zabbar","Zebbug","Zejtun","Zurrieq"};
        String[] smartphoneOS = {"Android", "Apple","Windows Mobile","Other","Do not own"};
        String[] smartphoneOwnershipYears = {"> 3 years", "2 years","1 year","< 1 year","none"};

        /* Initialize views */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mBackButton = (ImageView) findViewById(R.id.backBtn);
        mCourseEditText = (MaterialEditText) findViewById(R.id.materialEditTextCourse);
        mLocationSpinner = (MaterialSpinner) findViewById(R.id.spinnerLocation);
        mSmartPhoneOwnershipSpinner = (MaterialSpinner) findViewById(R.id.spinnerSmartPhoneOS);
        mSmartPhoneYearsofOwnerShipSpinner = (MaterialSpinner) findViewById(R.id.spinnerSmartPhoneYears);

        /* Initialize view functionality */
        toolbar.setTitle(R.string.ttl_basic_information);
        toolbar.setTitleTextColor(Color.WHITE);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLocationSpinner.getSelectedItemPosition() == 0 || mSmartPhoneOwnershipSpinner.getSelectedItemPosition() == 0 || mSmartPhoneYearsofOwnerShipSpinner.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    writeParticipantDataToDatabase();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(Constants.PARTICIPANT_ID_KEY, mParticipantID);
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


        ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        ArrayAdapter<String> smartPhoneOsSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smartphoneOS);
        ArrayAdapter<String> smartPhoneOwnershipSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smartphoneOwnershipYears);

        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smartPhoneOsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smartPhoneOwnershipSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mLocationSpinner.setAdapter(locationSpinnerAdapter);
        mSmartPhoneOwnershipSpinner.setAdapter(smartPhoneOsSpinnerAdapter);
        mSmartPhoneYearsofOwnerShipSpinner.setAdapter(smartPhoneOwnershipSpinnerAdapter);

        /* Hide course details if not student */
        if(!getIntent().getExtras().getBoolean("isStudent")){
            mCourseEditText.setVisibility(View.GONE);
        }
    }

    private void writeParticipantDataToDatabase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_PARTICIPANTS).push();
        String course = " ";

        if(mCourseEditText.getVisibility() != View.GONE)
            course = mCourseEditText.getText().toString();

        Participant participant = new Participant(getIntent().getExtras().getString(Constants.PARTICIPANT_GENDER_KEY),
                getIntent().getExtras().getString(Constants.PARTICIPANT_AGE_KEY),
                mLocationSpinner.getSelectedItem().toString(),
                mSmartPhoneOwnershipSpinner.getSelectedItem().toString(),
                mSmartPhoneYearsofOwnerShipSpinner.getSelectedItem().toString(),
                getIntent().getExtras().getString(Constants.PARTICIPANT_EMPLOYMENT_KEY),
                course);
        ref.setValue(participant);
        mParticipantID = ref.getKey();
    }
}
