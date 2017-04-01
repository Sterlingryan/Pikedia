package thesis.uom.pikedia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.crowdsourcingmodule.ExperimentWizardActivity;

/**
 * Created by SterlingRyan on 3/23/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, ExperimentWizardActivity.class);
        startActivity(intent);
    }
}
