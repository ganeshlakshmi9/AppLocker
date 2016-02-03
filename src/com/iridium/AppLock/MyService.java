/**
 * 
 */
package com.iridium.AppLock;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.iridium.AppLock.DBUttiles.DBUttiles;

/**
 * @author rajapeela
 * 
 */
public class MyService extends Service {

	private Thread SendDataThread;
	private String pkgNme = "";
	public static ArrayList<String> tempUnlock = new ArrayList<String>();
	private ArrayList<String> mulApps = new ArrayList<String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		SendDataThread = new Thread(null, backgrounSendData, "checkdata");
		SendDataThread.start();

	}

	private Runnable backgrounSendData = new Runnable() {

		public void run() {
			while (true) {
				try {
					
						lockApps();
						Thread.sleep(500);
						
					
				} catch (Exception exception) {
					exception.printStackTrace();
					Log.e("Exception", "Cont gEt Thread");
				}
			}
			

		}
	};

	// protected void startStayAlertActivity() {
	// getProcessDetails(getApplicationContext(), null);
	// }

	protected void lockApps() {
		
		// TODO Auto-generated method stub
		ActivityManager am = (ActivityManager) getApplicationContext()
				.getSystemService(ACTIVITY_SERVICE);

		String packageName = am.getRunningTasks(1).get(0).topActivity
				.getPackageName();
		pkgNme = packageName;
		
		String className = am.getRunningTasks(1).get(0).topActivity.getClassName();
		String[] lockedApps = DBUttiles.getDBUttile(getApplicationContext())
				.getLockedApps();
		
		
			for (int i = 0; i < lockedApps.length; i++) {
				//Log.e(UI_MODE_SERVICE, packageName+" receiver--");
				if (lockedApps[i].toString().trim().equals(packageName) && !tempUnlock.contains(packageName)) {
					//Log.e(UI_MODE_SERVICE, "matched receiver-- "+packageName);
					//Toast.makeText(getApplicationContext(), "Activity Triggrd true"+packageName,3000).show();
					//System.out.println("Locked Apps---->"+packageName+"       "+NewActivity.packageName);
					//System.out.println("Locked Apps---->"+packageName+"       "+NewActivity.packageName);
					//if(!NewActivity.unlocked){
						Intent notifyIntent = new Intent(getApplicationContext(),
								NewActivity.class); //
		
						notifyIntent
								.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK); //
		
						notifyIntent
								.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP); //
						
						notifyIntent
		                .setPackage(packageName);					
		
						getApplicationContext().startActivity(notifyIntent);
						//NewActivity.unlocked = true;
					//}
				}else {
					Log.e("else", "Else----------");
					for(String bkgApp : tempUnlock){
						if(bkgApp.equals(am.getRunningTasks(1).get(0).topActivity
				.getPackageName())){
							/*Intent notifyIntent = new Intent(getApplicationContext(),
									NewActivity.class); //
			
							notifyIntent
									.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK); //
			
							notifyIntent
									.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP); //
							
							notifyIntent
			                .setPackage(packageName);					
			
							getApplicationContext().startActivity(notifyIntent);*/	
							//Log.e("launch","manual");
							mulApps.add(bkgApp);
							
						}else{
							try {
								Thread.sleep(1000);
								for(String str : mulApps){
									if(tempUnlock.contains(str))
									tempUnlock.remove(str);
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
				}
			}
		
	}

	/**
	 * 
	 * @return
	 */
	/*
	 * public String[][] getProcessDetails(Context context, String[]
	 * LockPackages) { final PackageManager pm = context.getPackageManager(); //
	 * get a list // installed apps. int i = 0;
	 * 
	 * List<ApplicationInfo> packages = pm
	 * .getInstalledApplications(PackageManager.GET_META_DATA);
	 * 
	 * String solution[][] = new String[packages.size()][4]; for
	 * (ApplicationInfo packageInfo : packages) {
	 * 
	 * // if (TrafficStats.getUidTxBytes(packageInfo.uid) != -1) {
	 * solution[i][0] = pm.getApplicationLabel(packageInfo).toString(); //
	 * solution[i][1] = TrafficStats.getUidTxBytes(packageInfo.uid) + // "";
	 * solution[i][2] = packageInfo.name; solution[i][3] = packageInfo.icon +
	 * ""; } ActivityManager am = (ActivityManager) getApplicationContext()
	 * .getSystemService(ACTIVITY_SERVICE);
	 * 
	 * String packageName = am.getRunningTasks(1).get(0).topActivity
	 * .getPackageName(); String className =
	 * am.getRunningTasks(1).get(0).topActivity .getClassName(); //
	 * android.os.Process.killProcess(packageInfo.uid); // com.iridium.AppLock
	 * // com.sec.android.app.twlauncher // com.sec.android.app.twlauncher for
	 * (int j = 0; j < LockPackages.length; i++) { if
	 * (packageName.equals(LockPackages[j])) {
	 * 
	 * // am.killBackgroundProcesses(packageName);
	 * 
	 * Intent notifyIntent = new Intent(context, NewActivity.class); //
	 * 
	 * notifyIntent .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK); //
	 * 
	 * notifyIntent .addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 * //
	 * 
	 * context.startActivity(notifyIntent);
	 * 
	 * } else {
	 * 
	 * am.restartPackage(packageName); List<ApplicationInfo> packages1 = pm
	 * .getInstalledApplications(PackageManager.GET_META_DATA); for
	 * (ApplicationInfo packageInfo : packages1) { if
	 * (packageInfo.packageName.equals(packageName)) {
	 * android.os.Process.killProcess(packageInfo.uid); Log.e("", ""); } } } //
	 * android
	 * .os.Process.killProcess(android.os.Process.myPid()).UIHelper.killApp
	 * (true);
	 * 
	 * // List<RunningAppProcessInfo> runningPro = am //
	 * .getRunningAppProcesses(); // // for (RunningAppProcessInfo runAppInfo :
	 * runningPro) { // android.os.Process.killProcess(runAppInfo.pid); // }
	 * 
	 * Log.e("", "");
	 * 
	 * } // "Kiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"); // // i++; // } // }
	 * 
	 * // return solution; return null; } }
	 */
}