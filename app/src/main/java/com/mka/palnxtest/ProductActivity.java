package com.mka.palnxtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameTv, priceTv, descTv, limitTv, unitsTv;
    private EditText nameEt, priceEt, descEt, limitEt, unitsEt;
    private String key;
    private DatabaseReference mainRef;
    private Product product;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        key = getIntent().getExtras().getString("key1");
        declare();
    }

    private void declare() {
        mainRef = FirebaseDatabase.getInstance().getReference().child("aSalem").child(key);

        nameTv = (TextView) findViewById(R.id.tv_name_ProductActivity);
        nameEt = (EditText) findViewById(R.id.et_editName_ProductActivity);
        priceTv = (TextView) findViewById(R.id.tv_price_ProductActivity);
        priceEt = (EditText) findViewById(R.id.et_editPrice_ProductActivity);
        descTv = (TextView) findViewById(R.id.tv_desc_ProductActivity);
        descEt = (EditText) findViewById(R.id.et_editDesc_ProductActivity);
        limitTv = (TextView) findViewById(R.id.tv_limit_ProductActivity);
        limitEt = (EditText) findViewById(R.id.et_editLimit_ProductActivity);
        unitsTv = (TextView) findViewById(R.id.tv_units_ProductActivity);
        unitsEt = (EditText) findViewById(R.id.et_editUnits_ProductActivity);
        editBtn = (Button) findViewById(R.id.btn_edit_ProductActivity);
        editBtn.setOnClickListener(this);
        nameEt.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
        limitEt.setVisibility(View.GONE);
        priceEt.setVisibility(View.GONE);
        unitsEt.setVisibility(View.GONE);
        descEt.setVisibility(View.GONE);
        retrieve();
    }

    void retrieve() {
        mainRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Float price = dataSnapshot.child("price").getValue(Float.class);
                Float units = dataSnapshot.child("units").getValue(Float.class);
                Float limit = dataSnapshot.child("limit").getValue(Float.class);
                String desc = dataSnapshot.child("desc").getValue(String.class);
                product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setLimitUnits(limit);
                product.setDesc(desc);
                product.setUnits(units);
                nameTv.setText(name);
                descTv.setText(desc);
                unitsTv.setText(String.valueOf(units));
                priceTv.setText(String.valueOf(price));
                limitTv.setText(String.valueOf(limit));

                nameEt.setText(name);
                descEt.setText(desc);
                unitsEt.setText(String.valueOf(units));
                priceEt.setText(String.valueOf(price));
                limitEt.setText(String.valueOf(limit));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delet_action) {
            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.edit_action) {
            nameEt.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.VISIBLE);
            limitEt.setVisibility(View.VISIBLE);
            priceEt.setVisibility(View.VISIBLE);
            unitsEt.setVisibility(View.VISIBLE);
            descEt.setVisibility(View.VISIBLE);


            nameTv.setVisibility(View.GONE);
            limitTv.setVisibility(View.GONE);
            priceTv.setVisibility(View.GONE);
            unitsTv.setVisibility(View.GONE);
            descTv.setVisibility(View.GONE);

        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == editBtn) {

            String nameStr, priceStr, unitStr, descStr, limitStr;
            nameStr = nameEt.getText().toString();
            descStr = descEt.getText().toString();
            priceStr = priceEt.getText().toString();
            limitStr = limitEt.getText().toString();
            unitStr = unitsEt.getText().toString();

            Float units = Float.valueOf(unitStr);
            Float limit = Float.valueOf(limitStr);
            Float price = Float.valueOf(priceStr);
            if (TextUtils.isEmpty(nameStr) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(descStr)
                    || TextUtils.isEmpty(unitStr) || TextUtils.isEmpty(limitStr)) {
                Toast.makeText(this, R.string.writeProduct, Toast.LENGTH_SHORT).show();
            } else {
                mainRef.child("name").setValue(nameStr);
                mainRef.child("price").setValue(price);
                mainRef.child("limit").setValue(limit);
                mainRef.child("units").setValue(units);
                mainRef.child("desc").setValue(descStr);
                Toast.makeText(this, R.string.editDone, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProductActivity.this, ChooseActionActivity.class));
            }


        }
    }
}
