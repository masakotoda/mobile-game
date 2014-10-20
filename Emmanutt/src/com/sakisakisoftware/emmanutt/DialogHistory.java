package com.sakisakisoftware.emmanutt;
import com.sakisakisoftware.sakiutil.*;
import java.util.ArrayList;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class DialogHistory extends Dialog
{    
   protected MainActivity m_activity;
 
   void setContentView()
   {
       setContentView(R.layout.dialog_history);
   }
   
	    public DialogHistory(MainActivity activity)
	    {
	        super(activity);
	        m_activity = activity;
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView();
	        
	        Button closeButton = (Button) findViewById(R.id.buttonClose);
	        setButtonActionClose(closeButton);

	        loadHistory();
	    }

	    private void setButtonActionClose(Button clicked)
	    {
	        clicked.setOnClickListener(new View.OnClickListener() 
	        {
	            @Override
	            public void onClick(View v) 
	            {
	                dismiss();
	            }
	        });
	    }

	    private void loadHistory()
	    {
	        ArrayList<SakiSQLiteHelper.HistoryItem> items = m_activity.loadHistory();
	        ListView listView = (ListView) findViewById(R.id.listViewHistory);
	        ListAdapterHistory adapter = new ListAdapterHistory(m_activity, items);
	        listView.setAdapter(adapter);
	    }
	}
