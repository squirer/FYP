package com.example.fyp_footballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Squad extends Activity {
	
	
	PlayerCoordinate[] playerPositions = new PlayerCoordinate[10];
	RelativeLayout squadLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// TO ROLLBACK JUST UNCOMMON ACTIVITY SQUAD
		setContentView(R.layout.activity_squad);
		loadCurrentSquadPositions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.class., menu);
		return true;
	}
	
	public void loadCurrentSquadPositions() {
		LayoutInflater mInflater = LayoutInflater.from(this);  
		View contentView = mInflater.inflate(R.layout.activity_tactics, null); 
		squadLayout = (RelativeLayout) contentView.findViewById(R.id.fullSquadLayout);
		
		ImageButton player = new ImageButton(this);
		player.setBackgroundResource(R.drawable.playericon2);
		player.setX(100);
		player.setY(100);
		squadLayout.addView(player);
		setContentView(squadLayout);
	}

}
