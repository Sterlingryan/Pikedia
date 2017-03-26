package thesis.uom.pikedia.ui.experimentwizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.participantsinformation.ParticipantsBasicInformationOne;

/**
 * Created by SterlingRyan on 3/22/2017.
 */

public class ExperimentWizardActivity extends AppCompatActivity {

    private ImageView mProgressImageView;
    private ImageView mProgressImageView1;
    private ImageView mProgressImageView2;
    private ImageButton mFinishButton;
    private ViewPager viewPager;

    private static final String LOG_TAG = ExperimentWizardActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        mProgressImageView = (ImageView) findViewById(R.id.progressImageView);
        mProgressImageView1 = (ImageView) findViewById(R.id.progressImageView1);
        mProgressImageView2 = (ImageView) findViewById(R.id.progressImageView2);
        mFinishButton = (ImageButton) findViewById(R.id.progressFinish);
        mFinishButton.setVisibility(View.GONE);

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParticipantsBasicInformationOne.class);
                startActivity(intent);
            }
        });

        initializeScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initializeScreen(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
         **/
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        mProgressImageView.setImageResource(R.drawable.circlefilled);
                        mProgressImageView1.setImageResource(R.drawable.circle);
                        mProgressImageView2.setImageResource(R.drawable.circle);
                        break;
                    case 1:
                        mProgressImageView.setImageResource(R.drawable.circle);
                        mProgressImageView1.setImageResource(R.drawable.circlefilled);
                        mProgressImageView2.setImageResource(R.drawable.circle);
                        break;
                    case 2:
                        mProgressImageView.setImageResource(R.drawable.circle);
                        mProgressImageView1.setImageResource(R.drawable.circle);
                        mProgressImageView2.setImageResource(R.drawable.circlefilled);
                        mFinishButton.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mProgressImageView.setImageResource(R.drawable.circlefilled);
                        mProgressImageView1.setImageResource(R.drawable.circle);
                        mProgressImageView2.setImageResource(R.drawable.circle);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.clearOnPageChangeListeners();
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Use positions (0 and 1) to find and instantiate fragments with newInstance()
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:
                    fragment = PhotographWizardFragment.newInstance();
                    break;
                case 1:
                    fragment = ExperimentWizardFragment.newInstance();
                    break;
                case 2:
                    fragment = FeedbackWizardFragment.newInstance();
                    break;
                default:
                    fragment = PhotographWizardFragment.newInstance();
                    break;
            }

            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }
    }
}
