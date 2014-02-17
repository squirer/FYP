package com.example.fyp_footballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class Login extends Activity {
	final String usernameTest = "Robert";
	final String passwordTest = "1234";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void Sign_up_page(View view)
	{
	}
	
	public void Logging_in(View view)
	{	
		/* check log-in details */		
		EditText username_field = (EditText) findViewById(R.id.editusername);
		EditText password_field= (EditText) findViewById(R.id.editpassword);
		
		String username = username_field.getText().toString();
		String password = password_field.getText().toString();
	
		/*
		if(username != null && password != null)
		{			
			// attempt to login 	
			if(username.equals(usernameTest) && password.equals(passwordTest)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);	
			}
		}
		*/
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);	
	}
}
