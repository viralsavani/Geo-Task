package com.mobile.av.geotask;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Anand on 4/12/2015.
 */
public class PrefsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment()).commit();
    }
}
