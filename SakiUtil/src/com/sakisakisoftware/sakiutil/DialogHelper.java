package com.sakisakisoftware.sakiutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHelper {
    public static void showAlert(Context context, String title, String message)
    {
          AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

          // Set title
          alertDialogBuilder.setTitle(title);
       
          // Set dialog message
          alertDialogBuilder.setMessage(message);
          alertDialogBuilder.setCancelable(false);
          alertDialogBuilder.setPositiveButton("Close", 
              new DialogInterface.OnClickListener() 
              {
                  public void onClick(DialogInterface dialog, int id)
                  {
                      dialog.cancel();
                  }
              });

          // Create alert dialog
          AlertDialog alertDialog = alertDialogBuilder.create();       
          alertDialog.show();    
    }
}
