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


public class LimitFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView lv;
    private ArrayList<Product> products, limits;
    private DatabaseReference mainRef;
    private ProductAdapter adapter;

    public LimitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LimitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LimitFragment newInstance(String param1, String param2) {
        LimitFragment fragment = new LimitFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_limit, container, false);
        lv = view.findViewById(R.id.lv_limitFragment);
        products = new ArrayList<>();
        limits = new ArrayList<>();
        mainRef = FirebaseDatabase.getInstance().getReference().child("aSalem");
        retrieve();

        adapter = new ProductAdapter(getContext(), android.R.layout.simple_list_item_1, limits);
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
                //   products.add(product);
                if (limit >= units) {
                    limits.add(product);
                }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
