package com.example.android.organizer;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class home extends AppCompatActivity {

    String user,cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        user = extras.getString("user");
        cl = extras.getString("class");
        assert cl!=null;
        cl = cl.replaceAll("\\s","");
        user = user.replaceAll("\\s","");
        TextView uid = (TextView) findViewById(R.id.uid);
        if(user.length()>10)
          uid.setTextSize(10);
        uid.setText(getString(R.string.userdisp,user));
    }

    public void Profile(View view){
        Intent tc = new Intent(this, profile.class);
        tc.putExtra("user", user);
        tc.putExtra("class", cl);
        this.startActivity(tc);
    }

    public void attendance(View view){
        if (cl.equals("A")) {
            Intent tc = new Intent(this, attendance4.class);
            tc.putExtra("user", user);
            this.startActivity(tc);
        } else if (cl.equals("B")) {
            Intent tc = new Intent(this, attendance5.class);
            tc.putExtra("user", user);
            this.startActivity(tc);
        } else if (cl.equals("C")) {
            Intent tc = new Intent(this, attendance.class);
            tc.putExtra("user", user);
            this.startActivity(tc);

        }

    }

    public void Reminders(View view) {

        if (cl.equals("A")) {
            Intent tc = new Intent(this, reminders4.class);
            tc.putExtra("user", user);
            this.startActivity(tc);
        } else if (cl.equals("B")) {
            Intent tc = new Intent(this, reminders5.class);
            tc.putExtra("user", user);
            this.startActivity(tc);
        } else if (cl.equals("C")) {
            Intent tc = new Intent(this, reminders.class);
            tc.putExtra("user", user);
            this.startActivity(tc);

        }

    }
    public void logout(View view){
        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        db.execSQL("delete from auto");
        db.execSQL("insert into auto values('000','X')");

        Intent in = new Intent(this,MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(in);
    }
}

