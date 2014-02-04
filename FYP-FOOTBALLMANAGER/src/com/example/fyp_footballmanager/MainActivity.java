package com.example.fyp_footballmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button squad;
	Button tactics;
	Button about;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		squad = (Button) findViewById(R.id.Squad);
		tactics = (Button) findViewById(R.id.Tactics);
		about = (Button) findViewById(R.id.About);
		
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
}
