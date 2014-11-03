package com.sakisakisoftware.emmanutt;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LayoutGameFinish implements EmmanuttLayout
{
	private MainActivity m_activity;
	TextView m_viewScore;
	TextView m_viewQuestion;
	TextView m_viewFortune;
	ImageView m_viewGradeL;
	ImageView m_viewGradeR;
	int m_correct;
	int m_point;
	int m_level;
	
	LayoutGameFinish(MainActivity activity, int level, int correct, int point)
	{
		m_activity = activity;
		m_correct = correct;
		m_point = point;
		m_level = level;
	}

	@Override
	public void onBackPressed()
	{
		m_activity.showLevelSelectLayout();
	}

	@Override
	public void initialize() 
	{
		m_activity.setTitle(m_activity.getString(R.string.app_name));

		m_activity.setContentView(R.layout.game_finish);
		
    	m_viewScore = (TextView)m_activity.findViewById(R.id.textViewScore);
    	m_viewQuestion = (TextView)m_activity.findViewById(R.id.textViewQuestion);
    	m_viewFortune = (TextView)m_activity.findViewById(R.id.textViewFortune);
    	m_viewGradeL = (ImageView)m_activity.findViewById(R.id.imageGradeL);
    	m_viewGradeR = (ImageView)m_activity.findViewById(R.id.imageGradeR);

    	m_viewScore.setText(Integer.toString(m_point));
    	m_viewQuestion.setText(Integer.toString(m_correct) + " / 10");
    	
    	int idDrawable = (m_correct < 5) ? R.drawable.grade_bad : R.drawable.grade_good;
		
    	if (m_correct < 5)
    	{
    		m_viewFortune.setText(m_activity.getString(R.string.nosecret));
    	}
    	else
    	{
    		m_viewFortune.setText(getSecret());
    	}

		m_viewGradeL.setImageResource(idDrawable);
		m_viewGradeR.setImageResource(idDrawable);
		setOnClickListnerNextLevel();
		setOnClickListnerExit();
		setOnClickListnerPlayAgain();
		
		new CountDownTimer(500 * 8, 500) 
		{
			public void onTick(long millisUntilFinished) 
			{
				if (m_viewGradeL.getVisibility() == View.VISIBLE)
	   	    	{
		   			m_viewGradeL.setVisibility(View.INVISIBLE);
		   			m_viewGradeR.setVisibility(View.VISIBLE);
	   	    	}
	   	    	else
	   	    	{
		   			m_viewGradeR.setVisibility(View.INVISIBLE);
		   			m_viewGradeL.setVisibility(View.VISIBLE);
	   	    	}
	   	    }

	   	    public void onFinish() 
	   	    {
	   			m_viewGradeR.setVisibility(View.VISIBLE);
	   			m_viewGradeL.setVisibility(View.VISIBLE);
	   	    }
		}.start();   
	}
	
	void setOnClickListnerNextLevel()
	{
		if (m_level < 7)
		{
			m_activity.findViewById(R.id.buttonNextLevel).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					m_activity.showLevelStartLayout(m_level + 1);
				}
			});
		}
		else
		{
			m_activity.findViewById(R.id.buttonNextLevel).setEnabled(false);
		}
	}

	void setOnClickListnerPlayAgain()
	{
		m_activity.findViewById(R.id.buttonPlayAgain).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				m_activity.showLevelSelectLayout();
			}
		});
	}

	void setOnClickListnerExit()
	{
		m_activity.findViewById(R.id.buttonExit).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				m_activity.finish();
			}
		});
	}
	
	String getSecret()
	{
		int[] resIDs = new int [10];
		resIDs[0] = R.string.emmassecret;
		resIDs[1] = R.string.secret1;
		resIDs[2] = R.string.secret2;
		resIDs[3] = R.string.secret3;
		resIDs[4] = R.string.secret4;
		resIDs[5] = R.string.secret5;
		resIDs[6] = R.string.secret6;
		resIDs[7] = R.string.secret7;
		resIDs[8] = R.string.secret8;
		resIDs[9] = R.string.secret9;
		int index = (int)(Math.random() * resIDs.length);
		if (index >= resIDs.length)
			index = 0;
		
		String secret = "";
		if (index > 0)
			secret += "Well done! I will tell you a secret about " + Integer.toString(index) + ".\n";
		secret += m_activity.getString(resIDs[index]);
		return secret;
	}
}
