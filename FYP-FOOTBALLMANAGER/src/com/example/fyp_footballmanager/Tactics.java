package com.example.fyp_footballmanager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Tactics extends Activity {

	//DrawingTheArrow arrow;
	RelativeLayout squadLayout;
	float arrowX;
	float arrowY;
	Bitmap tactic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_tactics);
		
		//arrow = new DrawingTheArrow(this);
		LayoutInflater mInflater = LayoutInflater.from(this);  
		View contentView = mInflater.inflate(R.layout.activity_tactics, null); 
		squadLayout = (RelativeLayout) contentView.findViewById(R.id.fullSquadLayout);

		setContentView(squadLayout);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//arrow.touched=true;
		//arrow.x = event.getX();
		//arrow.y = event.getY();
		Button testAddButton = new Button(this);
		testAddButton.setText("new button created");
		testAddButton.setX(event.getX() - 150);
		testAddButton.setY(event.getY() - 150);
		squadLayout.addView(testAddButton);
		setContentView(squadLayout);
		return false;
	}
}
