package com.example.fyp_footballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Squad extends Activity {

	ImageButton[] playerButtons = new ImageButton[10]; 
	PlayerCoordinate[] allPositions;
	RelativeLayout squadLayout;
	float h, w;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater mInflater = LayoutInflater.from(this);  
		View contentView = mInflater.inflate(R.layout.activity_squad, null); 
		squadLayout = (RelativeLayout) contentView.findViewById(R.id.fullSquadLayoutActivitySquad);
		setContentView(squadLayout);
	
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		h = displaymetrics.heightPixels;
		w = displaymetrics.widthPixels;
		setDefaultSquad();
		
	// an attempt to listen for button presses	
		playerButtons[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.class., menu);
		return true;
	}



	public void setDefaultSquad() {
		
		PlayerCoordinate rb = new PlayerCoordinate((w/10)*7.6,(h/10)*5.5);
		PlayerCoordinate cb = new PlayerCoordinate((w/10)*5.6 ,(h/10)*6);
		PlayerCoordinate cb2 = new PlayerCoordinate((w/10)*3.6, (h/10)*6);
		PlayerCoordinate lb = new PlayerCoordinate((w/10)*1.6,(h/10)*5.5);
		
		PlayerCoordinate rm = new PlayerCoordinate((w/10)*7.6,(h/10) * 3.5);
		PlayerCoordinate cm = new PlayerCoordinate((w/10)*5.6,(h/10) * 4);
		PlayerCoordinate cm2 = new PlayerCoordinate((w/10)*3.6,(h/10) * 4);
		PlayerCoordinate lm = new PlayerCoordinate((w/10)*1.6,(h/10) * 3.5);
		
		PlayerCoordinate rf = new PlayerCoordinate((w/10)*5.6,(h/10)*1.8);
		PlayerCoordinate lf = new PlayerCoordinate((w/10)*3.6,(h/10) *1.8);

		PlayerCoordinate[] playerPositions = {rb,cb,cb2,lb,rm,cm,cm2,lm,rf,lf};
		allPositions = playerPositions;

		for(int i=0; i<allPositions.length; i++) {
			PlayerCoordinate p = allPositions[i];
			ImageButton player = new ImageButton(this);
			player.setBackgroundResource(R.drawable.playericon2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			squadLayout.addView(player);
			playerButtons[i] = player;
		}

		setContentView(squadLayout);
	}
	



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		for(int i=0; i<playerButtons.length; i++) {
			if(playerButtons[i] == null) {
				return false;
			}
		}
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			boolean pressed=false;
/*			for(int i=0; i<playerButtons.length; i++) {
				pressed = playerButtons[i].isPressed();
				if(pressed) {
					// here I need to move a button to see if anything happens
				//	ImageButton p = new ImageButton(this);
				//	p.setBackgroundResource(R.drawable.playericon2);
				//	p.setX(w - (w/2));
				//	p.setY(h - (h/2));
				
					Button p = new Button(this);
					p.setText("new button created");
					p.setX(event.getX() - 150);
					p.setY(event.getY() - 150);
					squadLayout.addView(p);
					setContentView(squadLayout);
				}

			}
			*/
			Button p = new Button(this);
			p.setText("new button created");
			p.setX(event.getX() - 150);
			p.setY(event.getY() - 150);
			squadLayout.addView(p);
			setContentView(squadLayout);
		
		}
		return false;
	}

	
	
}
