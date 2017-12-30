package com.example.android.organizer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class admin extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor x,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
         db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
         x = db.rawQuery("select * from user", null);
         y = db.rawQuery("select * from att ", null);
        y.moveToFirst();
        x.moveToFirst();
        int n = x.getCount();
        String ip;
        LinearLayout LL = (LinearLayout) findViewById(R.id.in);
        for(int i = 0 ;i<n;i++){
            TextView tv = new TextView(this);
            tv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ip = "Username : "+x.getString(0)+"  Password : "+x.getString(1)+"  No of subjects : "+cat(x.getString(2))+"\n";
            tv.setText(ip);
            LL.addView(tv);
            x.moveToNext();
        }
        n = y.getCount();
        for(int i = 0 ;i<n;i++){
            TextView tv = new TextView(this);
            tv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ip = ""+y.getString(0)+" "+y.getString(1)+" "+y.getString(2)+" "+y.getString(3)+"\n";
            tv.setText(ip);
            LL.addView(tv);
            y.moveToNext();
        }

        x.close();
        y.close();

    }
    public int cat(String cl)
    {
        if (cl.equals("A")) {
           return 4;
        } else if (cl.equals("B")) {
            return 5;
        } else return 6;
    }
}
