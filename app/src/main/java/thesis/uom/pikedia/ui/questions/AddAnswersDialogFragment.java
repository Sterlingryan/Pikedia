package thesis.uom.pikedia.ui.questions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 4/3/2017.
 */

public class AddAnswersDialogFragment extends DialogFragment{

    EditText mEditTextNewElement;
    String mName;
    String mPushID;

    private DatabaseReference mAttributesDatabase;


    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddAnswersDialogFragment newInstance(String name,String pushID) {
        AddAnswersDialogFragment addAnswersDialogFragment = new AddAnswersDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("pushID", pushID);
        addAnswersDialogFragment.setArguments(bundle);
        return addAnswersDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mName = getArguments().getString("name");
        mPushID = getArguments().getString("pushID");
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        builder.setTitle("Add an answer");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialogue_add_element, null);
        mEditTextNewElement = (EditText) rootView.findViewById(R.id.edittextElement);


        mEditTextNewElement.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addElement();
                }
                return true;
            }
        });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addElement();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    /**
     * Add new active list
     */
    public void addElement() {
        String element = mEditTextNewElement.getText().toString();
        if (!element.isEmpty()) {
            mAttributesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mName).child(Constants.CASE_STUDY_QUESTIONS).child(mPushID).child(Constants.CASE_STUDY_ANSWERS).push().child("answer");
            mAttributesDatabase.setValue(element);
        }
    }
}
