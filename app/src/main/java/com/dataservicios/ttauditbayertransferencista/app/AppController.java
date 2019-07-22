package com.dataservicios.ttauditbayertransferencista.app;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dataservicios.ttauditbayertransferencista.services.UplodMediaService;


public class AppController extends Application {

//	public static final String TAG = AppController.class.getSimpleName();
//
//	private boolean serviceRunningFlag;
//
//	private static AppController mInstance;
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		DatabaseManager.init(this);
//		Log.d(TAG, "onCreated");
//		startService(new Intent(this, UpdateService.class));
////		startService(new Intent(this, MonitoGPSServices.class));
//		mInstance = this;
//	}
//
//	public static synchronized AppController getInstance() {
//		return mInstance;
//	}
//
//	public void setServiceRunningFlag(boolean serviceRunningFlag) {
//		this.serviceRunningFlag = serviceRunningFlag;
//	}
//
//
//
//	@Override
//	public void onTerminate() {
//		super.onTerminate();
//		Log.i(TAG, "onTerminated");
//		stopService(new Intent(this, UpdateService.class));
////		stopService(new Intent(this, MonitoGPSServices.class));
//	}

	public static final String TAG = AppController.class.getSimpleName();
	private Context context;
	private Intent mServiceIntent;
	private UplodMediaService mUplodMediaService;


	public Context getContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "onCreated");
		context = this;
		mUplodMediaService = new UplodMediaService(getContext());
		mServiceIntent = new Intent(getContext(), mUplodMediaService.getClass());
		if (!isMyServiceRunning(mUplodMediaService.getClass())) {
			startService(mServiceIntent);
		}

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.i(TAG, "onTerminated");

	}

	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				Log.i ("isMyServiceRunning?", true+"");
				return true;
			}
		}
		Log.i ("isMyServiceRunning?", false+"");
		return false;
	}
}