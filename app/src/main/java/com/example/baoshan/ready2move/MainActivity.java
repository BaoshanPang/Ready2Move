package com.example.baoshan.ready2move;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    ArrayList<Integer> playlist;
    MediaPlayer mediaPlayer;
    int delay = 1500;
    boolean inited = false;
    boolean isblack = true;

    public void list_update(boolean checked, int audio){
        if(checked)
            playlist.add(audio);
        else
            playlist.remove(playlist.indexOf(audio));
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
             // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chk_dirs:
                list_update(checked,R.raw.left);
                list_update(checked,R.raw.forward);
                list_update(checked,R.raw.right);
                list_update(checked,R.raw.backward);
                break;
            case R.id.chk_cut:
                list_update(checked,R.raw.cut);
                list_update(checked, R.raw.cuts2);
                break;
            case R.id.chk_scissor:
                list_update(checked, R.raw.scissor);
                list_update(checked, R.raw.scissor2);
                break;
            case R.id.chk_dropsd:
                list_update(checked, R.raw.dropshoulder);
                break;
            case R.id.chk_cruff:
                list_update(checked, R.raw.cruff);
                break;
            case R.id.chk_vpull:
                list_update(checked, R.raw.vpull);
                list_update(checked, R.raw.lpull);
                list_update(checked, R.raw.vthenl);
                break;
            case R.id.chk_fakeshoot:
                list_update(checked, R.raw.fakeshoot);
                list_update(checked, R.raw.fakepass);
                break;
            case R.id.chk_stopgo:
                list_update(checked, R.raw.stopgo);
                break;
            case R.id.chk_switchfeet:
                list_update(checked, R.raw.lfoot);
                list_update(checked, R.raw.rfoot);
                break;
            case R.id.chk_shoot:
                list_update(checked, R.raw.shoot);
                list_update(checked, R.raw.pass);
                break;
            case R.id.chk_others:
                list_update(checked, R.raw.rollstep);
                list_update(checked, R.raw.rchop);
                list_update(checked, R.raw.maradona);
                list_update(checked, R.raw.stepback);
                list_update(checked, R.raw.stepover);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        playlist = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et = findViewById(R.id.editText2);
        final Button bt_start = findViewById(R.id.bt_start);
        final android.support.constraint.ConstraintLayout back = findViewById(R.id.back);
        final Drawable background = back.getBackground();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                if(inited) {
                    mediaPlayer.reset();
                    inited  = false;
                }
                if(playlist.size() > 0) {
                    int idx = new Random().nextInt(playlist.size());
                    String text = et.getText().toString();
                    delay = Integer.parseInt(text);
                    mediaPlayer = MediaPlayer.create(context, playlist.get(idx));
                    inited = true;
                    mediaPlayer.start();

                    if(!isblack) {
                        back.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                        isblack = true;
                    } else {
                        isblack = false;
                        back.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    if(delay < mediaPlayer.getDuration())
                        delay = mediaPlayer.getDuration();
                    handler.postDelayed(this, delay);
                }

            }

        };
        et.setText(Integer.toString(delay));

        bt_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(bt_start.getText() == "START") {
                    bt_start.setText("STOP");
                    String text = et.getText().toString();
                    delay = Integer.parseInt(text);
                    handler.postDelayed(runnable, delay);  //for interval...
                } else {
                    handler.removeCallbacks(runnable);
                    bt_start.setText("START");
                    back.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }

            }
    });
    }
}
