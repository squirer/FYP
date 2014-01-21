package com.example.fyp_footballmanager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Tactics extends Activity {

	RelativeLayout squadLayout;
	float arrowX;
	float arrowY;
	float distortionX;
	float distortionY;
	float h,w;

	PlayerCoordinate[] playerPositions;
	String savedFormation="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		init();
		
		squadLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					arrowX = event.getX() - distortionX;
					arrowY = event.getY() - distortionY;
					drawTheArrow();
					return true;
				}

				return false;
			}
		});
	}


	public void init() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		h = displaymetrics.heightPixels;
		w = displaymetrics.widthPixels;
		distortionX = (float) (h/59.2);
		distortionY = (float) (w/36);

		LayoutInflater mInflater = LayoutInflater.from(this);  
		View contentView = mInflater.inflate(R.layout.activity_tactics, null); 
		squadLayout = (RelativeLayout) contentView.findViewById(R.id.fullSquadLayout);

		savedFormation = readFromFile();
		copyCoordinatesFromFile(savedFormation);
		showSquad();
		
		setContentView(squadLayout);
	}
	
	public void copyCoordinatesFromFile(String contents) {
		String[] allCoordinates = contents.split(",");
		
		String[] individualCoordinates;
		String x;
		String y;
		PlayerCoordinate[] tempPositions = new PlayerCoordinate[11];
		for(int i=0; i<allCoordinates.length; i++) {
			individualCoordinates = allCoordinates[i].split(" ");
			x = individualCoordinates[0];
			y = individualCoordinates[1];
			PlayerCoordinate p = 
					new PlayerCoordinate(Double.parseDouble(x), Double.parseDouble(y));
			tempPositions[i] = p;
		}
		playerPositions = tempPositions;
	}
	

	private String readFromFile() {

		String ret = "";

		try {
			InputStream inputStream = openFileInput("squad.txt");

			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		}
		catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return ret;
	}


	public void drawTheArrow() {
		ImageButton arrow = new ImageButton(this);
		arrow.setBackgroundResource(R.drawable.arrowtactic);
		arrow.getMeasuredWidth();
		arrow.getMeasuredHeight();
		arrow.setX(arrowX);
		arrow.setY(arrowY);
		squadLayout.addView(arrow);
		setContentView(squadLayout);
	}

	public void showSquad() {
		for(int i=0; i<playerPositions.length; i++) {
			PlayerCoordinate p = playerPositions[i];
			ImageButton player = new ImageButton(this);
			player.setBackgroundResource(R.drawable.orb2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			int imageWidth = (int)w/17;
			int imageHeight = (int)h/27;
			player.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(player);
			setContentView(squadLayout);
		}
	}

}
