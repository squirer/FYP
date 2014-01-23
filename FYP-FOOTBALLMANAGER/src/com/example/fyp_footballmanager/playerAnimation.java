package com.example.fyp_footballmanager;

public class playerAnimation implements Runnable {

	float playerStartPosX;
	float playerStartPosY;
	float playerFinalPosX;
	float playerFinalPosY;
	
	public playerAnimation(float px, float py, float ax, float ay) {
		playerStartPosX = px;
		playerStartPosY = py;
		playerFinalPosX = ax;
		playerFinalPosY = ay;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			
		}
	}
}
