package com.example.android.organizer;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Math.min;

public class sub5 extends AppCompatActivity {

    String exec,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub5);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        exec = extras.getString("sql");
        user = extras.getString("us");
        assert user != null;
        user = user.replaceAll("\\s","");

    }

    public String fixout(String s)
    {
        s = s.replaceAll("'","<><>");
        return s;
    }

    public boolean check(String a, String b,String c,String d,String e){//RETURN 1 if SAFE

        if((a.equals(b))||(a.equals(c))||(a.equals(d))||(a.equals(e)))
            return false;
        if((b.equals(c))||(b.equals(d))||(b.equals(e)))
            return false;
        if((c.equals(d))||(c.equals(e)))
            return false;
        return !d.equals(e);


    }


    public void createsubs(View view){

        int l;

        EditText s1 = (EditText) findViewById(R.id.sub1);
        EditText s2 = (EditText) findViewById(R.id.sub2);
        EditText s3 = (EditText) findViewById(R.id.sub3);
        EditText s4 = (EditText) findViewById(R.id.sub4);
        EditText s5 = (EditText) findViewById(R.id.sub5);

        EditText a1 = (EditText) findViewById(R.id.abb1);
        EditText a2 = (EditText) findViewById(R.id.abb2);
        EditText a3 = (EditText) findViewById(R.id.abb3);
        EditText a4 = (EditText) findViewById(R.id.abb4);
        EditText a5 = (EditText) findViewById(R.id.abb5);


        String is1,is2,is3,is4,is5,ia1,ia2,ia3,ia4,ia5;
        is1 = s1.getText().toString();
        is1=fixout(is1);
        is2 = s2.getText().toString();
        is2=fixout(is2);
        is3 = s3.getText().toString();
        is3=fixout(is3);
        is4 = s4.getText().toString();
        is4=fixout(is4);
        is5 = s5.getText().toString();
        is5=fixout(is5);



        ia1 = a1.getText().toString();           //CHecking abbre length
        l = min(3,ia1.length());
        ia1 = ia1.substring(0,l);
        ia1=fixout(ia1);

        ia2 = a2.getText().toString();
        l = min(3,ia2.length());
        ia2 = ia2.substring(0,l);
        ia2=fixout(ia2);

        ia3 = a3.getText().toString();
        l = min(3,ia3.length());
        ia3 = ia3.substring(0,l);
        ia3=fixout(ia3);

        ia4 = a4.getText().toString();
        l = min(3,ia4.length());
        ia4 = ia4.substring(0,l);
        ia4=fixout(ia4);

        ia5 = a5.getText().toString();
        l = min(3,ia5.length());
        ia5 = ia5.substring(0,l);
        ia5=fixout(ia5);



        SQLiteDatabase db = openOrCreateDatabase("myData", MODE_PRIVATE, null);
        if(!((is1.equals(""))||(is2.equals(""))||(is3.equals(""))||(is4.equals(""))||(is5.equals(""))||(ia1.equals(""))||(ia2.equals(""))||(ia3.equals(""))||(ia4.equals(""))||(ia5.equals(""))))
        {
            if((check(is1,is2,is3,is4,is5))&&(check(ia1,ia2,ia3,ia4,ia5))) {
                String sqlst = "insert into sub values('" + user + "','" + is1 + "','" + is2 + "','" + is3 + "','" + is4 + "','" + is5 + "','" + "" + "','" + ia1 + "','" + ia2 + "','" + ia3 + "','" + ia4 + "','" + ia5 + "','" + "" + "')";
                db.execSQL(sqlst);
                db.execSQL("insert into att values('" + user + "','" + is1 + "',0,0)");
                db.execSQL("insert into att values('" + user + "','" + is2 + "',0,0)");
                db.execSQL("insert into att values('" + user + "','" + is3 + "',0,0)");
                db.execSQL("insert into att values('" + user + "','" + is4 + "',0,0)");
                db.execSQL("insert into att values('" + user + "','" + is5 + "',0,0)");
                db.execSQL(exec);
                db.execSQL("delete from auto");
                db.execSQL("insert into auto values('000','X')");

                Intent tc = new Intent(this, MainActivity.class);

                //tc.putExtra("user",user);
                this.startActivity(tc);
                finish();
            }
            else{
                Toast x;
                x = Toast.makeText(this , "Names and abbreviations must be unique!",Toast.LENGTH_SHORT);
                x.show();
            }
        }

        else {
            Toast x;
            x = Toast.makeText(this , "Cannot set fields empty!",Toast.LENGTH_SHORT);
            x.show();
        }




    }
}
