package com.mka.palnxtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class ChooseActionActivity extends AppCompatActivity implements View.OnClickListener, ProductsFragment.OnFragmentInteractionListener
        , LimitFragment.OnFragmentInteractionListener {
    private Button scanBtn, productsBtn, shortsBtn, payBtn;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        scanBtn = (Button) findViewById(R.id.scanBtn_ChooseActivity);
        productsBtn = (Button) findViewById(R.id.productsBtn_ChooseActivity);
        shortsBtn = (Button) findViewById(R.id.shortsBtn_ChooseActivity);
        scanBtn.setOnClickListener(this);
        productsBtn.setOnClickListener(this);
        shortsBtn.setOnClickListener(this);
        payBtn = (Button) findViewById(R.id.btn_pay_ChooseActivity);
        payBtn.setOnClickListener(this);
        container = (FrameLayout) findViewById(R.id.container);
    }

    @Override
    public void onClick(View view) {
        if (view == scanBtn) {
            startActivity(new Intent(ChooseActionActivity.this, MainActivity.class));
        } else if (view == productsBtn) {
            ProductsFragment fragment = new ProductsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();


        } else if (view == shortsBtn) {
            LimitFragment fragment = new LimitFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        } else if (view == payBtn) {
            startActivity(new Intent(ChooseActionActivity.this, PayActivity.class));


        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
