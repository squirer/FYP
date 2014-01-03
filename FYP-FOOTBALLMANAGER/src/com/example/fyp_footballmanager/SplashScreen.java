package com.example.fyp_footballmanager;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


public class SplashScreen extends Activity {

	MediaPlayer ourSong;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(7000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					//Intent main_screen = new Intent("com.example.fyp_footballmanager.MainActivity");
					//startActivity(main_screen);
					Intent open_login = new Intent("com.example.fyp_footballmanager.LOGIN");
					startActivity(open_login);
				}
			}
			
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}



