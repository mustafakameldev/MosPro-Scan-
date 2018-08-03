package com.mka.palnxtest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView lv;
    private ArrayList<Product> products;
    private DatabaseReference mainRef;
    private ProductAdapter adapter;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        lv = view.findViewById(R.id.lv_productsFragment);
        products = new ArrayList<>();
        mainRef = FirebaseDatabase.getInstance().getReference().child("aSalem");
        retrieve();

        adapter = new ProductAdapter(getContext(), android.R.layout.simple_list_item_1, products);
        lv.setAdapter(adapter);

        return view;
    }


    private void retrieve() {
        mainRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Float price = dataSnapshot.child("price").getValue(Float.class);
                Float units = dataSnapshot.child("units").getValue(Float.class);
                Float limit = dataSnapshot.child("limit").getValue(Float.class);

                Product product = new Product();
                product.setName(name);
                product.setLimitUnits(limit);
                product.setPrice(price);
                product.setUnits(units);
                product.setDatasnapShot(dataSnapshot.getKey());
                products.add(product);
                adapter.notifyDataSetChanged();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                convertView = inflater.inflate(R.layout.layout_product, null);
            }
            {

                TextView name = convertView.findViewById(R.id.tv_name_layoutProduct);
                TextView price = convertView.findViewById(R.id.tv_price_LayoutProduct);
                TextView units = convertView.findViewById(R.id.tv_units_LayoutProduct);
                TextView limit = convertView.findViewById(R.id.tv_limit_LayoutProduct);
                name.setText(products.get(position).getName());
                String mytext = String.valueOf(products.get(position).getPrice());
                price.setText(mytext);
                String unitss = String.valueOf(products.get(position).getUnits());
                units.setText(unitss);
                String limits = String.valueOf(products.get(position).getLimitUnits());
                limit.setText(limits);
                final Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("key1", products.get(position).getDatasnapShot());


                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(intent);
                    }
                });

            }
            return convertView;
        }
    }


}
