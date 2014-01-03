package com.example.fyp_footballmanager;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingTheArrow extends View{

	Bitmap tactic;
	Paint blue = new Paint();
	float x,y;
	public DrawingTheArrow(Context context) {
		super(context);
		// the arrow_down_float here should be later changed to an image with squad
		// and pitch background 
		tactic = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_down_float);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.FILL);
		canvas.save();
		canvas.drawBitmap(tactic, x, y, blue);
		canvas.restore();
		invalidate();
	}



	/*Detects x and y position of touch on screen. Updates these values for arrow position*/
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = event.getX();
		y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		}
		return false;
	}
}



/*CODE NOTES*/
// THIS CANVAS HERE NEEDS TO BE DRAWING THE ORIGINAL SCREEN WITH PLAYERS

/*
	Rect r = new Rect();
	r.set(0,0, canvas.getWidth(), canvas.getHeight()/2);
	if(x < canvas.getWidth()) {
		x+= 10;
	}
	else x = 0;
	if(y < canvas.getHeight()) {
		y+=10;
	}
	else {
		y = 0;
	}
	canvas.drawRect(r, blue);
*/
