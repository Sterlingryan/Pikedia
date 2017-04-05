package thesis.uom.pikedia.ui.questions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 4/3/2017.
 */

public class AnswersActivity extends AppCompatActivity {
    private FloatingActionButton mAddAnswerButton;
    private ListView mAnswersListView;
    private ArrayList<String> mAnswersList;
    private ArrayAdapter<String> mAnswersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        initialize();
        retrieveData();

    }

    private void initialize(){
        mAddAnswerButton = (FloatingActionButton) findViewById(R.id.fab_add_answer);
        mAnswersListView = (ListView) findViewById(R.id.list_view_items);

        mAnswersList = new ArrayList<>();
        mAnswersAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, mAnswersList);
        mAnswersListView.setAdapter(mAnswersAdapter);

        mAddAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAnswersDialog(getIntent().getExtras().getString(Constants.CASE_STUDY),getIntent().getExtras().getString("QuestionPushID"));
            }
        });
    }

    private void showAddAnswersDialog(String caseStudyName, String pushID) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddAnswersDialogFragment.newInstance(caseStudyName, pushID);
        dialog.show(getFragmentManager(), "AddAnswersDialogFragment");
    }

    private void retrieveData(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(getIntent().getExtras().getString(Constants.CASE_STUDY)).child(Constants.CASE_STUDY_QUESTIONS).child(getIntent().getExtras().getString("QuestionPushID"));
        mCaseStudiesDatabase.child(Constants.CASE_STUDY_ANSWERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAnswersAdapter.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mAnswersList.add(AttributeList.get("answer"));
                }
                mAnswersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
