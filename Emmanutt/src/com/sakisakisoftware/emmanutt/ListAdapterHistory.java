package com.sakisakisoftware.emmanutt;
import com.sakisakisoftware.sakiutil.*;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListAdapterHistory extends BaseAdapter
{
    private Activity m_activity;
    private ArrayList<SakiSQLiteHelper.HistoryItem> m_items;
    private Bitmap m_happy;
    private Bitmap m_neutral;
    private Bitmap m_unhappy;
         
    public ListAdapterHistory(
        Activity activity, 
        ArrayList<SakiSQLiteHelper.HistoryItem> items) 
    {
        m_activity = activity;
        m_items = items;
        m_happy = BitmapFactory.decodeResource(m_activity.getResources(), R.drawable.op1);
        m_neutral = BitmapFactory.decodeResource(m_activity.getResources(), R.drawable.op2);
        m_unhappy = BitmapFactory.decodeResource(m_activity.getResources(), R.drawable.op3);
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {    
        View gridView;
        if (convertView == null) 
        {    
            LayoutInflater inflater = (LayoutInflater) m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = new View(m_activity);    
            gridView = inflater.inflate(R.layout.adapter_history, null);
        }
        else 
        {
            gridView = convertView;
        }

        SakiSQLiteHelper.HistoryItem item = m_items.get(position);
            
        String info = item.m_dateTime + " ";
        Bitmap bitmap = m_neutral;
        switch (item.m_type)        
        {
        case SakiSQLiteHelper.HistoryItem.typeAppStart:
        	info += "App Start";
        	break;
        case SakiSQLiteHelper.HistoryItem.typeAppEnd:
        	info += "App End";
        	bitmap = m_unhappy;
        	break;
        case SakiSQLiteHelper.HistoryItem.typeNewGame:
        	info += "New Game";
        	break;
        case SakiSQLiteHelper.HistoryItem.typeSuspendGame:
        	info += "Suspend - level ";
        	info += Integer.toString(item.m_level);
        	bitmap = m_unhappy;
        	break;
        case SakiSQLiteHelper.HistoryItem.typeResumeGame:
        	info += "Resume - level ";
        	info += Integer.toString(item.m_level);
        	break;
        case SakiSQLiteHelper.HistoryItem.typeFinishGame:
        	info += "Finish - level ";
        	info += Integer.toString(item.m_level);
        	info += ", ";
        	info += Integer.toString(item.m_point);
        	info += "pt.\n";
        	info += item.m_description;
        	bitmap = m_happy;
        	break;            
        }
        TextView description = (TextView) gridView.findViewById(R.id.description);
        description.setText(info);

        ImageView imageView = (ImageView) gridView.findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

        return gridView;
    }

    @Override
    public int getCount() 
    {
        return m_items.size();
    }

    @Override
    public Object getItem(int position) 
    {
        return null;
    }

    @Override
    public long getItemId(int position) 
    {
        return position;
    }
}
