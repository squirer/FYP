package com.example.fyp_footballmanager;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		h = displaymetrics.heightPixels;
		w = displaymetrics.widthPixels;
		distortionX = (float) (h/59.2);
		distortionY = (float) (w/36);

		LayoutInflater mInflater = LayoutInflater.from(this);  
		View contentView = mInflater.inflate(R.layout.activity_tactics, null); 
		squadLayout = (RelativeLayout) contentView.findViewById(R.id.fullSquadLayout);

/*
		Bundle b=this.getIntent().getExtras();
		if(b != null) {
			float[] array = b.getFloatArray("xs");
			Log.e("contains", array.toString());
		}
*/
/*		float[] array=b.getFloatArray("xs");
		if(array[0] == 5) {
			String result = "{";
			for(int i=0; i<array.length; i++) {
				result += array[i];
			}
			result+="}";
			Log.e("array", result);
		}
*/

		setContentView(squadLayout);

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
			player.setBackgroundResource(R.drawable.playericon2);
			player.setX((float) p.xPos);
			player.setY((float) p.yPos);
			squadLayout.addView(player);
			setContentView(squadLayout);
		}
	}

}
