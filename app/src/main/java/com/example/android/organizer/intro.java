package com.example.android.organizer;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class intro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        final int delay = 2250;
        final TextView logo = (TextView) findViewById(R.id.logo);
        ValueAnimator anim = ValueAnimator.ofArgb(logo.getCurrentTextColor(),Color.parseColor("#497aaf"));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                logo.setTextColor((Integer) animation.getAnimatedValue());
            }
        });
        anim.start();
        anim.setDuration(1000);

        ValueAnimator anim2 = ValueAnimator.ofInt(140,0);


        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                logo.setGravity(Gravity.CENTER_HORIZONTAL);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.setMargins(0, (Integer) animation.getAnimatedValue(),0,0);
                logo.setLayoutParams(params);
            }
        });
        anim2.start();
        anim2.setDuration(1000);



       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                TextView id = (TextView) findViewById(R.id.name);

                id.setText(R.string.app_name);
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                TextView vers = (TextView) findViewById(R.id.ver);

                vers.setText(R.string.version);
            }
        },1500);


        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView logo = (TextView) findViewById(R.id.logo);
                logo.setTextColor(Color.parseColor("#d8d8d8"));






            }
        },delay*2);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainintent = new Intent(intro.this,MainActivity.class);
                intro.this.startActivity(mainintent);
                intro.this.finish();

            }
        },delay);

    }
}
