package com.mka.palnxtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private EditText name, price, limit, units, desc;
    private Button btn_add;
    private String key;
    private DatabaseReference mainRef;
    private boolean there;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        key = getIntent().getExtras().getString("key");
        mainRef = FirebaseDatabase.getInstance().getReference().child("aSalem");
        declare();
        check();

    }

    void check() {
        mainRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("key").getValue(String.class).equals(key)) {
                        there = true;
                        break;
                    } else {
                        there = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void declare() {

        tv = (TextView) findViewById(R.id.tv_key_AddProductActivity);
        tv.setText(key);
        name = (EditText) findViewById(R.id.et_name_AddProduct);
        price = (EditText) findViewById(R.id.et_price_AddProduct);
        limit = (EditText) findViewById(R.id.et_limit_AddProduct);
        units = (EditText) findViewById(R.id.et_units_AddProduct);
        desc = (EditText) findViewById(R.id.et_desc_AddProductuctActivity);
        btn_add = (Button) findViewById(R.id.btn_addProduct);
        btn_add.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (there) {
            displayAlertMessage();
        }
        if (!there) {
            addProduct();
        }

    }

    public void displayAlertMessage() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(AddProductActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(AddProductActivity.this);
        }
        builder.setTitle("add ")
                .setMessage(R.string.isThere)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    void addProduct() {
        String nameS, descS, priceS, unitsS, limitS;
        float priceF, unitsF, limitF;
        nameS = name.getText().toString().trim();
        descS = desc.getText().toString().trim();
        priceS = price.getText().toString();
        limitS = limit.getText().toString();
        unitsS = units.getText().toString();
        limitF = Float.valueOf(limitS);
        unitsF = Float.valueOf(unitsS);
        priceF = Float.valueOf(priceS);
        if (!TextUtils.isEmpty(nameS) || !TextUtils.isEmpty(descS) || !TextUtils.isEmpty(priceS)
                || !TextUtils.isEmpty(unitsS) || !TextUtils.isEmpty(limitS)) {
            DatabaseReference push = mainRef.push();
            push.child("key").setValue(key);
            push.child("name").setValue(nameS);
            push.child("desc").setValue(descS);
            push.child("price").setValue(priceF);
            push.child("limit").setValue(limitF);
            push.child("units").setValue(unitsF);
            Toast.makeText(this, R.string.addDone, Toast.LENGTH_SHORT).show();
            name.setText(" ");
            price.setText(" ");
            desc.setText(" ");
            limit.setText(" ");
            units.setText(" ");
            startActivity(new Intent(AddProductActivity.this, ChooseActionActivity.class));
        } else {
            Toast.makeText(this, R.string.writeProduct, Toast.LENGTH_SHORT).show();
        }
    }


}
