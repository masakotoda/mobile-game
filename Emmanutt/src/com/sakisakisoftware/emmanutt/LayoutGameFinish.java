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
    		m_viewFortune.setText("Your performance doesn't deserve my secret.\nTry harder, Grasshopper...");
    	}
    	else
    	{
    		m_viewFortune.setText("Well done! I will tell you my secret.\nI was paid a salary of $10 per month for a 54 hour week.\nDo you make more that I did? No kidding!");
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
}
