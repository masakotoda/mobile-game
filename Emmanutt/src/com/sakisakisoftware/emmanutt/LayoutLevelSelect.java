package com.sakisakisoftware.emmanutt;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

public class LayoutLevelSelect implements EmmanuttLayout
{
	private MainActivity m_activity;
	private CountDownTimer m_countDown;
	private int m_counter;
	private View[] m_imageViews = new ImageView[4];
	
	LayoutLevelSelect(MainActivity activity)
	{
		m_activity = activity;
	}

	public void onBackPressed()
	{
		m_countDown.cancel();
		m_activity.finish();
	}

	@Override
	public void initialize() 
	{
		m_activity.setContentView(R.layout.level_select);

		m_imageViews[0] = m_activity.findViewById(R.id.imageView1);
		m_imageViews[1] = m_activity.findViewById(R.id.imageView2);
		m_imageViews[2] = m_activity.findViewById(R.id.imageView3);
		m_imageViews[3] = m_activity.findViewById(R.id.imageView4);

		final int levels = 7;
		int[] ids = new int[levels];
		ids[0] = R.id.button1;
		ids[1] = R.id.button2;
		ids[2] = R.id.button3;
		ids[3] = R.id.button4;
		ids[4] = R.id.button5;
		ids[5] = R.id.button6;
		ids[6] = R.id.button7;
		for (int i = 0; i < levels; i++)
		{
			final int level = i + 1;
			m_activity.findViewById(ids[i]).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					m_countDown.cancel();
					m_activity.showLevelStartLayout(level);
				}
			});
		}
		
		m_countDown = new CountDownTimer(60 * 1000, 500) 
		{
			public void onTick(long millisUntilFinished)
			{
				m_counter++;
				int mod = (m_counter % 4);
				int[] images = new int[4];
				images[0] = R.drawable.op1;
				images[1] = R.drawable.op2;
				images[2] = R.drawable.op3;
				images[3] = R.drawable.op4;
				for (int i = 0; i < 4; i++)
				{
					if (mod >= 4)
						mod = 0;
					((ImageView)m_imageViews[i]).setImageResource(images[mod]);
					mod++;
				}
			}

			public void onFinish()
			{
			}
		}.start();
	}
}
