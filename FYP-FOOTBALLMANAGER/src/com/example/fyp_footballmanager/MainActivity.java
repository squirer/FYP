package com.example.fyp_footballmanager;

//import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button squad;
	Button fixtures;
	Button tactics;
	Button about;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		squad = (Button) findViewById(R.id.Squad);
		fixtures = (Button) findViewById(R.id.Fixtures);
		tactics = (Button) findViewById(R.id.Tactics);
		about = (Button) findViewById(R.id.About);
		
		//MediaPlayer music = MediaPlayer.create(MainActivity.this,R.raw.lose);
		//music.start();
		
		
		squad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Class<?> ourClass = Class.forName("com.example.fyp_footballmanager." + "Squad" );
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					startActivity(ourIntent);
				}
				catch(ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		});
		
		
		fixtures.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Class<?> ourClass = Class.forName("com.example.fyp_footballmanager." + "Fixtures" );
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					startActivity(ourIntent);
				}
				catch(ClassNotFoundException e){
					e.printStackTrace();
				}

			}
		});
		
		
		tactics.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Class<?> ourClass = Class.forName("com.example.fyp_footballmanager." + "Tactics" );
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					startActivity(ourIntent);
				}
				catch(ClassNotFoundException e){
					e.printStackTrace();
				}

			}
		});
		
		
		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*	try{
					Class<?> ourClass = Class.forName("com.example.fyp_footballmanager." + "About" );
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					startActivity(ourIntent);
				}
				catch(ClassNotFoundException e){
					e.printStackTrace();
				}
				*/
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
