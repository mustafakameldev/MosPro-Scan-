package com.mka.palnxtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaidListActivity extends AppCompatActivity {
    private ArrayList<String> keys;
    private ListView lv;
    private DatabaseReference mainRef;
    private ArrayList<Product> products;
    private ProductAdapter adapter;
    private ArrayList<Float> numbers;
    private TextView tv_total;
    private Button btn_sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid);
        keys = getIntent().getExtras().getStringArrayList("keys");
        mainRef = FirebaseDatabase.getInstance().getReference().child("aSalem");
        lv = (ListView) findViewById(R.id.lv_paidList);
        tv_total = (TextView) findViewById(R.id.tv_total_PaidActivity);
        products = new ArrayList<>();
        numbers = new ArrayList<>();
        btn_sum = (Button) findViewById(R.id.btn_sum_PaidActivity);
        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_total.setText(sum().toString());

            }
        });
        retrieve();
        adapter = new ProductAdapter(this, android.R.layout.simple_list_item_1, products);
        lv.setAdapter(adapter);


    }

    private void retrieve() {
        mainRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.child("key").getValue(String.class);
                Product product = new Product();
                for (int i = 0; i < keys.size(); i++) {
                    if (key.equals(keys.get(i))) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        Float price = dataSnapshot.child("price").getValue(Float.class);
                        Float units = dataSnapshot.child("units").getValue(Float.class);
                        Float limit = dataSnapshot.child("limit").getValue(Float.class);
                        product.setName(name);
                        product.setLimitUnits(limit);
                        product.setPrice(price);
                        product.setUnits(units);
                        products.add(product);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    Double sum() {
        Double sum = 0.0;
        for (Float i : numbers) {

            sum = sum + i;
        }
        return sum;
    }

    public class ProductAdapter extends ArrayAdapter {
        LayoutInflater inflater;
        ArrayList<Product> products;

        public ProductAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList list) {
            super(context, resource, list);
            this.products = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (inflater == null) {
                inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_paid_item, null);
            }
            {
                TextView name = convertView.findViewById(R.id.tv_name_layoutPaid);
                TextView price = convertView.findViewById(R.id.tv_price_LayoutPaid);
                TextView units = convertView.findViewById(R.id.tv_units_LayoutPaid);
                final EditText paid = convertView.findViewById(R.id.editTxt_NoPaid_LayoutPaid);
                name.setText(products.get(position).getName());
                String mytext = String.valueOf(products.get(position).getPrice());
                price.setText(mytext);
                String unitss = String.valueOf(products.get(position).getUnits());
                units.setText(unitss);

                final float p = products.get(position).getPrice();

                Button btn = convertView.findViewById(R.id.btn_done_PiadLayout);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Integer nu = Integer.valueOf(paid.getText().toString());
                        numbers.add(nu * p);
                    }
                });
            }
            return convertView;
        }
    }

}
