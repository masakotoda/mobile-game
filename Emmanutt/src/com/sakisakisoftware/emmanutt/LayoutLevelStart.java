package com.sakisakisoftware.emmanutt;

import android.os.Handler;
import android.widget.TextView;

public class LayoutLevelStart implements EmmanuttLayout
{
	private MainActivity m_activity;
	int m_level;
	
	LayoutLevelStart(MainActivity activity, int level)
	{
		m_activity = activity;
		m_level = level;
	}

	public void onBackPressed()
	{
	}

	@Override
	public void initialize() 
	{
		m_activity.setTitle(m_activity.getString(R.string.app_name));

		m_activity.setContentView(R.layout.level_start);
		TextView view = (TextView)m_activity.findViewById(R.id.textView1);
		view.setText("Level " + m_level);

		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() 
			{ public void run() { m_activity.showGameLayout(m_level); } },
			2000);
	}
}
