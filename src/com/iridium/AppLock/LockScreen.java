package com.iridium.AppLock;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iridium.AppLock.DBUttiles.DBUttiles;
import com.iridium.AppLock.Uttile.Uttile;

public class LockScreen extends Activity {
	private Button submitButton, cancelButton;
	private EditText passwordFeild, rePasswordFeild;
	private boolean fistTime = false;
	private TextView machPassField;
	private Cursor pawordCursor;

	// private ProgressDialog processDialog;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (pawordCursor != null) {
			pawordCursor.close();
		}

		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.lockscreen);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// processDialog = new ProgressDialog(LockScreen.this);

		submitButton = (Button) findViewById(R.id.submitButton);
		cancelButton = (Button) findViewById(R.id.cancleButton);
		passwordFeild = (EditText) findViewById(R.id.passwordField);
		rePasswordFeild = (EditText) findViewById(R.id.editText1);
		machPassField = (TextView) findViewById(R.id.passWordMatch);

		startService(new Intent(getApplicationContext(), MyService.class));

		new Uttile().setAllAppsInDB(getApplicationContext());

		pawordCursor = DBUttiles.getDBUttile(getApplicationContext())
				.selectPASSWORD();

		if (pawordCursor == null || pawordCursor.getCount() == 0) {
			fistTime = true;
		} else {
			fistTime = false;
			((RelativeLayout) findViewById(R.id.reenterFields))
					.setVisibility(View.GONE);

		}

		rePasswordFeild.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (passwordFeild.getText().toString().trim()
						.equals(rePasswordFeild.getText().toString().trim())) {
					machPassField.setText("Password match");
					machPassField.setTextColor(Color.GREEN);

				} else {
					machPassField.setText("Password not match");
					machPassField.setTextColor(Color.RED);
				}

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				if (passwordFeild.getText().toString().trim()
						.equals(rePasswordFeild.getText().toString().trim())) {
					machPassField.setText("Password match");
					machPassField.setTextColor(Color.parseColor("#4cd800"));

				} else {
					machPassField.setText("Password not match");
					machPassField.setTextColor(Color.RED);
				}

			}
		});

		submitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (passwordFeild.getText().toString().trim().equals("")) {

					Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_LONG).show();
				} else {
					// processDialog.show();
					if (fistTime) {
						DBUttiles.getDBUttile(getApplicationContext())
								.insertPassword(
										passwordFeild.getText().toString()
												.trim(),
										getApplicationContext());
						startActivity(new Intent(getApplicationContext(),
								AppsScreen.class));

						// new Thread() {
						// @Override
						// public void run() {
						// // TODO Auto-generated method stub
						// try {
						// Thread.sleep(100000);
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						// processDialog.dismiss();
						// }
						// }.start();

						finish();

					} else {
						if (pawordCursor
								.getString(1)
								.toString()
								.equals(passwordFeild.getText().toString()
										.trim())) {
							startActivity(new Intent(getApplicationContext(),
									AppsScreen.class));
							//
							// new Thread() {
							// @Override
							// public void run() {
							// // TODO Auto-generated method stub
							// try {
							// Thread.sleep(100000);
							// } catch (InterruptedException e) {
							// // TODO Auto-generated catch block
							// e.printStackTrace();
							// }
							// processDialog.dismiss();
							// }
							// }.start();

							finish();
						} else {

							// processDialog.dismiss();
							Toast.makeText(getApplicationContext(),
									"You entered is incorrect password",
									Toast.LENGTH_LONG).show();
						}

						Log.e("", "");
					}

				}
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
