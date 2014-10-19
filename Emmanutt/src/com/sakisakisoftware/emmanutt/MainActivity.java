package com.sakisakisoftware.emmanutt;

import com.sakisakisoftware.sakiutil.DialogHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private EmmanuttLayout m_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        showMainLayout();
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
}