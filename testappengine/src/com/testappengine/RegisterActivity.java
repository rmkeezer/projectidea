package com.testappengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class RegisterActivity extends Activity {

	enum State {
		REGISTERED, REGISTERING, UNREGISTERED, UNREGISTERING
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		if (GCMIntentService.PROJECT_NUMBER == null
				|| GCMIntentService.PROJECT_NUMBER.length() == 0) {
		} else {
			try {
				GCMIntentService.register(getApplicationContext());
			} catch (Exception e) {
				Log.e(RegisterActivity.class.getName(),
						"Exception received when attempting to register for Google Cloud "
								+ "Messaging. Perhaps you need to set your virtual device's "
								+ " target to Google APIs? "
								+ "See https://developers.google.com/eclipse/docs/cloud_endpoints_android"
								+ " for more information.", e);
			}
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Intent mainIntent = new Intent(this, IdeaWall.class);
		mainIntent.putExtra("deviceID", intent.getStringExtra("deviceID"));
		startActivity(mainIntent);

		finish();

	}
}