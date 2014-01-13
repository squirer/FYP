package com.example.fyp_footballmanager;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Squad extends Activity {

	ImageButton[] playerButtons = new ImageButton[11]; 
	PlayerCoordinate[] allPositions;
	RelativeLayout squadLayout;
	float h, w;
	int status;

	ImageButton rm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		init();
		
		setDefaultSquad();
		rm = (ImageButton) squadLayout.findViewWithTag("rm");

		rm.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					status = 1;
					//			gk.setX(gk.getX() + 20);
					//			gk.setY(gk.getY() + 20);

				}
				return true;
			}
		});


		squadLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					if(status == 1) {
						rm.setX(event.getX() - (rm.getWidth()/2));
						rm.setY(event.getY() - (rm.getHeight()/2));
						status = 0;
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

		String[] tags = {"gk", "rb", "cb", "cb2", "lb", "rm", "cm", "cm2", "lm", "rf", "lf"};

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
			playerButtons[i] = player;
			//	fileWriter.write(p.xPos + "," + p.yPos);
		}

		//fileWriter.close();

		/*
		Bundle b=new Bundle();
		b.putFloatArray("xs",new float[]{5,4,3,2,1});
		Class<?> ourClass;
		try {
			ourClass = Class.forName("com.example.fyp_footballmanager." + "Tactics" );
			Intent i=new Intent(Squad.this, ourClass);
			i.putExtras(b);
		//	startActivity(i);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 */	
		setContentView(squadLayout);
	}

}
