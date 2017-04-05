package thesis.uom.pikedia.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.camera.CameraActivity;
import thesis.uom.pikedia.ui.crowdsourcingmodule.ExperimentWizardActivity;
import thesis.uom.pikedia.ui.factswizard.OpenEndedQuestionsActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/23/2017.
 */

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> mCaseStudyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void initialize(){
        mListView = (ListView) findViewById(R.id.caseStudyGridLayout);
        final String[] caseStudies = {"Christ The King Statue", "Valletta City Gate", "New Parliament Building", "Pjazza Teatru Rjal", "Saint John's Co-Cathedral", "University Of Malta"};

        TextView title = (TextView) findViewById(R.id.appBarTitle);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Spirax-Regular.ttf");
        title.setTypeface(custom_font);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        
        mCaseStudyList = new ArrayList<>();
        for(String string: caseStudies){
            mCaseStudyList.add(string);
        }
        ListAdapter listAdapter = new ListAdapter();
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_click_effect));
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra(Constants.PARTICIPANT_ID_KEY,getIntent().getExtras().getString(Constants.PARTICIPANT_ID_KEY));
                intent.putExtra(Constants.CASE_STUDY, mCaseStudyList.get(position));
                startActivity(intent);
            }
        });

    }

    private class ListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mCaseStudyList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCaseStudyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.cardview_case_study, null);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.textViewTitle);
            titleTextView.setText(mCaseStudyList.get(position));
            ImageView imageView = (ImageView) convertView.findViewById(R.id.caseStudyImageView);
            Glide.with(getApplicationContext()).load(mThumbIds[position]).centerCrop().into(imageView);
            return convertView;
        }
    }

    public Integer[] mThumbIds = {
            R.drawable.christtheking, R.drawable.citygate,
            R.drawable.parliament_house, R.drawable.pjazzateatrurjal,
            R.drawable.st_johns_cocathedral, R.drawable.university_of_malta,
    };

}
