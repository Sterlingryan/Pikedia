package thesis.uom.pikedia.ui.participantsinformation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/21/2017.
 */

public class ParticipantsBasicInformationTwo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_personal_information_two);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.basic_information);
        toolbar.setTitleTextColor(Color.WHITE);
    }
}
