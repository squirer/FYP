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
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Tactics extends Activity implements Runnable {

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

	Button[] playerButtons = new Button[11];
	String[] tags = {"GK", "RB", "CB", "CB2", "LB", "RM", "CM", "CM2", "LM", "RS", "LS"};
	PlayerCoordinate[] playerPositions;
	String savedFormation="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		setOnClickListenersForAllPlayers();

		squadLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (clickedId != 0 && event.getAction() == MotionEvent.ACTION_DOWN){

					arrowX = event.getX() - distortionX;
					arrowY = event.getY() - distortionY;
					playX = playerButtons[clickedId].getX();
					playY = playerButtons[clickedId].getY();
					drawTheArrow(playX, playY);

					TranslateAnimation animation = new TranslateAnimation(0, arrowX-playX-((w/17)/2), 0, arrowY -playY);
					animation.setDuration(2000); 
					animation.setRepeatCount(3);
					playerButtons[clickedId].startAnimation(animation);
					playerButtons[clickedId].setBackgroundResource(R.drawable.orb2);
					clickedId = 0;

					return true;
				}
				return false;
			}
		});
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/*Calls helper method to initialise this screen*/
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

	/*updates the references to Buttons finding them based on their tags previously set*/
	public void setReferencesToButtons() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i] = (Button) squadLayout.findViewWithTag(tags[i]);
			playerButtons[i].setId(i);
		}
	}

	/*Takes as String of contents of File and walks through collecting coordinates of players*/
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


	/*Reads information from the squad formation text file and returns String of contents*/
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


	/*using right angle triangle trigonometry this calculates the angle
	 * required to turn the arrow using inverse sine methodology */
	public void rotateArrowToDirection() {
		rotateAngle = 0;
		float opposite = Math.abs(arrowX - playX);
		float adjacent = Math.abs(arrowY - playY);
		float hypotenuse = (float) Math.sqrt((opposite * opposite) + (adjacent * adjacent));
		rotateAngle = (float) Math.asin(opposite/hypotenuse);
		calculateCorrectQuadrantAndAngle(); 
	}

	/*This calls the 2 methods required to convert radians to degrees and
	 * to add these to required degrees of given quadrant*/
	public void calculateCorrectQuadrantAndAngle() {
		convertRadiansToDegrees();
		setQuadrant();
	}

	/*simple calculation to convert radians to degrees*/
	public void convertRadiansToDegrees() {
		rotateAngle = (float) ((rotateAngle * 180) /3.14);
	}

	/*tests to see where the second click occurs and adds appropriate degrees*/
	public void setQuadrant() {
		if((arrowY <= playY) && (arrowX <= playX)) {
			rotateAngle = 90 + (90 - rotateAngle);
		}
		if(arrowX > playX && arrowY <= playY) {
			rotateAngle = 180 + rotateAngle;
		}
		if(arrowX > playX && arrowY > playY) {
			rotateAngle *= -1;
		}
	}

	/*Draws the arrow to the screen and decodes and rotates the arrow image accordingly*/
	public void drawTheArrow(float playX, float playY) {
		int imageWidth = (int)w/17;
		int imageHeight = (int)h/27;
		ImageButton arrow = new ImageButton(this);
		arrow.setX(arrowX);
		arrow.setY(arrowY);

		// this decodes the arrow tactic image and applies
		// a matrix to rotate the arrow at given angle
		Bitmap bMap = BitmapFactory.decodeResource(getResources(),R.drawable.arrowtactic);
		Matrix mat = new Matrix();
		rotateArrowToDirection();
		mat.postRotate(rotateAngle);
		Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
				bMap.getWidth(), bMap.getHeight(), mat, false);

		arrow.setImageBitmap(bMapRotate);
		// set alpha 45 removes transparency border of arrow
		arrow.getBackground().setAlpha(45);
		arrow.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
		squadLayout.addView(arrow);
		setContentView(squadLayout);
	}


	/*Adds Buttons to the screen to represent each player with a drawable image*/
	public void showSquad() {
		for(int i=0; i<playerPositions.length; i++) {
			PlayerCoordinate p = playerPositions[i];
			Button player = new Button(this);
			player.setBackgroundResource(R.drawable.orb2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			player.setText(tags[i]);
			player.setTag(tags[i]);
			player.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20); 
			int imageWidth = (int) ((int)w/9.2);
			int imageHeight = (int)h/20;
			player.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(player);
			setContentView(squadLayout);
		}
		setReferencesToButtons();
	}


	/*sets every player to its original colour to "de-select" them as such*/
	public void setColoursOriginal() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i].setBackgroundResource(R.drawable.orb2);
		}
	}


	/*This class describes a new OnTouchListener for our players*/
	public class playerTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			clickedId = v.getId();
			setColoursOriginal();
			playerButtons[clickedId].setBackgroundResource(R.drawable.orb);
			return true;
		}

	}

	/*sets all clickListeners for all squad and save andd reset buttons*/
	public void setOnClickListenersForAllPlayers() {
		for(int i=1; i<playerButtons.length; i++) {
			playerButtons[i].setOnTouchListener(new playerTouchListener());
		}
	}
}
