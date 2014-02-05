package com.example.fyp_footballmanager;

import java.io.BufferedReader;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Squad extends Activity {

	Button[] playerButtons = new Button[11];
	Button[] textualIds = new Button[11];
	
	PlayerCoordinate[] allPositions = new PlayerCoordinate[11];
	RelativeLayout squadLayout;
	float h, w;
	int clickedButton;
	private int _xDelta;
	private int _yDelta;
	String[] tags = {"GK", "RB", "CB", "CB2", "LB", "RM", "CM", "CM2", "LM", "RS", "LS"};
	String contents;
	Button reset, save;
	
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
		save = (Button) squadLayout.findViewById(R.id.save);
	
		contents = readFromFile();
		if(contents.equals("") || contents.equals(null)) {
			setDefaultSquad();
		}
		copyCoordinatesFromFile();
		addPlayersToScreen();
		updateReferencesToButtons();
		setOnClickListenersForAllPlayers();
	}
	


	/*calls setDefault() Method and returns screen to 4-4-2 formation*/
	public void resetSquadToDefault() {	
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


	/*reads from the squad text file of player positions*/
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
		} 
		catch (IOException e) {
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
			playerButtons[i] = (Button) squadLayout.findViewWithTag(tags[i]);
			playerButtons[i].setId(i);
		}
	}


	/*Collects current players Positions and creates imageButtons for every player
	 * Adds them to screen with Image states*/
	public void addPlayersToScreen() {

		for(int i=0; i<allPositions.length; i++) {
			
			PlayerCoordinate p = allPositions[i];
			Button player = new Button(this);
			player.setBackgroundResource(R.drawable.orb2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			player.setTag(tags[i]);
			int imageWidth = (int) ((int)w/9.2);
			int imageHeight = (int)h/20;
			player.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			player.setText(tags[i]);
			player.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20); 
			squadLayout.addView(player);
		}
		updateReferencesToButtons();
		setContentView(squadLayout);
	}


	/*sets colours back to their original so as nothing seems selected from the squad*/
	public void setColoursOriginal() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i].setBackgroundResource(R.drawable.orb2);
		}
	}

	
	
	/*moves a squad member based on which one has been selected by ID
	 * changes colour of squad member during dragging and release*/
	public class squadMoveListener implements OnTouchListener {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	        int id = v.getId();
	        final int X = (int) event.getRawX();
			final int Y = (int) event.getRawY();
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
				_xDelta = X - lParams.leftMargin;
				_yDelta = Y - lParams.topMargin;
				playerButtons[id].setBackgroundResource(R.drawable.orb);
				break;
			case MotionEvent.ACTION_UP:
				playerButtons[id].setBackgroundResource(R.drawable.orb2);
				updateReferencesToButtons();
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				playerButtons[id].setBackgroundResource(R.drawable.orb);
				break;
			case MotionEvent.ACTION_POINTER_UP:	
				playerButtons[id].setBackgroundResource(R.drawable.orb2);
				updateReferencesToButtons();
				break;
			case MotionEvent.ACTION_MOVE:
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
				layoutParams.leftMargin = X - _xDelta;
				layoutParams.topMargin = Y - _yDelta;
				layoutParams.rightMargin = -250;
				layoutParams.bottomMargin = -250;
				playerButtons[id].setLayoutParams(layoutParams);
				playerButtons[id].setBackgroundResource(R.drawable.orb);
				break;
			}
	        return true;
	    }

	}
	
	
	/*sets all clickListeners for all squad and save andd reset buttons*/
	public void setOnClickListenersForAllPlayers() {

		for(int i=1; i<playerButtons.length; i++) {
			playerButtons[i].setOnTouchListener(new squadMoveListener());
		}
		
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				resetSquadToDefault();
			}
		});
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveFormationToFile();
			}
		});
	}
}
