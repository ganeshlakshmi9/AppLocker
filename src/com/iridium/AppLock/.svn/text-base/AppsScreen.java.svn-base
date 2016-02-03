/**
 * 
 */
package com.iridium.AppLock;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iridium.AppLock.DBUttiles.DBUttiles;

/**
 * @author rajapeela
 * 
 */
public class AppsScreen extends Activity {

	private LinearLayout baseLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.appsscreen);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

		// this.getWindow().setSoftInputMode(
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Button doneButton = (Button) findViewById(R.id.button1);
		doneButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		baseLayout = (LinearLayout) findViewById(R.id.baseLayOut);

		Cursor appListData = DBUttiles.getDBUttile(getApplicationContext())
				.selectAllLockApps();

		int i = 0;

		while (appListData.isAfterLast() == false) {

			createListView(appListData.getString(1), appListData.getString(2),
					appListData.getString(3), appListData.getString(4),
					appListData.getString(5));
			appListData.moveToNext();
			i++;
		}

		appListData.close();
	}

	private void createListView(final String appname, final String packegename,
			String icon, String flage, String tempFlage) {
		RelativeLayout subLayOut = new RelativeLayout(getApplicationContext());
		subLayOut.setPadding(2, 1, 5, 0);

		Drawable bgImage = getApplicationContext().getResources().getDrawable(
				R.drawable.bg);
		subLayOut.setBackgroundDrawable(bgImage);

		RelativeLayout.LayoutParams icon_relativeParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		icon_relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
		icon_relativeParams.height = 35;
		icon_relativeParams.width = 35;

		ImageView iconImage = new ImageView(getApplicationContext());
		iconImage.setId(1);

		PackageManager pk = getPackageManager();

		Drawable d;
		try {
			d = pk.getApplicationIcon(packegename);
			iconImage.setImageDrawable(d);
			if (iconImage.getHeight() > 20) {

			}

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			iconImage.setImageResource(R.drawable.icon);

		}

		subLayOut.addView(iconImage, icon_relativeParams);
		TextView AppName = new TextView(getApplicationContext());
		AppName.setId(2);

		// if (appname == null || appname.equals("")) {
		try {
			AppName.setText("  "
					+ pk.getApplicationLabel(
							pk.getApplicationInfo(packegename,
									PackageManager.GET_META_DATA)).toString());
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			AppName.setText(packegename.toString());
		}

		// } else {
		// AppName.setText(appname);
		// }

		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

		relativeParams.addRule(RelativeLayout.RIGHT_OF, iconImage.getId());
		relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
		subLayOut.addView(AppName, relativeParams);

		CheckBox enablePass = new CheckBox(getApplicationContext());
		enablePass.setId(3);
		enablePass.setButtonDrawable(R.drawable.checkbox_selector);

		if (flage.toString().trim().equals("true"))
			enablePass.setChecked(true);
		else
			enablePass.setChecked(false);

		RelativeLayout.LayoutParams relativeParams2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// relativeParams2.addRule(RelativeLayout.RIGHT_OF, workArea.getId());
		relativeParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		relativeParams2.addRule(RelativeLayout.CENTER_VERTICAL);
		// enablePass.setPadding(0, 0, 20, 0);
		enablePass.setCompoundDrawablePadding(10);

		subLayOut.addView(enablePass, relativeParams2);

		subLayOut.setFadingEdgeLength(2);
		subLayOut.setVerticalFadingEdgeEnabled(true);

		TextView lineText = new TextView(getApplicationContext());
		lineText.setText("");
		lineText.setTextColor(Color.BLACK);
		lineText.setHeight(2);

		// lineText.setBackgroundDrawable(R.drawable.line);
		Drawable myImage = getApplicationContext().getResources().getDrawable(
				R.drawable.line);
		lineText.setBackgroundDrawable(myImage);
		lineText.layout(0, 3, 0, 3);

		subLayOut.setFadingEdgeLength(2);

		subLayOut.setVerticalFadingEdgeEnabled(true);

		RelativeLayout.LayoutParams relativeParams3 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

		relativeParams3.addRule(RelativeLayout.BELOW, iconImage.getId());
		relativeParams3.addRule(RelativeLayout.CENTER_VERTICAL);

		// subLayOut.addView(lineText,relativeParams3);

		enablePass.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					DBUttiles.getDBUttile(getApplicationContext()).updateFlage(
							appname, packegename, "true");

				} else {
					DBUttiles.getDBUttile(getApplicationContext()).updateFlage(
							appname, packegename, "false");
				}

			}
		});

		baseLayout.addView(subLayOut);

	}
}
