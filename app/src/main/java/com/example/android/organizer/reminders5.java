package com.example.android.organizer;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.Date;


public class reminders5 extends AppCompatActivity {

    String user;
    View viewx;
    SQLiteDatabase db ;
    DecimalFormat df = new DecimalFormat("##.#");
    DateFormat dt = new SimpleDateFormat("dd:MM:yyyy:hh:mm:ss");
    Cursor x2;
    static int curt = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders5);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        user = extras.getString("user");
        user = user.replaceAll("\\s","");

        newcheck();
        init();
        disp();
        orientchangefix();
    }

    public void newcheck(){
        db.execSQL("CREATE TABLE IF NOT EXISTS newtable(status varchar);");
        Cursor x = db.rawQuery("select * from newtable where status = '"+user+"'", null);
        x.moveToFirst();
        int a = x.getCount();



        if(a<=0)  //new
        {
            db.execSQL("insert into rem values('" + user + "','Hello!','A')");
            db.execSQL("insert into rem values('" + user + "','Add reminders for different subjects!','B')");
            db.execSQL("insert into rem values('" + user + "','Long press to delete','C')");
            db.execSQL("insert into rem values('" + user + "','Rotate screen for dashboard mode','D')");
            db.execSQL("insert into rem values('" + user + "','In dashboard mode view subjectwise reminders, start any task specifying the minute allotment for it','D')");
            db.execSQL("insert into newtable values('" + user + "')");
        }

    }


    public void ct(View view){
        RadioButton b2 = (RadioButton) findViewById(R.id.rb2);
        if( b2.isChecked())  curt = 2;
        RadioButton b3 = (RadioButton) findViewById(R.id.rb3);
        if( b3.isChecked())  curt = 3;
        RadioButton b4 = (RadioButton) findViewById(R.id.rb4);
        if( b4.isChecked())  curt = 4;
        RadioButton b5 = (RadioButton) findViewById(R.id.rb5);
        if( b5.isChecked())  curt = 5;
        RadioButton b6 = (RadioButton) findViewById(R.id.rb6);
        if( b6.isChecked())  curt = 6;



    }
    void orientchangefix()
    {
        LinearLayout x9 = (LinearLayout) findViewById(R.id.x9);
        if(x9.getTag().equals("vertical")){
            Toast x;
        }
        else{
            Toast x;
            RadioButton b;


            if(curt==2)
                b = (RadioButton) findViewById(R.id.rb2);
            else if(curt==3)
                b = (RadioButton) findViewById(R.id.rb3);
            else if(curt==4)
                b = (RadioButton) findViewById(R.id.rb4);
            else if(curt==5)
                b = (RadioButton) findViewById(R.id.rb5);
            else if(curt==6)
                b = (RadioButton) findViewById(R.id.rb6);
            else
                b = (RadioButton) findViewById(R.id.rb2);

            b.setChecked(true);
            horizontalDisplay();


        }

    }
    public String fixout(String s)
    {
        s = s.replaceAll("'","<><>");
        return s;
    }
    public String fixin(String s)
    {
        s = s.replaceAll("<><>","'");
        return s;
    }


    public void finish(View view){
        finish();
    }
    public void init(){



        String a1, a2 , a3 , a4 , a5 , a6 ;
        RadioButton b2 = (RadioButton) findViewById(R.id.rb2);

        RadioButton b3 = (RadioButton) findViewById(R.id.rb3);

        RadioButton b4 = (RadioButton) findViewById(R.id.rb4);

        RadioButton b5 = (RadioButton) findViewById(R.id.rb5);

        RadioButton b6 = (RadioButton) findViewById(R.id.rb6);


        Cursor x = db.rawQuery("select * from sub where name = '"+user+"'", null);
        x.moveToFirst();

        a1=fixin(x.getString(7));
        a2=fixin(x.getString(8));
        a3=fixin(x.getString(9));
        a4=fixin(x.getString(10));
        a5=fixin(x.getString(11));

        b2.setText(a1);
        b3.setText(a2);
        b4.setText(a3);
        b5.setText(a4);
        b6.setText(a5);



    }



    public void horizontalDisplay(View view) {

        final View view1 = view;
        LinearLayout MainLL = (LinearLayout) findViewById(R.id.x1);

        String pri;
        boolean r1, r2, r3, r4, r5, r6;
        RadioButton b2 = (RadioButton) findViewById(R.id.rb2);
        r2 = b2.isChecked();
        RadioButton b3 = (RadioButton) findViewById(R.id.rb3);
        r3 = b3.isChecked();
        RadioButton b4 = (RadioButton) findViewById(R.id.rb4);
        r4 = b4.isChecked();
        RadioButton b5 = (RadioButton) findViewById(R.id.rb5);
        r5 = b5.isChecked();
        RadioButton b6 = (RadioButton) findViewById(R.id.rb6);
        r6 = b6.isChecked();
        if (r2)
            pri = "A";
        else if (r3)
            pri = "B";
        else if (r4)
            pri = "C";
        else if (r5)
            pri = "D";
        else
            pri = "E";
        Cursor x;
        RadioButton b1 = (RadioButton) findViewById(R.id.rb1);
        r1 = b1.isChecked();
        if(r1)
             x = db.rawQuery("select * from rem where name = '" + user + "' order by priority", null);
        else
         x = db.rawQuery("select * from rem where priority = '" + pri + "' and name = '"+user+"'", null);
        int n = x.getCount();
        String title;
        int act;
        x.moveToFirst();

        int id;
        final LinearLayout xy = MainLL;
        xy.removeAllViews();
        TextView ait = (TextView) findViewById(R.id.it);
        ait.setText(String.valueOf(n));


        for (int i = 0; i < n; i++) {
            title = fixin(x.getString(1));
            pri = x.getString(2);
            LinearLayout SubLL = new LinearLayout(this);
            final NumberPicker alarm = new NumberPicker(this);
            alarm.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
           // alarm.setBackgroundColor(Color.parseColor("#ffffff"));
            alarm.setMinValue(0);
            alarm.setMaxValue(300);
            final TextView alarmadd = new TextView(this);
            TextView t = new TextView(this);
            TextView spacer = new TextView(this);
            spacer.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,25));
            TextView vspacer = new TextView(this);
            vspacer.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
            vspacer.setText("  ");
            TextView aligner = new TextView(this);
            aligner.setText(" • ");
            aligner.setTextSize(25);
            aligner.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            SubLL.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            SubLL.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams til = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,8);
            LinearLayout.LayoutParams alm = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
            LinearLayout.LayoutParams almadd = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
            alm.gravity = Gravity.CENTER;
            alarm.setLayoutParams(alm);
            alarmadd.setLayoutParams(almadd);
            alarm.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            alarmadd.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            alarm.setScaleX(2.5f);
            alarm.setScaleY(2.5f);
            alarmadd.setText("Start");
            alarmadd.setBackgroundColor(Color.parseColor("#000000"));
            alarmadd.setBackgroundColor(Color.parseColor("#000000"));
            alarmadd.setTextSize(15);
            t.setTextSize(25);
            t.setLayoutParams(til);
            final String titl = title;
            SubLL.setWeightSum(10);
            t.setText( title);
            final String colour = pri;
            if (pri.equals("A"))
                pri = "#f44242";
            else if (pri.equals("B"))
                pri = "#f4c242";
            else if (pri.equals("C"))
                pri = "#23af0e";
            else if (pri.equals("D"))
                pri = "#42b9f4";
            else if (pri.equals("E"))
                pri = "#ea7ca2";
            else
                pri = "#f48835";
            t.setBackgroundColor(Color.parseColor(pri));
            aligner.setBackgroundColor(Color.parseColor(pri));
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SQLiteDatabase d = openOrCreateDatabase("myData", MODE_PRIVATE, null);
                    d.execSQL("delete from rem where title = '" + fixout(titl) + "' and priority like '%" + colour + "%' and name = '"+user+"'");
                    xy.removeAllViews();
                    horizontalDisplay(view1);
                    return true;
                }
            });
            x2 = db.rawQuery("select * from remtime where name = '"+user+"' and rem = '"+fixout(titl)+"'", null);
            x2.moveToFirst();
            int st = x2.getCount();
            if(st>0) {
                Date dnow = null, dget = null;
                long pending = 0;
                try {
                    dnow = dt.parse(String.valueOf(dt.format((new Date()))));
                    dget = dt.parse(x2.getString(2));
                    pending = dnow.getTime() - dget.getTime();
                } catch (Exception e) {
                    Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
                }
                int tott = (Integer.valueOf(x2.getString(3))) * 60 * 1000;
                int remaining = tott - (int) pending;
                if(remaining<0)
                    remaining=0;
                new CountDownTimer((remaining), 1000) {
                    public void onTick(long time) {
                        alarm.setValue(Integer.valueOf("" + ((time / (60 * 1000)) )));
                        if ((time % (60 * 1000)) / 1000 < 10)
                            alarmadd.setText("¤ " + ((time / (60 * 1000))) + ":0" + (time % (60 * 1000)) / 1000);
                        else
                            alarmadd.setText("¤ " + ((time / (60 * 1000))) + ":" + (time % (60 * 1000)) / 1000);
                        alarmadd.setTextSize(15);
                    }
                    public void onFinish() {
                        alarmadd.setTextSize(15);
                        alarmadd.setText("Time Up");
                        db.execSQL("delete from remtime where name = '" + user + "' and rem = '" + fixout(titl) + "'");
                    }
                }.start();
                ValueAnimator anim = ValueAnimator.ofArgb(Color.parseColor("#36bc46"), Color.parseColor("#f92c2c"));
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        alarmadd.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                });
                float fr = 1-((float)remaining)/((float)tott);
                anim.setCurrentFraction(fr);
                anim.setInterpolator(new AccelerateInterpolator());
                anim.setDuration(tott);
                anim.start();
            }

                alarmadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ValueAnimator anim = ValueAnimator.ofArgb(Color.parseColor("#36bc46"), Color.parseColor("#f92c2c"));
                        if (alarm.getValue() > 0&&(alarmadd.getText().equals("Start") || alarmadd.getText().equals("Time Up"))) {
                            db.execSQL("insert into remtime values('" + user + "','" + fixout(titl) + "','" + dt.format(new Date()) + "','" + (alarm.getValue() - 1) + "');");
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    alarmadd.setBackgroundColor((Integer) animation.getAnimatedValue());
                                }
                            });
                            anim.setInterpolator(new AccelerateInterpolator());
                            anim.start();
                            anim.setDuration((alarm.getValue() - 1) * 60 * 1000);
                            new CountDownTimer((alarm.getValue() - 1) * 60 * 1000, 1000) {
                                public void onTick(long time) {
                                    alarm.setValue(Integer.valueOf("" + ((time / (60 * 1000)) + 1)));
                                    if ((time % (60 * 1000)) / 1000 < 10)
                                        alarmadd.setText("¤ " + ((time / (60 * 1000))) + ":0" + (time % (60 * 1000)) / 1000);
                                    else
                                        alarmadd.setText("¤ " + ((time / (60 * 1000))) + ":" + (time % (60 * 1000)) / 1000);
                                    alarmadd.setTextSize(15);
                                }

                                public void onFinish() {
                                    alarmadd.setTextSize(15);
                                    alarmadd.setText("Time Up");
                                    db.execSQL("delete from remtime where name = '" + user + "' and rem = '" + fixout(titl) + "'");
                                }
                            }.start();
                        }
                    }
                });

            x.moveToNext();
            SubLL.addView(aligner);
            SubLL.addView(t);
            SubLL.addView(vspacer);
            SubLL.addView(alarm);
            SubLL.addView(alarmadd);
            MainLL.addView(SubLL);
            MainLL.addView(spacer);
        }
    }
    public void horizontalDisplay() {


        horizontalDisplay(viewx);

    }


    public void disp() {
        disp(viewx);

    }

    public void disp(View view) {
        int st = 0;

        Cursor x = db.rawQuery("select * from rem where name = '"+user+"' order by priority", null);
        LinearLayout MainLL = (LinearLayout) findViewById(R.id.x1);

        int n = x.getCount();
        String title, pri;
        int act;
        x.moveToFirst();

        int id;
        final LinearLayout xy = MainLL;
        xy.removeAllViews();
        TextView ait = (TextView) findViewById(R.id.it);
        ait.setText(String.valueOf(n));
        for (int i = 0; i < n; i++) {
            //id = Integer.valueOf(x.getString(0));

            //GET
            title = fixin(x.getString(1));
            pri = x.getString(2);
            LinearLayout SubLL = new LinearLayout(this);
            TextView alarm = new TextView(this);
            TextView t = new TextView(this);
            TextView aligner = new TextView(this);
            aligner.setText(" • ");
            aligner.setTextSize(25);
            aligner.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));




            //Declarations


            SubLL.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            SubLL.setOrientation(LinearLayout.HORIZONTAL);
           // t.setBackground(getResources().getDrawable(R.drawable.rounded) );

            LinearLayout.LayoutParams til = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,8);
            LinearLayout.LayoutParams alm = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
            TextView spacer = new TextView(this);
            spacer.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,25));
            TextView vspacer = new TextView(this);
            vspacer.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
            vspacer.setText("  ");


            alm.gravity = Gravity.CENTER;
            alarm.setLayoutParams(alm);
            // alarm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            alarm.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);

            alarm.setText("¤");

            //t.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,5));



            alarm.setBackgroundColor(Color.parseColor("#000000"));
            alarm.setTextSize(20);
            t.setTextSize(25);

            t.setLayoutParams(til);
            final String titl = title;
            SubLL.setWeightSum(8);




            //Dclarations
            //GO

            t.setText(title);

            final String colour = pri;
            if (pri.equals("A"))
                pri = "#f44242";
            else if (pri.equals("B"))
                pri = "#f4c242";
            else if (pri.equals("C"))
                pri = "#23af0e";
            else if (pri.equals("D"))
                pri = "#42b9f4";
            else
                pri = "#ea7ca2";

            t.setBackgroundColor(Color.parseColor(pri));
            aligner.setBackgroundColor(Color.parseColor(pri));

            //final int iid = id;



            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String a, b, c;

                    SQLiteDatabase d = openOrCreateDatabase("myData", MODE_PRIVATE, null);
                    d.execSQL("delete from rem where title = '" + fixout(titl) + "' and priority like '%" + colour + "%' and name = '"+user+"'");


                    xy.removeAllViews();
                    disp();

                    return true;
                }
            });
            alarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast x;
                    x = Toast.makeText(v.getContext(), "Timer W.I.P. ", Toast.LENGTH_SHORT);
                    x.show();

                }
            });
            x.moveToNext();
            //GO
            //SubLL.addView(alarm);
           // SubLL.addView(vspacer);
            SubLL.addView(aligner);
            SubLL.addView(t);

            MainLL.addView(SubLL);
            MainLL.addView(spacer);
        }


    }


    public void add(View view) {

        Cursor x = db.rawQuery("select * from rem where name = '"+user+"' order by priority ", null);
        EditText x1 = (EditText) findViewById(R.id.t1);
        String val = fixout(x1.getText().toString());
        x.moveToFirst();
        String pri;
        boolean r2, r3, r4, r5, r6;
        RadioButton b2 = (RadioButton) findViewById(R.id.rb2);
        r2 = b2.isChecked();
        RadioButton b3 = (RadioButton) findViewById(R.id.rb3);
        r3 = b3.isChecked();
        RadioButton b4 = (RadioButton) findViewById(R.id.rb4);
        r4 = b4.isChecked();
        RadioButton b5 = (RadioButton) findViewById(R.id.rb5);
        r5 = b5.isChecked();
        RadioButton b6 = (RadioButton) findViewById(R.id.rb6);
        r6 = b6.isChecked();

        if (r2)
            pri = "A";
        else if (r3)
            pri = "B";
        else if (r4)
            pri = "C";
        else if (r5)
            pri = "D";
        else
            pri = "E";




        String inp;
        if (!(val.equals(""))) {
            inp = "insert into rem values ('" + user + "','" + val + "','" + pri + "');";

            db.execSQL(inp);

            x1.setText("");
            disp();
        }

    }


}

/*
"@style/Theme.FullScreen"   @style/AppTheme
 */