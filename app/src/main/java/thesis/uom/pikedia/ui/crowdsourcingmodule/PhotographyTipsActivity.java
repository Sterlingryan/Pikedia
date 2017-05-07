package thesis.uom.pikedia.ui.crowdsourcingmodule;


import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import thesis.uom.pikedia.R;
import thesis.uom.pikedia.model.CaseStudy;
import thesis.uom.pikedia.ui.camera.CameraActivity;
import thesis.uom.pikedia.ui.camera.PhotographPreview;
import thesis.uom.pikedia.utils.Constants;

/**
 * Created by SterlingRyan on 3/25/2017.
 */

public class PhotographyTipsActivity extends AppCompatActivity {
    private ImageView mNextButton;

    protected String mLatitude;
    protected String mLongitude;

    private LocationManager mLocationManager;
    private MyLocationListener mLocationListener;

    private CaseStudy mCaseStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photography_tips);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        TextView textView = (TextView) findViewById(R.id.mainTextView);
        ImageView imageView = (ImageView) findViewById(R.id.mainImageView);
        textView.setText(R.string.expwzd_feature_description);
        imageView.setImageResource(R.drawable.ic_edit_location_white_48dp);
        mCaseStudy = (CaseStudy) getIntent().getExtras().get(Constants.CASE_STUDY);

        mLocationListener = new MyLocationListener();
        Location l = getLastKnownLocation();
        if (l != null) {
            mLatitude = ((Double)l.getLatitude()).toString();
            mLongitude = ((Double)l.getLongitude()).toString();
        } else {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }

        mNextButton = (ImageView) findViewById(R.id.imageViewNext);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaseStudy.setLongitude(mLongitude);
                mCaseStudy.setLatitude(mLatitude);
                Intent intent = new Intent(getApplicationContext(), PhotographPreview.class);
                intent.putExtra("imagepath",getIntent().getExtras().getString("imagepath"));
                intent.putExtra(Constants.CASE_STUDY_TIME_IN_MILISECONDS, getIntent().getExtras().getLong(Constants.CASE_STUDY_TIME_IN_MILISECONDS));
                intent.putExtra(Constants.CASE_STUDY, mCaseStudy);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(mLocationListener);
    }

    private Location getLastKnownLocation() {
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            mLongitude = "Longitude: " +loc.getLongitude();
            mLatitude = "Latitude: " +loc.getLatitude();

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
}

