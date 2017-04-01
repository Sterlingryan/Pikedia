package thesis.uom.pikedia.ui.crowdsourcingmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import thesis.uom.pikedia.R;

/**
 * Created by SterlingRyan on 3/22/2017.
 */

public class PhotographWizardFragment extends Fragment {

    public PhotographWizardFragment() {
    }

    public static PhotographWizardFragment newInstance() {
        PhotographWizardFragment fragment = new PhotographWizardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_photograph, container, false);
        initializeScreen(rootView);
        return rootView;
    }

        public void initializeScreen(View rootView){
            ImageView mMainImage = (ImageView) rootView.findViewById(R.id.mainImageView);
            mMainImage.setImageResource(R.drawable.profporter_logo_white_background);

            TextView mWizardText = (TextView) rootView.findViewById(R.id.mainTextView);
            mWizardText.setText(R.string.expwzd_welcome);
        }
}
