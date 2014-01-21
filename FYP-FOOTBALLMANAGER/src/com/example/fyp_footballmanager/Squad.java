package com.example.fyp_footballmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Squad extends Activity {

	ImageButton[] playerButtons = new ImageButton[11]; 
	PlayerCoordinate[] allPositions = new PlayerCoordinate[11];
	RelativeLayout squadLayout;
	float h, w;
	int clickedButton;
	private int _xDelta;
	private int _yDelta;
	String[] tags = {"gk", "rb", "cb", "cb2", "lb", "rm", "cm", "cm2", "lm", "rf", "lf"};
	String contents;
	Button reset;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		init();		
	}

	
	/*Initialises helper variables and sets Content View*/
	public void init() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		h = displaymetrics.heightPixels;
		w = displaymetrics.widthPixels;
		LayoutInflater mInflater = LayoutInflater.from(this);  
		View contentView = mInflater.inflate(R.layout.activity_squad, null); 
		squadLayout = (RelativeLayout) contentView.findViewById(R.id.fullSquadLayoutActivitySquad);
		setContentView(squadLayout);
		reset = (Button) squadLayout.findViewById(R.id.reset);	
/*IDEALLY HERE SHOULD HAVE THE FILE CHECK TO SEE IF THERE IS A CURRENT DEFAULT SQUAD SET*/
		
		contents = readFromFile();
		if(contents.equals("") || contents.equals(null)) {
			setDefaultSquad();
			contents = readFromFile();
		}
		copyCoordinatesFromFile();
		addPlayersToScreen();
		updateReferencesToButtons();
		setOnClickListenersForAllPlayers();
	}
	

	public void resetSquadToDefault() {
/*		String result = "" + (w/2.2) + "," + (h/10* 7.3) + " " +
							 (w/10)*7.6 + "," + (h/10)*5.5 + " " +
							 (w/10)*5.6 + "," + (h/10)*6 + " " +
							 (w/10)*3.6 + "," + (h/10)*6 + " " +
							 (w/10)*1.6 + "," + (h/10)*5.5 + " " +
							 (w/10)*7.6 + "," + (h/10)*3.5 + " " +
							 (w/10)*5.6 + "," + (h/10) * 4 + " " +
							 (w/10)*3.6 + "," + (h/10) * 4 + " " +
							 (w/10)*1.6 + "," + (h/10) * 3.5 + " " +
							 (w/10)*5.6 + "," + (h/10)*1.8 + " " +
							 (w/10)*3.6 + "," + (h/10) *1.8 + " ";
		writeToFile(result);
		contents = readFromFile();
		copyCoordinatesFromFile();
*/		
		setDefaultSquad();
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i].setX((float) allPositions[i].xPos);
			playerButtons[i].setY((float) allPositions[i].yPos);
		}
		setContentView(squadLayout);
	}

	
	
	/*reads coordinates from file and updates the playerPositions array*/
	public void copyCoordinatesFromFile() {
		contents = readFromFile();
		String[] allCoordinates = contents.split(",");
		String[] individualCoordinates;
		String x;
		String y;
		PlayerCoordinate[] playerPositions = new PlayerCoordinate[11];
		for(int i=0; i<allCoordinates.length; i++) {
			individualCoordinates = allCoordinates[i].split(" ");
			x = individualCoordinates[0];
			y = individualCoordinates[1];
			PlayerCoordinate p = 
					new PlayerCoordinate(Double.parseDouble(x), Double.parseDouble(y));
			playerPositions[i] = p;
		}
		allPositions = playerPositions;
	}



	/*This collects current location of squad and saves to the file*/
	public void saveFormationToFile() {
		String newFormation="";
		for(int i=0; i<playerButtons.length; i++) {
			newFormation += playerButtons[i].getX() + " " + playerButtons[i].getY() + ",";
		}
		writeToFile(newFormation);
	}


	/*Writes new squad information to a file - passes data to be written in*/
	private void writeToFile(String data) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("squad.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		}
		catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		} 
	}


	/*reads from the squadFile*/
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
			ImageButton ERROR = new ImageButton(this);
			ERROR.setBackgroundResource(R.drawable.arrowtactic);
			ERROR.setX((float) w/2);
			ERROR.setY((float) h - 15);
			int imageWidth = (int)w/20;
			int imageHeight = (int)h/30;
			ERROR.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(ERROR);
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
			ImageButton ERROR = new ImageButton(this);
			ERROR.setBackgroundResource(R.drawable.arrowtactic);
			ERROR.setX((float) w/2);
			ERROR.setY((float) h - 15);
			int imageWidth = (int)w/20;
			int imageHeight = (int)h/30;
			ERROR.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(ERROR);
		}

		return ret;
	}

	
	



	/*This is the DEFAULT SQUAD setup if a default is not chosen*/
	public void setDefaultSquad() {

		PlayerCoordinate gk = new PlayerCoordinate((w/2.2),(h/10)* 7.3);
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

		PlayerCoordinate[] playerPositions = {gk,rb,cb,cb2,lb,rm,cm,cm2,lm,rf,lf};
		allPositions = playerPositions;

		String squadData = "";
		for(int i=0; i<allPositions.length; i++) {
			squadData += allPositions[i].xPos + " " + allPositions[i].yPos + ",";
		}
		
		writeToFile(squadData);
	}


	/*This ensures buttons have a valid reference which is up to date*/
	public void updateReferencesToButtons() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i] = (ImageButton) squadLayout.findViewWithTag(tags[i]);
		}
	}


	/*Collects current players Positions and creates imageButtons for every player
	 * Adds them to screen with Image states*/
	public void addPlayersToScreen() {

		for(int i=0; i<allPositions.length; i++) {
			PlayerCoordinate p = allPositions[i];
			ImageButton player = new ImageButton(this);
			player.setBackgroundResource(R.drawable.playericon2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			player.setTag(tags[i]);
			int imageWidth = (int)w/20;
			int imageHeight = (int)h/30;
			player.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(player);
			//playerButtons[i] = player;
		}
		updateReferencesToButtons();
		setContentView(squadLayout);
	}


	/*sets colours back to their original so as nothing seems selected from the squad*/
	public void setColoursOriginal() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i].setBackgroundResource(R.drawable.playericon2);
		}
	}


	public void setOnClickListenersForAllPlayers() {

		playerButtons[1].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[1].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[1].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[1].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[1].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[1].setLayoutParams(layoutParams);
					playerButtons[1].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[2].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[2].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[2].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[2].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[2].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[2].setLayoutParams(layoutParams);
					playerButtons[2].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[3].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[3].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[3].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[3].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[3].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[3].setLayoutParams(layoutParams);
					playerButtons[3].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[4].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[4].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[4].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[4].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[4].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[4].setLayoutParams(layoutParams);
					playerButtons[4].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[5].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[5].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[5].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[5].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[5].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[5].setLayoutParams(layoutParams);
					playerButtons[5].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[6].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[6].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[6].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[6].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[6].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[6].setLayoutParams(layoutParams);
					playerButtons[6].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[7].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[7].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[7].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[7].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[7].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[7].setLayoutParams(layoutParams);
					playerButtons[7].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[8].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[8].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[8].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[8].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[8].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[8].setLayoutParams(layoutParams);
					playerButtons[8].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[9].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[9].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[9].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[9].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[9].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[9].setLayoutParams(layoutParams);
					playerButtons[9].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});

		playerButtons[10].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				final int X = (int) event.getRawX();
				final int Y = (int) event.getRawY();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					_xDelta = X - lParams.leftMargin;
					_yDelta = Y - lParams.topMargin;
					playerButtons[10].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_UP:
					playerButtons[10].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					playerButtons[10].setBackgroundResource(R.drawable.playericon3);
					break;
				case MotionEvent.ACTION_POINTER_UP:	
					playerButtons[10].setBackgroundResource(R.drawable.playericon2);
					updateReferencesToButtons();
					saveFormationToFile();
					break;
				case MotionEvent.ACTION_MOVE:
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
					layoutParams.leftMargin = X - _xDelta;
					layoutParams.topMargin = Y - _yDelta;
					layoutParams.rightMargin = -250;
					layoutParams.bottomMargin = -250;
					playerButtons[10].setLayoutParams(layoutParams);
					playerButtons[10].setBackgroundResource(R.drawable.playericon3);
					break;
				}
				return true;
			}});
		
		
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resetSquadToDefault();
			}
		});
	}
}
