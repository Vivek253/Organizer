package com.example.android.organizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class create extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        initComponents();

    }

    private void initComponents() {
        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        np.setMinValue(4);
        np.setMaxValue(6);
        np.setValue(5);
        final NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
        np2.setMinValue(1);
        np2.setMaxValue(99);
        np2.setValue(75);


        final EditText epw = (EditText) findViewById(R.id.pw);
        final EditText ename = (EditText) findViewById(R.id.name);
        final EditText epw2 = (EditText) findViewById(R.id.pw2);

        epw2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    np.requestFocus();
                    return true;

                }
                return false;
            }
        });
        epw.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    epw2.requestFocus();
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

    public boolean isAN(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    public void creater(View view) {
        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        final NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
        final EditText epw = (EditText) findViewById(R.id.pw);
        final EditText ename = (EditText) findViewById(R.id.name);
        final EditText epw2 = (EditText) findViewById(R.id.pw2);
        String name, pw, pw2;
        Cursor users = null;
        int npvalue, at;
        name = ename.getText().toString();
        name = name.replaceAll("\\s", "");
        pw = epw.getText().toString();
        pw = pw.replaceAll("\\s", "");
        pw2 = epw2.getText().toString();
        pw2 = pw2.replaceAll("\\s", "");

        npvalue = np.getValue();
        at = np2.getValue();

        String cat;
        if (npvalue == 4)
            cat = "A";
        else if (npvalue == 5)
            cat = "B";
        else
            cat = "C";


        //Correct match block
        if (pw.equals(pw2) && (!pw.equals("")) && (!name.equals(""))) {

            db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
            users = db.rawQuery("select * from user", null);
            String guname, gpw;
            int n = users.getCount();
            users.moveToFirst();
            int status = 1;                  //1 means no other user
            for (int i = 0; i < n; i++) {
                guname = users.getString(0);
                guname = guname.replaceAll("\\s", "");
                gpw = users.getString(1);
                gpw = gpw.replaceAll("\\s", "");
                users.moveToNext();

                if ((guname.equals(name)) || (gpw.equals(pw))) {
                    status = 0;
                    break;
                }


            }
            if (status == 1 && (!name.equals("000"))) {
                String estatement = "insert into user values('" + name + "','" + pw + "','" + cat + "'," + at + ");";
                Toast x;
                if ((!isAN(name)) || (!isAN(pw))) {

                    x = Toast.makeText(this, "Name/Password should not contai special characters!", Toast.LENGTH_SHORT);
                    x.show();

                } else if (npvalue == 4) {
                    Intent tc = new Intent(this, sub4.class);
                    tc.putExtra("sql", estatement);
                    tc.putExtra("us", name);
                    this.startActivity(tc);
                    finish();

                } else if (npvalue == 5) {
                    Intent tc = new Intent(this, sub5.class);
                    tc.putExtra("sql", estatement);
                    tc.putExtra("us", name);


                    this.startActivity(tc);
                    finish();
                } else {
                    Intent tc = new Intent(this, sub6.class);
                    tc.putExtra("sql", estatement);
                    tc.putExtra("us", name);
                    this.startActivity(tc);
                    finish();

                }

            } else {
                Toast x;
                x = Toast.makeText(this, "Username/password already taken!", Toast.LENGTH_SHORT);
                x.show();
            }


        } else if (pw.equals("") || name.equals("")) {
            Toast err;
            err = Toast.makeText(this, "Cant leave fields empty!", Toast.LENGTH_SHORT);
            err.show();
        } else {
            Toast mismatch;

            mismatch = Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT);
            mismatch.show();
        }
        users.close();
    }
}
