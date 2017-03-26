package thesis.uom.pikedia.ui.participantsinformation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/21/2017.
 */

public class ParticipantsBasicInformationOne extends AppCompatActivity {
    private LinearLayout mNextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_personal_information_one);

        /* Set activity title */
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.ttl_basic_information);
        toolbar.setTitleTextColor(Color.WHITE);

        mNextButton = (LinearLayout) findViewById(R.id.nextBtn);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParticipantsBasicInformationTwo.class);
                startActivity(intent);
            }
        });

    }
}
