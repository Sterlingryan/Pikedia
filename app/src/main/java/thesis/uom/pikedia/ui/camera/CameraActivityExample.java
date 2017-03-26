package thesis.uom.pikedia.ui.camera;

import android.app.Activity;
import android.os.Bundle;

import thesis.uom.pikedia.R;


public class CameraActivityExample extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, CameraFragment.newInstance())
                    .commit();
        }
    }
}
