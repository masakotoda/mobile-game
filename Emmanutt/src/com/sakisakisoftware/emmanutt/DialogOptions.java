package com.sakisakisoftware.emmanutt;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

public class DialogOptions extends Dialog
{    
   protected MainActivity m_activity;
 
   void setContentView()
   {
       setContentView(R.layout.dialog_option);
   }
   
   public DialogOptions(MainActivity activity)
   {
       super(activity);
       m_activity = activity;
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView();
       
       Button closeButton = (Button) findViewById(R.id.buttonClose);
       setButtonActionClose(closeButton);

       loadOptions();
   }

   private void setButtonActionClose(Button clicked)
   {
       clicked.setOnClickListener(new View.OnClickListener() 
       {
           @Override
           public void onClick(View v) 
           {
        	   CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxEasy);
        	   m_activity.setEasyMode(checkBox.isChecked());
        	   dismiss();
           }
       });
   }

   private void loadOptions()
   {
	   CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxEasy);
	   checkBox.setChecked(m_activity.isEasyMode());
   }
}
