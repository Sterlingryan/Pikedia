package thesis.uom.pikedia.ui.questions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.ui.factswizard.AddNewElementDialogFragment;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 4/3/2017.
 */

public class AddQuestionDialogFragment extends DialogFragment{
    EditText mEditTextNewElement;
    String mName;

    TextWatcher watcher = new TextWatcher() {
        int mStart = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStart = start + count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            String input = s.toString();
            String capitalizedText = input.toLowerCase();
            capitalizedText.substring(0, 1).toUpperCase();


            if (!capitalizedText.equals(mEditTextNewElement.getText().toString())) {
                mEditTextNewElement.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mEditTextNewElement.setSelection(mStart);
                        mEditTextNewElement.removeTextChangedListener(this);
                    }
                });
                mEditTextNewElement.setText(capitalizedText);
            }
        }
    };

    private DatabaseReference mAttributesDatabase;


    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddQuestionDialogFragment newInstance(String name) {
        AddQuestionDialogFragment addQuestionDialogFragment = new AddQuestionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        addQuestionDialogFragment.setArguments(bundle);
        return addQuestionDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mName = getArguments().getString("name");
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
        builder.setTitle("Add A Question");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialogue_add_question, null);
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
        mEditTextNewElement.addTextChangedListener(watcher);

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
            mAttributesDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_LOCATION_ATTRIBUTES).child(mName).child(Constants.CASE_STUDY_QUESTIONS).push().child("question");
            mAttributesDatabase.setValue(element + " ?");
        }
    }
}
