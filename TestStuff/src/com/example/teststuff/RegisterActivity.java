package com.example.teststuff;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {

	private String username;
	private String password;
	private String email;
	private MySQLiteHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		final Context context = this;
		db = new MySQLiteHelper(this);
		Button registerUser = (Button) findViewById(R.id.regiterUserButton);
		registerUser.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				username = ((EditText) findViewById(R.id.regiterUsernameText)).getText().toString();
				password = ((EditText) findViewById(R.id.registerPasswordText)).getText().toString();
				email = ((EditText) findViewById(R.id.registerEmailText)).getText().toString();
				registerCustomer(new Customer(username, password, email));
			}
		});
		
		Button getUsers = (Button) findViewById(R.id.getUsers);
		getUsers.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				List<Customer> customerList = getCustomerList();
				for(int i = 0; i < customerList.size(); i++)
					((EditText) findViewById(R.id.editText1)).setText(((EditText) findViewById(R.id.editText1)).getText().toString() 
							+ "\n" + customerList.get(i).getUsername() + " : " + customerList.get(i).getPassword());
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	private void registerCustomer(Customer newCustomer){
		db.addCustomer(newCustomer);
		
	}
	
	private List<Customer> getCustomerList(){
		return db.getAllCustomers();
	}
}
