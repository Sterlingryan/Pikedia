package thesis.uom.pikedia.ui.participantsinformation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.camera.CameraActivity;
import thesis.uom.pikedia.ui.experimentwizard.PhotographyTipsActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_personal_information_two);

        initialize();

    }

    public void initialize(){
        /* Initialize views */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mBackButton = (ImageView) findViewById(R.id.backBtn);
        mCourseEditText = (MaterialEditText) findViewById(R.id.materialEditTextCourse);
        mLocationSpinner= (MaterialSpinner) findViewById(R.id.spinnerLocation);
        mSmartPhoneOwnershipSpinner = (MaterialSpinner) findViewById(R.id.spinnerSmartPhoneOS);
        mSmartPhoneYearsofOwnerShipSpinner = (MaterialSpinner) findViewById(R.id.spinnerSmartPhoneYears);
        toolbar.setTitle(R.string.ttl_basic_information);
        toolbar.setTitleTextColor(Color.WHITE);

        /* Initialize view functionality */
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotographyTipsActivity.class);
                startActivity(intent);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] locations = {"Attard", "Balzan", "Birgu", "Birkirkara","Birzebbuga", "Bormla", "Dingli", "Fgura","Floriana","Fontana","Ghajnsielem", "Gharb", "Gharghur", "Ghasri", "Ghaxaq",
                "Gudja","Gzira","Hamrun","Iklin","Imdina","Imgarr","Imqabba","Imsida","Imtarfa","Isla","Kalkara","Kercem","Kirkop","Lija","Luqa","Marsa","Marsaskala","Marsaxlokk","Mellieha","Mosta"
                ,"Munxar","Nadur","Naxxar","Paola","Pembroke","Pieta","Qala","Qormi","Qrendi","Rabat","Rabat","Safi","San Gwann","San Giljan","San Lawrenz","Saint Lucia","Saint Paul/'s Bay","Saint Venera"
                ,"Sannat","Siggiewi","Sliema","Swieqi","Tarxien","Ta/' Xbiex","Valletta","Xaghra","Xewkija","Xghajra","Zabbar","Zebbug","Zejtun","Zurrieq"};
        String[] smartphoneOS = {"Android", "Apple","Windows Mobile","Other","Do not own","35-40", "40+"};
        String[] smartphoneOwnershipYears = {">3 years", "2 years","1 year","<1 year","none"};

        ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        ArrayAdapter<String> smarthPhoneOsSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smartphoneOS);
        ArrayAdapter<String> smartphoneOwnershipSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smartphoneOwnershipYears);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smarthPhoneOsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smartphoneOwnershipSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLocationSpinner.setAdapter(locationSpinnerAdapter);
        mSmartPhoneOwnershipSpinner.setAdapter(smarthPhoneOsSpinnerAdapter);
        mSmartPhoneYearsofOwnerShipSpinner.setAdapter(smartphoneOwnershipSpinnerAdapter);

        /* Hide course details if not student */
        if(!getIntent().getExtras().getBoolean("isStudent")){
            TextView courseTitle = (TextView) findViewById(R.id.textViewCourse);
            courseTitle.setVisibility(View.GONE);
            mCourseEditText.setVisibility(View.GONE);
        }

        /* Set spinner dropdown top at the foot of the its view*/
        ViewTreeObserver vto = mLocationSpinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLocationSpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measurement = mLocationSpinner.getMeasuredHeight() - 10;
                mSmartPhoneOwnershipSpinner.setDropDownVerticalOffset(measurement);
                mSmartPhoneYearsofOwnerShipSpinner.setDropDownVerticalOffset(measurement);
                mLocationSpinner.setDropDownVerticalOffset(measurement);
            }
        });
    }
}
