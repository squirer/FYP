package com.example.fyp_footballmanager;

import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Squad extends Activity {
	
	ImageButton[] playerButtons; 
	PlayerCoordinate[] allPositions;

	RelativeLayout squadLayout;
	float h, w;
	int clickedButton;

	ImageButton goalk,rightb,centreb,centreb2,leftb,rightm,centrem,centrem2,leftm,rightf,leftf;
	String[] tags = {"gk", "rb", "cb", "cb2", "lb", "rm", "cm", "cm2", "lm", "rf", "lf"};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		//File file = new File(this.getFilesDir(), "squadFormation");
/*		try {
			 reader = new BufferedReader(
			        new InputStreamReader(getAssets().open("squadformation.txt")));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		init();	
		setDefaultSquad();
		addPlayersToScreen();
		updateReferencesToButtons();

		setOnClickListenersForAllPlayers();

		squadLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					if(clickedButton != 0) {
						/*	float oldX, oldY, newX,newY;
						oldX = playerButtons[clickedButton].getX();
						oldY = playerButtons[clickedButton].getY();
						newX = event.getX();
						newY = event.getY();
						animatePlayerMovement(playerButtons[clickedButton], oldX,oldY,newX,newY);
						 */
						playerButtons[clickedButton].setX(event.getX() - (playerButtons[clickedButton].getWidth()/2));
						playerButtons[clickedButton].setY(event.getY() - (playerButtons[clickedButton].getHeight()/2));
						setColoursOriginal();
						clickedButton = 0;
					}
				}
				return true;
			}
		});

		setContentView(squadLayout);
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
		ImageButton[] tempButtons = {goalk,rightb,centreb,centreb2,leftb,rightm,centrem,centrem2,leftm,rightf,leftf};
		playerButtons = tempButtons;
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
		
		for(int i=0; i<allPositions.length; i++) {
			
		}
	}



	public void updateReferencesToButtons() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i] = (ImageButton) squadLayout.findViewWithTag(tags[i]);
		}
	}



	/*
	public void animatePlayerMovement(ImageButton player, float oldx, float oldy, float newx, float newY) {

		double addValue = 0.001;
		if(oldx > newx) {
			addValue *= -1;
		}
		while(oldx != newx) {
			player.setX(oldx);
			oldx += addValue;
			setContentView(squadLayout);
		}
	}
	 */

	/*Collects current players Positions and creates imageButtons for every player
	 * Adds them to screen with Image states*/
	public void addPlayersToScreen() {

		for(int i=0; i<allPositions.length; i++) {
			PlayerCoordinate p = allPositions[i];
			ImageButton player = new ImageButton(this);

			/*
			StateListDrawable states = new StateListDrawable();
			states.addState(new int[] {android.R.attr.state_pressed},
					getResources().getDrawable(R.drawable.playericon3));
			states.addState(new int[] {},
					getResources().getDrawable(R.drawable.playericon2));

			player.setImageDrawable(states);
			 */
			player.setBackgroundResource(R.drawable.playericon2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			player.setTag(tags[i]);
			int imageWidth = (int)w/20;
			int imageHeight = (int)h/30;
			player.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageHeight));
			squadLayout.addView(player);
		}
		setContentView(squadLayout);
	}


	public void setColoursOriginal() {
		for(int i=0; i<playerButtons.length; i++) {
			playerButtons[i].setBackgroundResource(R.drawable.playericon2);
		}
	}

	public void setOnClickListenersForAllPlayers() {
		playerButtons[1].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=1;
				setColoursOriginal();
				playerButtons[1].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[2].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=2;
				setColoursOriginal();
				playerButtons[2].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[3].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=3;
				setColoursOriginal();
				playerButtons[3].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[4].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=4;
				setColoursOriginal();
				playerButtons[4].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[5].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=5;
				setColoursOriginal();
				playerButtons[5].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[6].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=6;
				setColoursOriginal();
				playerButtons[6].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[7].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=7;
				setColoursOriginal();
				playerButtons[7].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[8].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=8;
				setColoursOriginal();
				playerButtons[8].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[9].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=9;
				setColoursOriginal();
				playerButtons[9].setBackgroundResource(R.drawable.playericon3);
			}
		});

		playerButtons[10].setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v)
			{
				clickedButton=10;
				setColoursOriginal();
				playerButtons[10].setBackgroundResource(R.drawable.playericon3);
			}
		});
	}
}
