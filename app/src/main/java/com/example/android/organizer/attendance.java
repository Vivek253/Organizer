package com.example.android.organizer;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;



import java.text.DecimalFormat;

public class attendance extends AppCompatActivity {

    String user,suber;
    SQLiteDatabase db ;

    Cursor x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        user = extras.getString("user");

        assert user != null;
        user = user.replaceAll("\\s", "");

        db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        init();
        init2();
        init3();
    }
    public void init3(){
         x = db.rawQuery("select * from rem where name = '"+user+"'and priority = 'A'", null);
         int a= x.getCount();
        x.close();
        x = db.rawQuery("select * from rem where name = '"+user+"'and priority = 'B'", null);
        int b = x.getCount();
        x.close();
        x = db.rawQuery("select * from rem where name = '"+user+"'and priority = 'C'", null);
        int c = x.getCount();
        x.close();
        x = db.rawQuery("select * from rem where name = '"+user+"'and priority = 'D'", null);
        int d = x.getCount();
        x.close();
        x = db.rawQuery("select * from rem where name = '"+user+"'and priority = 'E'", null);
        int e = x.getCount();
        x.close();
        x = db.rawQuery("select * from rem where name = '"+user+"'and priority = 'F'", null);
        int f = x.getCount();
        x.close();
        TextView t1 = (TextView) findViewById(R.id.r1);

        TextView t2 = (TextView) findViewById(R.id.r2);

        TextView t3 = (TextView) findViewById(R.id.r3);

        TextView t4 = (TextView) findViewById(R.id.r4);

        TextView t5 = (TextView) findViewById(R.id.r5);

        TextView t6 = (TextView) findViewById(R.id.r6);

        if(a!=0){
            if(a!=1)
                t1.setText(getString(R.string.Tasks_pending,a));
            else
                t1.setText(R.string.One_task);
        }
        if(b!=0){
            if(b!=1)
                t2.setText(getString(R.string.Tasks_pending,b));
            else
                t2.setText(R.string.One_task);
        }
        if(c!=0){
            if(c!=1)
                t3.setText(getString(R.string.Tasks_pending,c));
            else
                t3.setText(R.string.One_task);
        }
        if(d!=0){
            if(d!=1)
                t4.setText(getString(R.string.Tasks_pending,d));
            else
                t4.setText(R.string.One_task);
        }
        if(e!=0){
            if(e!=1)
                t5.setText(getString(R.string.Tasks_pending,e));
            else
                t5.setText(R.string.One_task);
        }
        if(f!=0) {
            if(f!=1)
            t6.setText(getString(R.string.Tasks_pending,f));
            else
                t6.setText(R.string.One_task);
        }
    }


    public String fixin(String s)
    {
        s = s.replaceAll("<><>","'");
        return s;
    }
    public void init() {
        String a1, a2, a3, a4, a5, a6;
        TextView t1 = (TextView) findViewById(R.id.s1);

        TextView t2 = (TextView) findViewById(R.id.s2);

        TextView t3 = (TextView) findViewById(R.id.s3);

        TextView t4 = (TextView) findViewById(R.id.s4);

        TextView t5 = (TextView) findViewById(R.id.s5);

        TextView t6 = (TextView) findViewById(R.id.s6);
         x = db.rawQuery("select * from sub where name = '" + user + "'", null);
        x.moveToFirst();

        a1 = x.getString(7);
        a1 = fixin(a1);
        a2 = x.getString(8);
        a2 = fixin(a2);
        a3 = x.getString(9);
        a3 = fixin(a3);
        a4 = x.getString(10);
        a4 = fixin(a4);
        a5 = x.getString(11);
        a5 = fixin(a5);
        a6 = x.getString(12);
        a6 = fixin(a6);
        t1.setText(a1);
        t2.setText(a2);
        t3.setText(a3);
        t4.setText(a4);
        t5.setText(a5);
        t6.setText(a6);
        x.close();


    }

    public String findsub( int no) {
        String ret;
         x = db.rawQuery("select * from sub where name = '" + user + "'", null);
        x.moveToFirst();
        ret = x.getString(no);
        x.close();

        return ret;
    }

    public void finish(View view){
        finish();
    }
    //Attendance set

    public void setstatus(TextView t, int abs, int pres) {
        String toset ;
       float fc, factor;
         x = db.rawQuery("select * from user where name = '"+user+"'", null);
        x.moveToFirst();
        fc = Integer.valueOf(x.getString(3));
        factor = fc/(100-fc);
        DecimalFormat df = new DecimalFormat("###");
        String fin;

        float bunks=(pres - factor*abs )/factor;

        if(factor*abs>pres) {
            fin = df.format(Math.ceil(factor*abs-pres));
           t.setTextColor(Color.parseColor("#a55c57"));
            if(fin.equals("1"))
                toset = " Absent : " + abs +" | Alert | Present : " + pres+"\nAttend one more class!";
                else
          toset = " Absent : " + abs +" | Alert | Present : " + pres+"\nAttend "+fin+" classes";

        }
        else if(factor*abs>pres-factor) {
            t.setTextColor(Color.parseColor("#f4c242"));
            toset = " Absent : " + abs +" | Just safe | Present : " + pres;
        }
        else {
            fin = df.format(Math.floor(bunks));
          t.setTextColor(Color.parseColor("#467a38"));
            if(fin.equals("1"))
            toset = " Absent : " + abs +" | Safe | Present : " +pres+"\n"+ fin +" bunk available!";
            else
                toset = " Absent : " + abs +" | Safe | Present : " +pres+"\n"+ fin +" bunks available!";
        }
        x.close();
        t.setText(toset);

    }

    public void init2() {

        int abs, pres;
        TextView st;
         //x;

        suber = findsub(1);
        x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            abs = Integer.valueOf(x.getString(3));
            pres = Integer.valueOf(x.getString(2));
            st = (TextView) findViewById(R.id.st1);
            setstatus(st, abs, pres);x.close();

        suber = findsub(2);
        x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            abs = Integer.valueOf(x.getString(3));
            pres = Integer.valueOf(x.getString(2));
            st = (TextView) findViewById(R.id.st2);
            setstatus(st, abs, pres);x.close();

        suber = findsub(3);
        x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            abs = Integer.valueOf(x.getString(3));
            pres = Integer.valueOf(x.getString(2));
            st = (TextView) findViewById(R.id.st3);
            setstatus(st, abs, pres);x.close();

        suber = findsub(4);
        x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            abs = Integer.valueOf(x.getString(3));
            pres = Integer.valueOf(x.getString(2));
            st = (TextView) findViewById(R.id.st4);
            setstatus(st, abs, pres);x.close();

        suber = findsub(5);
        x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            abs = Integer.valueOf(x.getString(3));
            pres = Integer.valueOf(x.getString(2));
            st = (TextView) findViewById(R.id.st5);
            setstatus(st, abs, pres);x.close();

        suber = findsub(6);
        x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            abs = Integer.valueOf(x.getString(3));
            pres = Integer.valueOf(x.getString(2));
            st = (TextView) findViewById(R.id.st6);
            setstatus(st, abs, pres);x.close();

    }

    public void pr1(View view) {
        suber = findsub(1);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            pres = pres + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void pr2(View view) {
        suber = findsub(2);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            pres = pres + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void pr3(View view) {
        suber = findsub(3);
       // suber = suber.replaceAll("\\s", "");
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            pres = pres + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void pr4(View view) {
        suber = findsub(4);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            pres = pres + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void pr5(View view) {
        suber = findsub(5);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            pres = pres + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void pr6(View view) {
        suber = findsub(6);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            pres = pres + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void ab1(View view) {
        suber = findsub(1);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            abs = abs + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void ab2(View view) {
        suber = findsub(2);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            abs = abs + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void ab3(View view) {
        suber = findsub(3);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            abs = abs + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void ab4(View view) {
        suber = findsub(4);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            abs = abs + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void ab5(View view) {
        suber = findsub(5);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            abs = abs + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }

    public void ab6(View view) {
        suber = findsub(6);
         x = db.rawQuery("select * from att where name = '" + user + "' and subname = '" + suber + "'", null);
            x.moveToFirst();
            int abs = Integer.valueOf(x.getString(3));
            int pres = Integer.valueOf(x.getString(2));
            String sub = x.getString(1);
            abs = abs + 1;
            db.execSQL("delete from att where name = '" + user + "'and subname = '" + suber + "'");
            db.execSQL("insert into att values('" + user + "','" + sub + "'," + pres + "," + abs + ")");x.close();
        init2();
    }


}


