package com.example.teststuff;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";
	

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_EMAIL = "email";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
				+ KEY_PASSWORD + " TEXT," + KEY_EMAIL + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addCustomer(Customer newCustomer) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, newCustomer.getUsername().toString()); // Customer username
		values.put(KEY_PASSWORD, newCustomer.getPassword().toString()); // Customer password
		values.put(KEY_EMAIL, newCustomer.getEmail().toString()); // Customer email

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Customer getCustomer(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL }, KEY_USERNAME + "=?",
				new String[] {username}, null, null, null, null);
		if (cursor != null){
			cursor.moveToFirst();
			Customer contact = new Customer(cursor.getString(1).toString(),
					cursor.getString(2).toString(), cursor.getString(3).toString());
			// return contact
			return contact;
		}
		return null;
	}
	
	boolean doesUsernameExist(String username){
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL }, KEY_USERNAME + "=?",
				new String[] {username}, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
			if(cursor.getString(1).toString().compareTo(username) == 0){
				cursor.close();
				return true;
			}else{
				cursor.close();
				return false;
			}
		}else
			return false;
		
		
	}
	
	// Getting All Contacts
	public List<Customer> getAllCustomers() {
		List<Customer> customerList = new ArrayList<Customer>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Customer contact = new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3));
				customerList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return customerList;
	}

	// Updating single contact
	public int updateContact(Customer contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, contact.getUsername());
		values.put(KEY_PASSWORD, contact.getPassword());
		values.put(KEY_EMAIL, contact.getEmail());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_USERNAME + " = ?",
				new String[] { contact.getUsername() });
	}

	// Deleting single contact
	public void deleteContact(Customer contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_USERNAME + " = ?",
				new String[] { String.valueOf(contact.getUsername()) });
		db.close();
	}


	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
