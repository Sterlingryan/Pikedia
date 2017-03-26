package thesis.uom.pikedia.ui.factswizard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/19/2017.
 */

public class BasicInformationOneActivity extends AppCompatActivity{

    private LinearLayout mNextButton;
    private ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_one);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.ttl_structure_information);
        toolbar.setTitleTextColor(Color.WHITE);

        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinnerTypeOfArchitecture);
        spinner.setAdapter(adapter);

        MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinnerArtisticStyle);
        spinner2.setAdapter(adapter);

        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasicInformationTwoActivity.class);
                startActivity(intent);
            }
        });

        mBackButton = (ImageView) findViewById(R.id.backBtn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
