package com.example.kijanu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcast extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{

		Intent service = new Intent(context, VideoLiveWallpaper.class);
		context.startService(service);

	}

}

