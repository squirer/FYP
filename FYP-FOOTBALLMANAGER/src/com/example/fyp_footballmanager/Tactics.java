package com.example.fyp_footballmanager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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
	float playX;
	float playY;
	int quadrant;
	float rotateAngle;
	float distortionX;
	float distortionY;
	float h,w;
	int clickedId;
	
	ImageButton[] playerButtons = new ImageButton[11];
	String[] tags = {"gk", "rb", "cb", "cb2", "lb", "rm", "cm", "cm2", "lm", "rf", "lf"};
	PlayerCoordinate[] playerPositions;
	String savedFormation="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		init();
		setClickListenersForPlayers();

		squadLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (clickedId != 0 && event.getAction() == MotionEvent.ACTION_DOWN){
					arrowX = event.getX() - distortionX;
					arrowY = event.getY() - distortionY;
					playX = playerButtons[clickedId].getX();
					playY = playerButtons[clickedId].getY();
					drawTheArrow(playX, playY);
					clickedId = 0;
					return true;
				}
				return false;
			}
		});
	}

	public void rotateArrowToDirection() {
	    rotateAngle = 0;
		// opposite is position away from player
		float opposite = Math.abs(arrowY - playY);
		float adjacent = Math.abs(arrowX - playX);
		float hypotenuse = (float) Math.sqrt((opposite*opposite) + (adjacent * adjacent));
		rotateAngle = (float) Math.asin(opposite/hypotenuse);
		setQuadrant();
	}
	
	public void setQuadrant() {
		if(arrowX < playX && arrowY < playY) {
			rotateAngle += 90;
		}
		if(arrowX > playX && arrowY < playY) {
			rotateAngle = 270-rotateAngle;
		}
		if(arrowX < playX && arrowY > playY) {
			rotateAngle = 90 - rotateAngle;
		}
		if(arrowX > playX && arrowY > playY) {
			rotateAngle = -90 + rotateAngle;
		}
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


	public void setReferencesToButtons() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i] = (ImageButton) squadLayout.findViewWithTag(tags[i]);
		}
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


	public void drawTheArrow(float playX, float playY) {
		int imageWidth = (int)w/17;
		int imageHeight = (int)h/27;
		ImageButton arrow = new ImageButton(this);
	//	arrow.setBackgroundResource(R.drawable.arrowtactic);
	//	arrow.getMeasuredWidth();
	//	arrow.getMeasuredHeight();
		arrow.setX(arrowX);
		arrow.setY(arrowY);

		// this decodes the arrow tactic image and applies
		// a matrix to rotate the arrow at given angle
		Bitmap bMap = BitmapFactory.decodeResource(getResources(),R.drawable.arrowtactic);
		Matrix mat = new Matrix();
		rotateArrowToDirection();
		mat.postRotate(rotateAngle);
		//mat.postRotate(180);
		Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
		                             bMap.getWidth(), bMap.getHeight(), mat, false);
		//BitmapDrawable bmd = new BitmapDrawable(bMapRotate);
		
		arrow.setImageBitmap(bMapRotate);
		// set alpha 45 removes transparency border of arrow
		arrow.getBackground().setAlpha(45);
	//	arrow.setImageDrawable(bmd);
		
		arrow.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
		
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
			player.setTag(tags[i]);
			int imageWidth = (int)w/17;
			int imageHeight = (int)h/27;
			player.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(player);
			setContentView(squadLayout);

			setReferencesToButtons();
		}
	}


	public void setClickListenersForPlayers() {
		playerButtons[1].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 1;
			}
		});

		playerButtons[2].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 2;
			}
		});

		playerButtons[3].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 3;
			}
		});

		playerButtons[4].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 4;
			}
		});

		playerButtons[5].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 5;
			}
		});

		playerButtons[6].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 6;
			}
		});
		
		playerButtons[7].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 7;
			}
		});

		playerButtons[8].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 8;
			}
		});

		playerButtons[9].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 9;
			}
		});

		playerButtons[10].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedId = 10;
			}
		});
	}
}
