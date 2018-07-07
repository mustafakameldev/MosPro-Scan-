package com.mka.palnxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class ButtonTest extends AppCompatActivity implements View.OnClickListener {
    Button hideButtons, hiddenButton;
    boolean hide = true;
    StickySwitch stickySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_test);
        hideButtons = (Button) findViewById(R.id.hideButtons);
        hiddenButton = (Button) findViewById(R.id.hiddenButton);
        hideButtons.setOnClickListener(this);
        hiddenButton.setOnClickListener(this);
        stickySwitch = (StickySwitch) findViewById(R.id.stickyBtnTestActivity);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                if (s.equals("clear")) {
                    Toast.makeText(ButtonTest.this, "i'm clear ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ButtonTest.this, "not clear", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == hiddenButton) {
            Toast.makeText(this, " i'm  hidden ", Toast.LENGTH_SHORT).show();
        } else if (v == hideButtons) {
            if (hide) {
                hiddenButton.setVisibility(View.GONE);
                hide = false;
            } else {
                hiddenButton.setVisibility(View.VISIBLE);
                hide = true;
            }

        }
    }
}
