/**
 * 
 */
package com.iridium.AppLock.Uttile;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.iridium.AppLock.NewActivity;
import com.iridium.AppLock.DBUttiles.DBUttiles;

/**
 * @author rajapeela
 * 
 */
public class Uttile {

	public void setAllAppsInDB(Context context) {
		// DBUttiles.getDBUttile(context).AppLockDropTable(context);
		final PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo packageInfo : packages) {

			if (packageInfo.icon == 0) {

			} else {

				if (DBUttiles.getDBUttile(context).isAppAvailable(
						packageInfo.packageName)
						|| packageInfo.packageName
								.equals("com.iridium.AppLock")) {

				} else {
					DBUttiles.getDBUttile(context).insertApps(
							packageInfo.className, packageInfo.packageName,
							packageInfo.icon + "", "false", "false");
				}
			}
		}

	}

	public String[][] getProcessDetails(Context context, String[] LockPackages) {
		final PackageManager pm = context.getPackageManager(); // get a list
		// installed apps.
		int i = 0;

		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		String solution[][] = new String[packages.size()][4];
		for (ApplicationInfo packageInfo : packages) {

			// if (TrafficStats.getUidTxBytes(packageInfo.uid) != -1) {
			solution[i][0] = pm.getApplicationLabel(packageInfo).toString();
			// solution[i][1] = TrafficStats.getUidTxBytes(packageInfo.uid) +
			// "";
			solution[i][2] = packageInfo.name;
			solution[i][3] = packageInfo.icon + "";
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		String packageName = am.getRunningTasks(1).get(0).topActivity
				.getPackageName();
		String className = am.getRunningTasks(1).get(0).topActivity
				.getClassName();
		// android.os.Process.killProcess(packageInfo.uid);
		// com.iridium.AppLock
		// com.sec.android.app.twlauncher
		// com.sec.android.app.twlauncher
		for (int j = 0; j < LockPackages.length; i++) {
			if (packageName.equals(LockPackages[j])) {

				// am.killBackgroundProcesses(packageName);

				Intent notifyIntent = new Intent(context, NewActivity.class); //

				notifyIntent
						.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK); //

				notifyIntent
						.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP); //

				context.startActivity(notifyIntent);

			} else {

				am.restartPackage(packageName);
				List<ApplicationInfo> packages1 = pm
						.getInstalledApplications(PackageManager.GET_META_DATA);
				for (ApplicationInfo packageInfo : packages1) {
					if (packageInfo.packageName.equals(packageName)) {
						android.os.Process.killProcess(packageInfo.uid);
						Log.e("", "");
					}
				}
			}

			Log.e("", "");

		}
		return null;
	}

}
