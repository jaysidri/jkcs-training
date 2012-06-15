package com.jahufar.openexchange.activity;

import com.jahufar.openexchange.R;
import com.jahufar.openexchange.R.xml;

import android.os.Bundle;

public class PreferenceActivity extends android.preference.PreferenceActivity {
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.pref_setting);				
	}

}
