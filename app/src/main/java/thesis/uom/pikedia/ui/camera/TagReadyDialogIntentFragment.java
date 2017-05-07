package thesis.uom.pikedia.ui.camera;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 4/25/2017.
 */

public class TagReadyDialogIntentFragment extends DialogFragment {
    private TextView mTextView;
    private boolean mIsAdded = false;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static TagReadyDialogFragment newInstance() {
        TagReadyDialogFragment tagReadyDialogFragment = new TagReadyDialogFragment();
        return tagReadyDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ready Tagging?");

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialogue_textview, null);
        mTextView = (TextView) rootView.findViewById(R.id.textView);
        mTextView.setText(R.string.diafrg_tag_ready);



        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Ready", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ((CameraIntentPhotoPreview) getActivity()).continueExperiment();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
