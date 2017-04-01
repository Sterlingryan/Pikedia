package thesis.uom.pikedia.ui.factswizard;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.DialogFragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/31/2017.
 */

public class MultipleAnswerQuestionsActivity extends AppCompatActivity {
    private ListView mList;
    private FloatingActionButton mAddAnswerButton;
    private ArrayList<String> mAnswerList;
    private AnswerAdapter mAnswerAdapter;

    private CaseStudy mCaseStudy;
    private String mAttribute;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_list);

        initiate();
        retrieveData();
    }


    private void initiate(){
        mList = (ListView) findViewById(R.id.list_view_items);
        mAddAnswerButton = (FloatingActionButton) findViewById(R.id.fab_add_item);

        mAnswerList = new ArrayList<>();
        mAnswerAdapter = new AnswerAdapter();

        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);
        mAttribute = getIntent().getExtras().getString(Constants.CASE_STUDY_ATTTRIBUTE);

        mList.setAdapter(mAnswerAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(mAttribute);
        toolbar.setTitleTextColor(Color.WHITE);

        mAddAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttributeDialog("Add " + mAttribute,mCaseStudy.getName(), mAttribute);
            }
        });
    }

    private void retrieveData(){
        DatabaseReference mCaseStudiesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mCaseStudy.getName());
        mCaseStudiesDatabase.child(mAttribute).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAnswerList.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    HashMap<String,String> AttributeList = (HashMap<String,String>)child.getValue();
                    mAnswerList.add(AttributeList.get("element"));
                    mAnswerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAddAttributeDialog(String title, String caseStudyName, String attribute) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddNewElementDialogFragment.newInstance(title, caseStudyName, attribute, mCaseStudy.getParticipantID());
        dialog.show(getFragmentManager(), "AddAttributeDialogFragment");
    }

    private class AnswerAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mAnswerList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAnswerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_answer, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.list_item);
            textView.setText(mAnswerList.get(position));
            return convertView;
        }
    }
}
