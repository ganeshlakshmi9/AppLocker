/**
 * 
 */
package com.iridium.AppLock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author rajapeela
 * 
 */
public class BootStartReciver extends BroadcastReceiver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, MyService.class));
	}

}
