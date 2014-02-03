package com.example.teststuff;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {

	private String username;
	private String password;
	private MySQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        db = new MySQLiteHelper(context);
        Button submitButton = (Button) findViewById(R.id.regiterUserButton);
        submitButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		username = ((EditText)findViewById(R.id.regiterUsernameText)).getText().toString();
        		password = ((EditText)findViewById(R.id.registerPasswordText)).getText().toString();
        		if(tryLogin(username, password)){
        			((TextView) findViewById(R.id.textView2)).setText(""); 
        			Intent intent = new Intent(context, LoginSuccess.class);
            		startActivity(intent);
            	}else
            		((TextView) findViewById(R.id.textView2)).setText("Invalid Password!");
        		}
        });
        Button registerButton = (Button) findViewById(R.id.button2);
        registerButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(context, RegisterActivity.class/*change to register page*/);
        		db.close();
        		startActivity(intent);
        	}        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private boolean tryLogin(String username, String password){
    	Customer customer = db.getCustomer(username);
    	if(customer != null){
	    	if(customer.getUsername().toString().compareTo(username) == 0 
	    			&& customer.getPassword().toString().compareTo(password) == 0)
	    		return true;
    	}
    	return false;
    }
}
