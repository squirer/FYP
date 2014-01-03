package com.example.fyp_footballmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Tactics extends Activity {

	DrawingTheArrow v;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_tactics);
		v = new DrawingTheArrow(this);
		setContentView(v);
	}

	
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		v = new DrawingTheArrow(this);
		setContentView(v);
		return true;
	}
	*/
}
