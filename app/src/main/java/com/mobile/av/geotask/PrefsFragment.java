package com.mobile.av.geotask;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by Anand on 4/12/2015.
 */
public class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String RANGE_PREF = "range";
    public static final String DEFAULT_RANGE = "500";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    /*
    Set default range for first time
     */
    @Override
    public void onResume() {
        super.onResume();
        EditTextPreference editTextPreference_Range = (EditTextPreference) findPreference(RANGE_PREF);
        String range = editTextPreference_Range.getText();
        if(range == null){
            range = DEFAULT_RANGE;
            editTextPreference_Range.setText(range);
        }
        updatePreference(editTextPreference_Range);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(findPreference(key));
    }

    /*
    Update summary of default range as per user input
     */
    private void updatePreference(Preference preference){
        if(preference instanceof EditTextPreference){
            EditTextPreference editTextPreference_Range = (EditTextPreference) preference;
            String range = editTextPreference_Range.getText();
            if(Integer.parseInt(range) < Integer.parseInt(DEFAULT_RANGE)){
                range = DEFAULT_RANGE;
            }
            editTextPreference_Range.setSummary(range);
        }
    }
}
