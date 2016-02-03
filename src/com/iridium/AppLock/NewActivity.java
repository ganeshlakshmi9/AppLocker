package com.iridium.AppLock;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iridium.AppLock.DBUttiles.DBUttiles;

/**
 * @author rajapeela
 * 
 */
public class NewActivity extends Activity {

    private Button submitButton, cancelButton;
    String appName = "";
    private Cursor pawordCursor;
    private EditText passwordFeild;
    private ArrayList<String> mIdList;
    String pkgName = "";
    public static boolean unlocked  = false;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (mIdList == null) mIdList = new ArrayList<String>();
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.main);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.window_title);
        submitButton = (Button) findViewById(R.id.submitButton1);
        passwordFeild = (EditText) findViewById(R.id.passwordField1);
        //cancelButton = (Button) findViewById(R.id.cancleButton1);
        submitButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	pawordCursor = DBUttiles.getDBUttile(getApplicationContext())
        				.selectPASSWORD();
            	if (pawordCursor
						.getString(1)
						.toString()
						.equals(passwordFeild.getText().toString()
								.trim())){
            		Cursor appListData = DBUttiles.getDBUttile(getApplicationContext())
                            .selectAllLockApps();
                    String packageName = getIntent().getPackage();
                    pkgName = packageName;
                    ActivityManager am = (ActivityManager) getApplicationContext()
                    .getSystemService(ACTIVITY_SERVICE);
                    //String packageName = am.getRunningTasks(1).get(0).baseActivity
                   // .getPackageName();
                    //Toast.makeText(getApplicationContext(), "Activity Triggrd true"+packageName,3000).show();
                    int i = 0;
                    while (appListData.isAfterLast() == false) {
                        
                        if(packageName.equals(appListData.getString(2))){
                            //Toast.makeText(getApplicationContext(), packageName+"::Inside Cond::"+appListData.getString(2),3000).show();
                            appName = appListData.getString(1);                            
                            break;
                        }
                                
                        appListData.moveToNext();
                        i++;
                    }

                    appListData.close();
                    DBUttiles.getDBUttile(getApplicationContext()).updateFlage(
                            appName, packageName, "false");                
                    //Toast.makeText(getApplicationContext(), "class Name ", 3000).show();                
                    PackageManager  pmi = getPackageManager();
                    Intent intent = null;
    	            intent = pmi.getLaunchIntentForPackage(packageName);                    
    	            if (intent != null){
    	                //startActivityForResult(intent, 2);
    	            	MyService.tempUnlock.add(packageName);
    	            	
    	            	//Log.e("added", "temp lock");
    	            	startActivity(intent);
    	            	try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    	            	//unlocked = true;
    	            	DBUttiles.getDBUttile(getApplicationContext()).updateFlage(
                                appName, packageName, "true");
    	            }
    	            
                    //System.out.println("event called");          		
            	}else{
            		Toast.makeText(getApplicationContext(), "Enter Correct Password", 3000).show();
            	}
                
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Toast.makeText(getApplicationContext(), "Back", 3000).show();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		Toast.makeText(getApplicationContext(), "Paused", 3000).show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getApplicationContext(), "Resume", 3000).show();
	}

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		Toast.makeText(getApplicationContext(), "Activity result :"+resultCode+"  "+requestCode, 3000).show();
		if(requestCode==2){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBUttiles.getDBUttile(getApplicationContext()).updateFlage(
                    appName, pkgName, "true");
		}
		
	}*/

	
	
}