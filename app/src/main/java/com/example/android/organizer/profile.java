package com.example.android.organizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class profile extends AppCompatActivity {
    String user,cl;
    int ab=0,pr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        user = extras.getString("user");
        cl = extras.getString("class");
        assert cl!=null;
        cl = cl.replaceAll("\\s","");
        user = user.replaceAll("\\s","");
        init();
    }

    public void del1(View view){
        final Button sure = (Button) findViewById(R.id.del);
        sure.setText("   Sure?   ");

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               sure.setText(" Deleting... ");
                SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
                db.execSQL("delete from user where name = '"+user+"'");
                db.execSQL("delete from rem where name = '"+user+"'");
                db.execSQL("delete from att where name = '"+user+"'");
                db.execSQL("delete from sub where name = '"+user+"'");
                db.execSQL("delete from auto where name = '"+user+"'");
                db.execSQL("delete from remtime where name = '"+user+"'");
                db.execSQL("delete from auto");
                db.execSQL("insert into auto values('000','X')");
                Intent in = new Intent(v.getContext(),MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(in);

            }
        });
    }

    public String findsub( int no) {
        String ret;
        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        Cursor x = db.rawQuery("select * from sub where name = '" + user + "'", null);
        x.moveToFirst();
        ret = x.getString(no);
        x.close();
        return ret;
    }

    public void setstatus(TextView t, int abs, int pres ,TextView pers,TextView sub,TextView apers) {
        String toset , tosetpers;
        float fc, factor;
        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        Cursor x = db.rawQuery("select * from user where name = '"+user+"'", null);
        x.moveToFirst();
        fc = Integer.valueOf(x.getString(3));
        ab+=abs;
        pr+=pres;
        DecimalFormat df = new DecimalFormat("##.#");
        String fin;

        factor = fc/(100-fc);
        if((abs+pres)!=0){
        if(factor*abs>pres) {
            fin = df.format((100*pres/(abs+pres)));
            t.setTextColor(Color.parseColor("#a55c57"));
            sub.setTextColor(Color.parseColor("#a55c57"));
            pers.setTextColor(Color.parseColor("#a55c57"));
            toset = "Critical";
            tosetpers =""+fin;

        }
        else if(factor*abs>pres-factor) {
            fin = df.format((100*pres/(abs+pres)));
            t.setTextColor(Color.parseColor("#f4c242"));
            sub.setTextColor(Color.parseColor("#f4c242"));
            pers.setTextColor(Color.parseColor("#f4c242"));
            toset = "Just safe";
            tosetpers =""+fin;
        }
        else {
            fin = df.format((100*pres/(abs+pres)));
            t.setTextColor(Color.parseColor("#467a38"));
            sub.setTextColor(Color.parseColor("#467a38"));
            pers.setTextColor(Color.parseColor("#467a38"));
            toset = "Safe";
            tosetpers =""+fin;
        }
            apers.setText(""+df.format(fc)+"");
            t.setText(toset);
            pers.setText(tosetpers);
        }
        else{
            apers.setText(""+df.format(fc)+"");
            t.setText("---");
            pers.setText("---");
            t.setTextColor(Color.parseColor("#000000"));
            sub.setTextColor(Color.parseColor("#000000"));
            pers.setTextColor(Color.parseColor("#000000"));

        }
        x.close();

    }

    public String fixin(String s)
    {
        s = s.replaceAll("<><>","'");
        return s;
    }


    public void finish(View view){
        finish();
    }

    void init(){

        int ct;
        if (cl.equals("A")) {
            ct=4;
        } else if (cl.equals("B")) {
           ct=5;
        } else if (cl.equals("C")) {
           ct=6;

        }
        else
            ct=0;

        TextView id = (TextView) findViewById(R.id.uid);
        id.setText(getString(R.string.userdisp,user));
        TextView nosub = (TextView) findViewById(R.id.nosub);
        nosub.setText(""+ct+"");
        TextView apers = (TextView) findViewById(R.id.att);
        //set apers

        LinearLayout subject = (LinearLayout) findViewById(R.id.subb);
        LinearLayout status = (LinearLayout) findViewById(R.id.stat);
        LinearLayout percent = (LinearLayout) findViewById(R.id.pers);
        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        Cursor x = db.rawQuery("select * from sub where name = '" + user + "'", null);
        x.moveToFirst();

        for(int i =0;i<ct;i++){
            TextView a = new TextView(this);
            TextView b = new TextView(this);
            TextView c = new TextView(this);
            a.setText(fixin(x.getString(i+1)));
            a.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 200));

            b.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,200));

            c.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 200));

            a.setTextSize(20);
            b.setTextSize(20);
            c.setTextSize(20);

            String suber = findsub( i+1);
            //suber = suber.replaceAll("\\s", "");
            Cursor y = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            if (x.getCount() > 0) {
                y.moveToFirst();
                int abs = Integer.valueOf(y.getString(3));
                int pres = Integer.valueOf(y.getString(2));

                setstatus(b, abs, pres ,c ,a,apers);

            subject.addView(a);
            status.addView(b);
                percent.addView(c);


        }
    }
        TextView overall = (TextView) findViewById(R.id.tatt);
        if(pr+ab!=0)
        overall.setText(""+(100*pr)/(pr+ab)+"");
        else
            overall.setText("--");


        x.close();
}
}

