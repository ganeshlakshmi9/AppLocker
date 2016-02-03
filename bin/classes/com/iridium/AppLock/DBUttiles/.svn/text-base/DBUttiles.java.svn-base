package com.iridium.AppLock.DBUttiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

public class DBUttiles {
	private static DBUttiles dbUttiles;
	private static String DATABASE_NAME = "AppLock.db";
	private static int DATABASE_VERSION = 1;
	private static final String INSERT_LOCKAPPS = "insert into AppLock (appname, packegename,icon,flage,tempFlage) values (?,?,?,?,?)";
	private static final String INSERT_LOCK_PASSWORD = "insert into PASSWORD (Password) values (?)";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement applockStatement, passStatment;

	public static DBUttiles getDBUttile(Context context) {
		if (dbUttiles == null) {
			dbUttiles = new DBUttiles(context);
		}
		return dbUttiles;
	}

	private DBUttiles(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		try {
			OpenHelper openHelper = new OpenHelper(this.context);
			this.db = openHelper.getWritableDatabase();
			openHelper.onCreate(db);

			this.applockStatement = this.db.compileStatement(INSERT_LOCKAPPS);
			this.passStatment = this.db.compileStatement(INSERT_LOCK_PASSWORD);

			loadLists();
			loadPassWord();

		} catch (SQLiteException e) {
			Log.w("Example",
					"connecting helper database, this will create db, tables.");
			e.getMessage();
		}

	}

	public void loadLists() {
		// TODO Auto-generated method stub

		Cursor cursor = this.db.query("AppLock", null, null, null, null, null,
				null);
		if (cursor.moveToFirst()) {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		} else {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			Log.e("No data", "DB There is no data cursor is null");
		}
	}

	public void loadPassWord() {
		// TODO Auto-generated method stub

		Cursor cursor = this.db.query("PASSWORD", null, null, null, null, null,
				null);
		if (cursor.moveToFirst()) {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		} else {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			Log.e("No data", "DB There is no data cursor is null");
		}
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS AppLock(_id INTEGER PRIMARY KEY AUTOINCREMENT, appname TEXT, packegename TEXT,icon TEXT,flage TEXT,tempFlage TEXT)");
			db.execSQL("CREATE TABLE IF NOT EXISTS PASSWORD(_id INTEGER PRIMARY KEY AUTOINCREMENT,password TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS AppLock");
			db.execSQL("DROP TABLE IF EXISTS PASSWORD");
			onCreate(db);
		}
	}

	public boolean AppLockDropTable(Context context) {

		db.execSQL("drop table AppLock");

		db.execSQL("CREATE TABLE IF NOT EXISTS AppLock(_id INTEGER PRIMARY KEY AUTOINCREMENT, appname TEXT, packegename TEXT,icon TEXT,flage TEXT,tempFlage TEXT)");

		this.applockStatement = this.db.compileStatement(INSERT_LOCKAPPS);

		return true;
	}

	public boolean PassWordDropTable(Context context) {

		db.execSQL("drop table PASSWORD");

		db.execSQL("CREATE TABLE IF NOT EXISTS PASSWORD(_id INTEGER PRIMARY KEY AUTOINCREMENT,password TEXT)");

		this.passStatment = this.db.compileStatement(INSERT_LOCK_PASSWORD);

		return true;
	}

	public boolean isAppAvailable(String packagename) {
		Cursor cursor = this.db.query("AppLock", null, null, null, null, null,
				null);
		if (cursor == null)
			return false;
		else {
			cursor.moveToFirst();

			while (cursor.isAfterLast() == false) {
				if (cursor.getString(2).toString().trim().equals(packagename)) {
					cursor.close();
					return true;
				}
				cursor.moveToNext();
			}
			cursor.close();
			return false;
		}
	}

	public String[] getLockedApps() {
		Cursor cursor = this.db.query("AppLock", null, "flage='true'", null,
				null, null, null);
		if (cursor == null)
			return null;
		else {
			String[] packageNames = new String[cursor.getCount()];
			cursor.moveToFirst();
			int i = 0;
			while (cursor.isAfterLast() == false) {
				packageNames[i] = cursor.getString(
						cursor.getColumnIndex("packegename")).toString();
				i++;
				cursor.moveToNext();
			}
			cursor.close();
			return packageNames;
		}
	}

	public Cursor selectAllLockApps() {
		Cursor cursor = this.db.query("AppLock", null, null, null, null, null,
				null);
		if (cursor == null)
			return null;
		else {
			cursor.moveToFirst();
			return cursor;
		}
	}

	public Cursor selectPASSWORD() {
		Cursor cursor = this.db.query("PASSWORD", null, null, null, null, null,
				null);
		if (cursor == null)
			return null;
		else {
			cursor.moveToFirst();
			return cursor;
		}
	}

	public int updateFlage(String AppName, String packagename, String flage) {

		ContentValues cv = new ContentValues();

		cv.put("flage", flage);

		return this.db.update("AppLock", cv, "packegename='" + packagename
				+ "'", null);
	}

	public boolean insertPassword(String password, Context context) {

		if (this.passStatment == null) {

			Toast.makeText(this.context,
					"Some Problem With Store Data Statment", Toast.LENGTH_LONG)
					.show();

			return false;
		} else {

			PassWordDropTable(context);

			this.passStatment.clearBindings();

			if (password != null)
				this.passStatment.bindString(1, password);
		}
		if (this.passStatment.executeInsert() == -1) {

			return false;
		} else {
			return true;
		}

		// company,location,subloc,latitude,longitude,image1,fencingarea,datecreated,datemodified,deleted
	}

	public boolean insertApps(String AppName, String PakageName, String icon,
			String flag, String tempFlag) {

		if (this.applockStatement == null) {

			Toast.makeText(this.context,
					"Some Problem With Store Data Statment", Toast.LENGTH_LONG)
					.show();

			return false;
		} else {

			this.applockStatement.clearBindings();

			if (AppName != null)
				this.applockStatement.bindString(1, AppName);

			if (PakageName != null)
				this.applockStatement.bindString(2, PakageName);

			if (icon != null)
				this.applockStatement.bindString(3, icon);

			if (flag != null)
				this.applockStatement.bindString(4, flag);
			if (tempFlag != null)
				this.applockStatement.bindString(4, tempFlag);

		}
		if (this.applockStatement.executeInsert() == -1) {

			return false;
		} else {
			return true;
		}

		// company,location,subloc,latitude,longitude,image1,fencingarea,datecreated,datemodified,deleted
	}
}
