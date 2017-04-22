package thesis.uom.pikedia.ui.factswizard;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.model.Line;
import thesis.uom.pikedia.ui.MainActivity;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 4/8/2017.
 */

public class FeaturesDescriptionActivity extends AppCompatActivity{

    private ListView mListView;
    private ArrayList<String> mFeaturesArrayList;
    private ArrayList<Line> mLinesArrayList;
    private CaseStudy mCaseStudy = new CaseStudy();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        initialize();
        readData();
    }

    private void initialize(){
        mListView = (ListView) findViewById(R.id.list_view_features);
        mFeaturesArrayList = new ArrayList<>();
        mLinesArrayList = new ArrayList<>();

        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Features Description");
        toolbar.setTitleTextColor(Color.WHITE);

        LinearLayout nextButton = (LinearLayout) findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpenEndedQuestionsActivity.class);
                mCaseStudy.setFeatures(createFeaturesHashmap());
                intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                startActivity(intent);
            }
        });


    }

    private void readData(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mCaseStudy.getName()).child(Constants.CASE_STUDY_FEATURES).child(mCaseStudy.getParticipantID());
        mCaseStudiesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mFeaturesArrayList.add(AttributeList.get("feature"));
                    mLinesArrayList.add(new Line(counter, ""));
                    counter++;
                }
                mListView.setAdapter(new FeaturesAdapter());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private HashMap<String, String> createFeaturesHashmap(){
        HashMap<String,String> features = new HashMap<>();
        for(int i = 0; i < mLinesArrayList.size(); i++){
            features.put(((Integer) i).toString(),mLinesArrayList.get(i).getText());
        }
        return features;
    }

    private class FeaturesAdapter extends BaseAdapter{

        public class ViewHolder{
            TextView textView;
            MaterialEditText editText;
        }

        private void check(int position) {
            for (Line l : mLinesArrayList) {
                l.setFocus(false);
            }
            mLinesArrayList.get(position).setFocus(true);
        }

        @Override
        public int getCount() {
            return mFeaturesArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mFeaturesArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;

            convertView = getLayoutInflater().inflate(R.layout.single_feature, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.featureTextView);
            holder.textView.setText(mFeaturesArrayList.get(position));
            holder.editText = (MaterialEditText) convertView.findViewById(R.id.materialEditTextFeature);
            holder.editText.setHorizontallyScrolling(false);
            holder.editText.setLines(5);
//            holder.editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            convertView.setTag(holder);

            final Line line = mLinesArrayList.get(position);

            if (holder.editText.getTag() instanceof TextWatcher) {
                holder.editText.removeTextChangedListener((TextWatcher) (holder.editText.getTag()));
            }

            if (TextUtils.isEmpty(line.getText())) {
                holder.editText.setTextKeepState("");
            } else {
                holder.editText.setTextKeepState(line.getText());
            }

            if (line.isFocus()) {
                holder.editText.requestFocus();
            } else {
                holder.editText.clearFocus();
            }

            holder.editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        check(position);
                    }
                    return false;
                }
            });

            final TextWatcher watcher = new SimpleTextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        line.setText(null);
                    } else {
                        line.setText(String.valueOf(s));
                    }
                }
            };
            holder.editText.addTextChangedListener(watcher);
            holder.editText.setTag(watcher);

            return convertView;
        }
    }
}
