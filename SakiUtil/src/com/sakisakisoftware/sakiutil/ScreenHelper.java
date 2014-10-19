package com.sakisakisoftware.sakiutil;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

//In future, we may want to move this to lib
public class ScreenHelper {

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity activity)
    {
        int sdkVer = android.os.Build.VERSION.SDK_INT;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (sdkVer < 13)
        {
            size.x = display.getHeight();
            size.y = display.getWidth();
        }
        else
        {
            display.getSize(size);
        }
        if (size.x < size.y)
        {
            return size.x;
        }
        else
        {
            return size.y;
        }
    }
    
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity activity)
    {
        int sdkVer = android.os.Build.VERSION.SDK_INT;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (sdkVer < 13)
        {
            size.x = display.getHeight();
            size.y = display.getWidth();
        }
        else
        {
            display.getSize(size);
        }
        if (size.x < size.y)
        {
            return size.y;
        }
        else
        {
            return size.x;
        }
    }
    
    public static float getFactor(Activity activity)
    {
        int width = getScreenWidth(activity);
        float factor = width / 1080.f;
        return factor;        
    }
}
