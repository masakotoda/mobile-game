package com.sakisakisoftware.emmanutt;

import java.util.ArrayList;

import com.sakisakisoftware.sakiutil.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private EmmanuttLayout m_layout;
	private EmmanuttSettings m_settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        m_settings = new EmmanuttSettings(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        showMainLayout();
	}
	
    @Override
    protected void onDestroy()
    {
        //m_settings.save();
        super.onDestroy();        
    }
	
	public void showLevelSelectLayout()
	{
		m_layout = new LayoutLevelSelect(this);
		m_layout.initialize();
	}
	
	public void showLevelStartLayout(int level)
	{
		m_layout = new LayoutLevelStart(this, level);
		m_layout.initialize();
	}

	public void showGameLayout(int level)
	{
		m_layout = new LayoutGame(this, level);
		m_layout.initialize();
	}

	public void showMainLayout()
	{
		m_layout = new LayoutMain(this);
		m_layout.initialize();
	}
	
	public void showGameFinishLayout(int level, int correct, int point)
	{
		m_layout = new LayoutGameFinish(this, level, correct, point);
		m_layout.initialize();
	}

	public void saveHistory(int level, int score, String description)
	{
		m_settings.notifySaveGame(level, score, description, false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_history:
            showHistory();
            break;
        case R.id.action_option:
            showOption();
            break;
        case R.id.action_settings:
            showAbout();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (m_layout != null)
        {
            m_layout.onBackPressed();
        }
        else
        {
        	super.onBackPressed();
        }
    }
    
    void showAbout()
    {
    	DialogHelper.showAlert(this, "About Emmanutt", "Emmanutt by (c) Sakisaki software");
    }

    void showHistory()
    {
    	(new DialogHistory(this)).show();
    }

    void showOption()
    {
    	(new DialogOptions(this)).show();
    }

    ArrayList<SakiSQLiteHelper.HistoryItem> loadHistory()
    {
    	return m_settings.loadHistory();
    }
    
    public boolean isEasyMode()
    {
    	int value = m_settings.loadInteger("EasyMode");
    	return (value == 1);
    }
    
    public void setEasyMode(boolean value)
    {
 	   	m_settings.saveItem("EasyMode", value ? 1 : 0);
 	}
        
}
