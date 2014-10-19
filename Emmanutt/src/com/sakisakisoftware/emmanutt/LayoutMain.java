package com.sakisakisoftware.emmanutt;

import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.view.View;

public class LayoutMain implements EmmanuttLayout
{
	private MainActivity m_activity;
	
	LayoutMain(MainActivity activity)
	{
		m_activity = activity;
	}

	@Override
	public void initialize() 
	{
		m_activity.setContentView(R.layout.activity_main);
		
		View view = m_activity.findViewById(R.id.root_layout);
		TransitionDrawable transition = (TransitionDrawable)view.getBackground();
		transition.startTransition(5000);
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() 
			{ public void run() { m_activity.showLevelSelectLayout(); } },
			6000); 		
	}

	@Override
	public void onBackPressed()
	{
        m_activity.finish();
	}
}
