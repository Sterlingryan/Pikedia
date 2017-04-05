package thesis.uom.pikedia.ui.questions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import thesis.uom.pikedia.R;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 4/3/2017.
 */

public class QuestionsActivity extends AppCompatActivity {
    private FloatingActionButton mAddQuestionButton;
    private ListView mQuestionListView;
    private ArrayList<String> mQuestionList;
    private ArrayList<String> mPushIDList;
    private String mPushId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initialize();
        changeDataSet();
    }

    private void initialize(){
        mAddQuestionButton = (FloatingActionButton) findViewById(R.id.fab_add_question);
        mQuestionListView = (ListView) findViewById(R.id.list_view_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        mQuestionList = new ArrayList<>();
        mPushIDList = new ArrayList<>();
        toolbar.setTitle("Participant's Questions");
        toolbar.setTitleTextColor(Color.WHITE);


        mAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddQuestionDialog(getIntent().getExtras().getString(Constants.CASE_STUDY));
            }
        });

        mQuestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AnswersActivity.class);
                intent.putExtra("QuestionPushID", mPushIDList.get(position));
                intent.putExtra(Constants.CASE_STUDY, getIntent().getExtras().getString(Constants.CASE_STUDY));
                startActivity(intent);
            }
        });
    }

    private void changeDataSet(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(getIntent().getExtras().getString(Constants.CASE_STUDY));
        mCaseStudiesDatabase.child(Constants.CASE_STUDY_QUESTIONS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestionList.clear();
                QuestionAdapter questionAdapter = new QuestionAdapter();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,Object> AttributeList = (HashMap<String,Object>)child.getValue();
                    mQuestionList.add(AttributeList.get("question").toString());
                    mPushIDList.add(child.getKey());
                }
                mQuestionListView.setAdapter(questionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAddQuestionDialog(String caseStudyName) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddQuestionDialogFragment.newInstance(caseStudyName);
        dialog.show(getFragmentManager(), "AddQuestionDialogFragment");
    }

    private class QuestionAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mQuestionList.size();
        }

        @Override
        public Object getItem(int position) {
            return mQuestionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_question, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.list_item);
            textView.setText(mQuestionList.get(position));
            return convertView;
        }


    }
}
