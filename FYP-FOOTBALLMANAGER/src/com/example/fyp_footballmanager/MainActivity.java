package com.example.fyp_footballmanager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button squad;
	Button tactics;
	Button fixtures;
	Button about;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		squad = (Button) findViewById(R.id.Squad);
		tactics = (Button) findViewById(R.id.Tactics);
		fixtures = (Button)findViewById(R.id.Fixtures);
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
				try{
					Class<?> ourClass = Class.forName("com.example.fyp_footballmanager." + "About" );
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
				Calendar cal = new GregorianCalendar(); 
				cal.setTime(new Date()); 
				cal.add(Calendar.MONTH, 0); 
				long time = cal.getTime().getTime(); 
				Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon(); 
				builder.appendPath("time"); 
				builder.appendPath(Long.toString(time)); 
				Intent intent = new Intent(Intent.ACTION_VIEW, builder.build()); 
				startActivity(intent); 
				Toast.makeText(getApplicationContext(),
						"Simply choose a day and hit the + to add a match event!" +
						"",Toast.LENGTH_LONG).show();
			}
		});
	}
}
