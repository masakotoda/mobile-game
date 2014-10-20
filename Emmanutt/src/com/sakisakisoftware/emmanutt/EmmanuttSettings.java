package com.sakisakisoftware.emmanutt;

import java.util.ArrayList;
import com.sakisakisoftware.sakiutil.SakiSQLiteHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmmanuttSettings 
{
	private String m_appName = "Emmanutt";
    private Activity m_activity;


    public EmmanuttSettings(Activity activity)
    {
        m_activity = activity;
    }

    public boolean save()
    {
        refreshSetting();

        SQLiteOpenHelper helper = new SakiSQLiteHelper(m_activity, m_appName);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            SakiSQLiteHelper.HistoryItem item = new SakiSQLiteHelper.HistoryItem();
            item.m_type = SakiSQLiteHelper.HistoryItem.typeAppEnd;
            SakiSQLiteHelper.saveItem(item, db);
        }
        catch (Exception e)
        {
        }
        db.close();
        return true;
    }

    public void load()
    {
        SQLiteOpenHelper helper = new SakiSQLiteHelper(m_activity, m_appName);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            SakiSQLiteHelper.HistoryItem item = new SakiSQLiteHelper.HistoryItem();
            item.m_type = SakiSQLiteHelper.HistoryItem.typeAppStart;
            SakiSQLiteHelper.saveItem(item, db);
        }
        catch (Exception e)
        {
        }
        db.close();
    }

    public void refreshSetting()
    {
    }

    public ArrayList<SakiSQLiteHelper.HistoryItem> loadHistory()
    {
        ArrayList<SakiSQLiteHelper.HistoryItem> items = null;
        SQLiteOpenHelper helper = new SakiSQLiteHelper(m_activity, m_appName);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            items = SakiSQLiteHelper.loadAllHistory(db);
        }
        catch (Exception e)
        {
        }
        db.close();
        return items;
    }

    public void clearHisotry()
	{
	    SQLiteOpenHelper helper = new SakiSQLiteHelper(m_activity, m_appName);
	    SQLiteDatabase db = helper.getWritableDatabase();
	    try
	    {
	        SakiSQLiteHelper.clearHistory(db);
	    }
	    catch (Exception e)
	    {
	    }
	    db.close();
	}

	public void notifyNewGame()
	{
        SQLiteOpenHelper helper = new SakiSQLiteHelper(m_activity, m_appName);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            SakiSQLiteHelper.HistoryItem item = new SakiSQLiteHelper.HistoryItem();
	        item.m_type = SakiSQLiteHelper.HistoryItem.typeNewGame;
	        SakiSQLiteHelper.saveItem(item, db);
        }
        catch (Exception e)
        {
        }
        db.close();
	}

	public void notifySaveGame(int level, int point, String description, boolean aborted)
	{
        SQLiteOpenHelper helper = new SakiSQLiteHelper(m_activity, m_appName);
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            SakiSQLiteHelper.HistoryItem item = new SakiSQLiteHelper.HistoryItem();
            if (aborted)
            {
                item.m_type = SakiSQLiteHelper.HistoryItem.typeSuspendGame;
            }
            else
            {
            	item.m_type = SakiSQLiteHelper.HistoryItem.typeFinishGame;
            }
            item.m_level = level;
            item.m_point = point;
            item.m_description = description;
            SakiSQLiteHelper.saveItem(item, db);
        }
        catch (Exception e)
        {
        }
        db.close();
	}
}
