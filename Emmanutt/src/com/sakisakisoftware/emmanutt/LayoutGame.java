package com.sakisakisoftware.emmanutt;

import com.sakisakisoftware.sakiutil.*;

import java.util.ArrayList;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutGame implements EmmanuttLayout
{
	MainActivity m_activity;
	int m_level;

	final Character m_opUnk = '?';
	final Character m_opAdd = '+';
	final Character m_opSub = '-';
	final Character m_opMul = '*';
	final Character m_opDiv = '/';
	final Integer m_countLevel = 7;
	final Integer m_total = 10;
	int[] m_timeout = new int[m_countLevel];

	CountDownTimer m_countDown;
	Integer m_score = 0;
	Integer m_timer = 0;
	Integer m_remaining = m_total;
	Integer m_correct = 0;
	Boolean m_cheated = false;
	Boolean m_pause = false;
	
	TextView m_viewScore;
	TextView m_viewTimer;
	ImageView m_viewGradeL;
	ImageView m_viewGradeR;
	View[] m_numbers = new View[m_countLevel + 1];
	View[] m_operators = new View[m_countLevel];
	View[] m_results = new View[8]; //0-99999999
	
	ArrayList<Integer> m_listVals = new ArrayList<Integer>();
	ArrayList<Character> m_listCheatOps = new ArrayList<Character>();
	ArrayList<Character> m_listUserOps = new ArrayList<Character>();
	ProblemGenerator m_generator = new ProblemGenerator();
	
	public LayoutGame(MainActivity activity, int level)
	{
		m_activity = activity;
		m_level = level;
		
		m_timeout[0] = 10;
		m_timeout[1] = 15;
		m_timeout[2] = 20;
		m_timeout[3] = 30;
		m_timeout[4] = 45;
		m_timeout[5] = 60;
		m_timeout[6] = 100;
	}

	@Override
	public void onBackPressed()
	{
		if (m_countDown != null)
		{
			m_countDown.cancel();
		}

		m_activity.showLevelSelectLayout();
	}

    @Override
	public void initialize() 
	{
    	m_activity.setContentView(R.layout.game_play);
      	 
    	m_viewScore = (TextView)m_activity.findViewById(R.id.textViewScore);
    	m_viewTimer = (TextView)m_activity.findViewById(R.id.textViewTimer);
    	m_viewGradeL = (ImageView)m_activity.findViewById(R.id.imageGradeL);
    	m_viewGradeR = (ImageView)m_activity.findViewById(R.id.imageGradeR);

		m_numbers[0] = m_activity.findViewById(R.id.textView1);
		m_numbers[1] = m_activity.findViewById(R.id.textView2);
		m_numbers[2] = m_activity.findViewById(R.id.textView3);
		m_numbers[3] = m_activity.findViewById(R.id.textView4);
		m_numbers[4] = m_activity.findViewById(R.id.textView5);
		m_numbers[5] = m_activity.findViewById(R.id.textView6);
		m_numbers[6] = m_activity.findViewById(R.id.textView7);
		m_numbers[7] = m_activity.findViewById(R.id.textView8);
		
		m_operators[0] = m_activity.findViewById(R.id.buttonOp1);
		m_operators[1] = m_activity.findViewById(R.id.buttonOp2);
		m_operators[2] = m_activity.findViewById(R.id.buttonOp3);
		m_operators[3] = m_activity.findViewById(R.id.buttonOp4);
		m_operators[4] = m_activity.findViewById(R.id.buttonOp5);
		m_operators[5] = m_activity.findViewById(R.id.buttonOp6);
		m_operators[6] = m_activity.findViewById(R.id.buttonOp7);
		
		m_results[7] = m_activity.findViewById(R.id.buttonDigit1);
		m_results[6] = m_activity.findViewById(R.id.buttonDigit2);
		m_results[5] = m_activity.findViewById(R.id.buttonDigit3);
		m_results[4] = m_activity.findViewById(R.id.buttonDigit4);
		m_results[3] = m_activity.findViewById(R.id.buttonDigit5);
		m_results[2] = m_activity.findViewById(R.id.buttonDigit6);
		m_results[1] = m_activity.findViewById(R.id.buttonDigit7);
		m_results[0] = m_activity.findViewById(R.id.buttonDigit8);
		
		m_viewGradeL.setVisibility(View.GONE);
    	m_viewGradeR.setVisibility(View.GONE);    	
   	 	m_viewScore.setText("0");
   	 	m_viewTimer.setText("0");
   	 	
   	 	for (int i = m_level + 1; i < m_numbers.length; i++)
   	 	{
   	 		m_numbers[i].setVisibility(View.GONE);
   	 	}
   	 	
   	 	for (int i = m_level; i < m_operators.length; i++)
   	 	{
   	 		m_operators[i].setVisibility(View.GONE);
   	 	}
   	 	
		int screenH = ScreenHelper.getScreenHeight(m_activity);		
   	 	int width = screenH / (m_level * 2 + 6);
   	 	int height = (int)(width * 1.5);

   	 	for (int i = 0; i < m_level + 1; i++)
   	 	{
   	 		View view = m_numbers[i];
	   	 	setHeight(view, height);
	   	 	setWidth(view, width);
   	 	}
   	 	for (int i = 0; i < m_level; i++)
   	 	{
   	 		View view = m_operators[i];
	   	 	setHeight(view, height);
	   	 	setWidth(view, width);
   	 	}

   	 	View buttonEqual = m_activity.findViewById(R.id.buttonEqual);
   	 	buttonEqual.setBackgroundResource(R.drawable.op5);
   	 	setHeight(buttonEqual, height);
   	 	setWidth(buttonEqual, width);

   	 	for (int i = 0; i < m_results.length; i++)
   	 	{
   	 		View view = m_results[i];
   	 		setHeight(view, height);
	   	 	setWidth(view, width);
   	 	}   	 	
   	 	
   	 	setOnClickListnerCheck();
   	 	setOnClickListnerCheat();
   	 	setOnClickListnerPass();
   	 	setOnClickListnerPause();
   	 	setOnClickListnerOperators();
   	 	
   	 	newProblem();
	}
	
	void newProblem()
	{
   	 	if (m_countDown != null)
   	 	{
   	 		m_countDown.cancel();
   	 	}

		if (m_remaining == 0)
		{
			String description = "You have solved " + m_correct + " problems!";
			m_activity.saveHistory(m_level, m_score, description);
			
			showTheGrade();
			return;
		}
		
		m_remaining--;

		m_cheated = false;
		m_listVals.clear();
		m_listCheatOps.clear();
		m_listUserOps.clear();
		
   	 	Integer result = m_generator.generateProblem(m_level, m_listVals, m_listCheatOps);

   	 	for (int i = 0; i < m_listCheatOps.size(); i++)
   	 	{
   	 		m_listUserOps.add(m_opUnk);
   	 	}
   	 	
		Integer current = m_total - m_remaining;
		String title = "Question " + current.toString() + " / " + m_total.toString();
		m_activity.setTitle(title);
		
   	 	for (int i = 0; i < m_level + 1; i++)
   	 	{
   	 		setNumberImage(m_numbers[i], m_listVals.get(i));
   	 	}
   	 	
   	 	for (int i = 0; i < m_level; i++)
   	 	{
   	 		m_operators[i].setBackgroundResource(R.drawable.op0);
   	 	}

   	 	setResultImage(result);
   	 	
   	 	startCountDownTimer(m_timeout[m_level - 1]);
   	}
	
	void startCountDownTimer(int remaining)
	{
		m_countDown = new CountDownTimer(1000 * remaining, 1000) 
		{
			public void onTick(long millisUntilFinished)
			{
				m_timer = (int)(millisUntilFinished / 1000);
				m_viewTimer.setText(m_timer.toString());
			}

			public void onFinish()
			{
				m_timer = 0;
				m_viewTimer.setText("0");
				Toast.makeText(m_activity, "Timeout", Toast.LENGTH_SHORT).show();
			}
		}.start();
	}
	
	void setResultImage(int result)
	{
		int multi = 10;
		for (int i = 1; i < 8; i++)
		{
			if (result < multi)
				setNumberImage(m_results[i], -1);
			else
				setNumberImage(m_results[i], (result / multi) % 10);
			multi *= 10;
		}	 	
 		setNumberImage(m_results[0], (result % 10) / 1);
	}
	
	void setNumberImage(int resID, int number)
	{
		View view = m_activity.findViewById(resID);
		setNumberImage(view, number);
	}

	void setNumberImage(View view, int number)
	{
		if (number >= 10)
			throw new IllegalArgumentException("invalid parameter");
		
		if (number < 0)
		{
			view.setVisibility(View.GONE);
		}
		else
		{
			view.setVisibility(View.VISIBLE);
			int[] drawables = new int[10];
			drawables[0] = R.drawable.num0;
			drawables[1] = R.drawable.num1;
			drawables[2] = R.drawable.num2;
			drawables[3] = R.drawable.num3;
			drawables[4] = R.drawable.num4;
			drawables[5] = R.drawable.num5;
			drawables[6] = R.drawable.num6;
			drawables[7] = R.drawable.num7;
			drawables[8] = R.drawable.num8;
			drawables[9] = R.drawable.num9;
		
			view.setBackgroundResource(drawables[number]);
		}		
	}
	
	void setOperatorImage(View view, char op)
	{
		int drawable = 0;
		if (op == m_opUnk)
			drawable = R.drawable.op0;
		else if (op == m_opAdd)
			drawable = R.drawable.op1;
		else if (op == m_opSub)
			drawable = R.drawable.op2;
		else if (op == m_opMul)
			drawable = R.drawable.op3;
		else if (op == m_opDiv)
			drawable = R.drawable.op4;
		
		if (drawable != 0)
		{
			view.setBackgroundResource(drawable);
		}
	}
	
	void showTheGrade()
	{
		m_activity.showGameFinishLayout(m_level, m_correct, m_score);
	}
	
	void setOnClickListnerCheck()
	{
		View view = m_activity.findViewById(R.id.buttonCheck);
   	 	view.setOnClickListener(new View.OnClickListener()
   		{
   			@Override
   			public void onClick(View v)
   			{
				boolean solved = true;
				for (char x : m_listUserOps)
				{
					if (x == m_opUnk)
					{
						solved = false;
						break;
					}
				}
					
				if (solved)
				{
					int user = m_generator.evaluate(m_listVals, m_listUserOps);
					int machine = m_generator.evaluate(m_listVals, m_listCheatOps);
					if (user == machine)
					{
						if (!m_cheated)
						{
							m_correct++;
						}
						m_score += (m_timer * 2 * m_level);
						m_viewScore.setText(m_score.toString());
						if (!m_cheated)
						{
							Toast.makeText(m_activity, "Correct!", Toast.LENGTH_SHORT).show();							
						}
						newProblem();
					}
					else
					{
						Toast.makeText(m_activity, "Wrong... try again", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(m_activity, "Fill all operators!", Toast.LENGTH_SHORT).show();
				}
			}
   		});
	}
	
	void setOnClickListnerOperators()
	{
   	 	for (int i = 0; i < m_level; i++)
   	 	{
   	 		final int index = i;
   	 		m_operators[i].setOnClickListener(new View.OnClickListener()
   			{
   				@Override
   				public void onClick(View v)
   				{
   					char op = m_listUserOps.get(index);
   					char newOp = getNextOperator(op);
   					m_listUserOps.set(index, newOp);
   					setOperatorImage(v, newOp);
   				}
   			});
   	 	}
	}
	
	void setOnClickListnerCheat()
	{
		m_activity.findViewById(R.id.buttonCheat).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
		   	 	m_countDown.cancel();
		   	 	m_timer = 0;
		   	 	m_viewTimer.setText("0");
		   	 	m_cheated = true;

		   	 	for (int i = 0; i < m_level; i++)
		   	 	{
		   	 		char opCheat = m_listCheatOps.get(i);
		   	 		m_listUserOps.set(i, opCheat);
		   	 		
		   	 		View view = m_operators[i];
		   	 		setOperatorImage(view, opCheat);
		   	 	}
			}
		});
	}
	
	void setOnClickListnerPass()
	{
		m_activity.findViewById(R.id.buttonPass).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				newProblem();
			}
		});
	}
	
	void setOnClickListnerPause()
	{
		m_activity.findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				m_activity.findViewById(R.id.buttonPass).setEnabled(m_pause);
				m_activity.findViewById(R.id.buttonCheat).setEnabled(m_pause);
				m_activity.findViewById(R.id.buttonCheck).setEnabled(m_pause);
				for (int i = 0; i < m_operators.length; i++)
					m_operators[i].setEnabled(m_pause);
				
				if (m_pause)
				{
					m_pause = false;
					startCountDownTimer(m_timer);
				}
				else
				{
					m_pause = true;
					m_countDown.cancel();
				}		
			}
		});
	}
	
	char getNextOperator(char op)
	{
		char newOp = m_opUnk;
		if (op == m_opUnk)
			newOp = m_opAdd;
		else if (op == m_opAdd)
			newOp = m_opSub;
		else if (op == m_opSub)
			newOp = m_opMul;
		else if (op == m_opMul)
			newOp = m_opDiv;
		else if (op == m_opDiv)
			newOp = m_opAdd;
		return newOp;
	}
	
	static void setWidth(View view, int width)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }

    static void setHeight(View view, int height)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }
    
    void setWidth(int resID, int width)
    {
    	setWidth(m_activity.findViewById(resID), width);
    }

    void setHeight(int resID, int height)
    {
    	setHeight(m_activity.findViewById(resID), height);
    }	
}
