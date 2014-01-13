package com.example.fyp_footballmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Fixtures extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fixtures);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
}
