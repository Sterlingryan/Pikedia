package thesis.uom.pikedia.ui.factswizard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/20/2017.
 */

public class BasicInformationTwoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_two);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.structure_information);
        toolbar.setTitleTextColor(Color.WHITE);
    }
}
