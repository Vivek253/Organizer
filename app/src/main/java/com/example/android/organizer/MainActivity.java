package com.example.android.organizer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    View viewx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        not("Hi","Welcomeo to Organizer!",1);
        admin();
        init();


    }

    public void not(String title,String Text,int id)
    {
        Intent in = new Intent(this,admin.class);
       PendingIntent i = PendingIntent.getActivity(this,1,in,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder myno = new NotificationCompat.Builder(this);
        myno.setSmallIcon(R.mipmap.ic_launcher);
        myno.setContentText(Text);
        myno.setContentTitle(title);
       // myno.setContentIntent(i);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, myno.build());
    }

    public void init() {


        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(name varchar,password varchar,category varchar(2),cutoff number);");
        db.execSQL("CREATE TABLE IF NOT EXISTS rem(name varchar ,title varchar , priority varchar(2));");
        db.execSQL("CREATE TABLE IF NOT EXISTS att(name varchar, subname varchar(10), present number  , absent number  );");
        db.execSQL("CREATE TABLE IF NOT EXISTS sub(name varchar,s1 varchar , s2 varchar , s3 varchar , s4 varchar ,s5 varchar , s6 varchar , a1 varchar , a2 varchar , a3 varchar , a4 varchar , a5 varchar ,a6 varchar )");
        db.execSQL("CREATE TABLE IF NOT EXISTS auto(name varchar, cat varchar);");
       db.execSQL("CREATE TABLE IF NOT EXISTS remtime(name varhar,rem varchar,ctime varchar,ttime varchar); ");
      //  db.execSQL("delete from remtime");



        Cursor autolog = db.rawQuery("select count(*) from auto", null);
        autolog.moveToFirst();
        int icount = autolog.getInt(0);
        if (icount <= 0)
            db.execSQL("insert into auto values('000','X')");
        autolog = db.rawQuery("select * from auto", null);


        autolog.moveToLast();

        String name = autolog.getString(0);
        String cl = autolog.getString(1);
        name = name.replaceAll("\\s", "");
        cl = cl.replaceAll("\\s", "");
        if (!name.equals("000")) {

            Intent tc = new Intent(this, home.class);
            tc.putExtra("user", name);
            tc.putExtra("class",cl);
            this.startActivity(tc);

            /*
            if (cl.equals("A")) {
                Intent tc = new Intent(this, reminders4.class);
                tc.putExtra("user", name);
                this.startActivity(tc);
            } else if (cl.equals("B")) {
                Intent tc = new Intent(this, reminders5.class);
                tc.putExtra("user", name);
                this.startActivity(tc);
            } else if (cl.equals("C")) {
                Intent tc = new Intent(this, reminders.class);
                tc.putExtra("user", name);
                this.startActivity(tc);
            }*/


        } else {
            final EditText epw = (EditText) findViewById(R.id.pw);
            EditText ename = (EditText) findViewById(R.id.name);
            epw.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                    if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        login();
                        return true;

                    }
                    return false;
                }
            });

            ename.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                    if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {


                        epw.requestFocus();
                        return true;

                    }
                    return false;
                }
            });
        }
        autolog.close();

    }

    public int fibo(int n)      //sum of fibos upto n
    {
        int first = 0, second = 1, next, c,sum=0;


        for ( c = 0 ; c < n ; c++ )
        {
            if ( c <= 1 )
                next = c;
            else
            {
                next = first + second;
                first = second;
                second = next;
            }
            sum+=next;
        }
        return sum;
    }

    public String admin()
    {
        String ret ;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int date = c.get(Calendar.DATE);
        int min = c.get(Calendar.MINUTE);

      /*  if(min>=30)
            hour = (hour + 6)%24;

            else
                hour = (hour+5)%24; //utc time correction*/




        int sum =0;
        sum+=fibo(date%10);
        sum+=fibo(min/10);

        sum+=fibo(hour);

        ret = ""+sum;

       return ret;

    }

    public void login(View view) {


        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        Cursor users = db.rawQuery("select * from user", null);
        String uname, pw, guname, gpw;
        final EditText epw = (EditText) findViewById(R.id.pw);
        final EditText ename = (EditText) findViewById(R.id.name);
        final CheckBox remme = (CheckBox) findViewById(R.id.remme);
        boolean rem = remme.isChecked();
        uname = ename.getText().toString();
        uname = uname.replaceAll("\\s", "");
        pw = epw.getText().toString();
        pw = pw.replaceAll("\\s", "");
        if(uname.equals("")||(pw.equals("")))
        {
            //do nothing
        }
        else if((uname.equals("tip")||uname.equals("Tip"))&&pw.equals(admin()))
        {
            Intent tc = new Intent(this, admin.class);
            ename.setText("");
            epw.setText("");
            this.startActivity(tc);

        }
        else {
            int n = users.getCount();
            users.moveToFirst();
            int status = 0;
            String cl = null;
            for (int i = 0; i < n; i++) {
                guname = users.getString(0);
                guname = guname.replaceAll("\\s", "");
                gpw = users.getString(1);
                gpw = gpw.replaceAll("\\s", "");
                cl = users.getString(2);
                users.moveToNext();

                cl = cl.replaceAll("\\s", "");
                if ((guname.equals(uname)) && (gpw.equals(pw))) {
                    status = 1;
                    break;
                }

            }

            if (status == 1 && (!uname.equals("000"))) {
                if (rem) {
                    db.execSQL("delete from auto");

                    db.execSQL("insert into auto values('" + uname + "','" + cl + "')");

                }
                else
                {
                    db.execSQL("delete from auto");
                    db.execSQL("insert into auto values('000','X')");
                }

                ename.setText("");
                epw.setText("");
                Intent tc = new Intent(this, home.class);
                tc.putExtra("user", uname);
                tc.putExtra("class", cl);
                this.startActivity(tc);
           /* if (cl.equals("A")) {
                Intent tc = new Intent(this, reminders4.class);
                tc.putExtra("user", uname);
                this.startActivity(tc);
            } else if (cl.equals("B")) {
                Intent tc = new Intent(this, reminders5.class);
                tc.putExtra("user", uname);
                this.startActivity(tc);
            } else if (cl.equals("C")) {
                Intent tc = new Intent(this, reminders.class);
                tc.putExtra("user", uname);
                this.startActivity(tc);
            }*/


            } else {
                Toast x;
                x = Toast.makeText(this, "Username/Password don't match!", Toast.LENGTH_SHORT);
                x.show();
            }
        }
        users.close();

    }

    public void create(View view){
        Intent tc = new Intent(this, create.class);
        this.startActivity(tc);

    }

    public void login()//Overloading
    {

        login(viewx);
    }

}

