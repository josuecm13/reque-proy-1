package com.example.admin.musicbeansapp.ui.bands.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.adapters.EventProfileAdapter;
import com.example.admin.musicbeansapp.adapters.ProductProfileAdapter;
import com.example.admin.musicbeansapp.ui.bands.AddProduct;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import musicbeans.dataaccess.ImageManager;
import musicbeans.entities.Event;
import musicbeans.entities.Product;
import musicbeans.entities.Sesion;
import musicbeans.entities.ViewBag;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean client=false;
    private OnFragmentInteractionListener mListener;

    private  ProductProfileAdapter _adapter;

    public ProductProfile() {
        // Required empty public constructor
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductProfile newInstance(String param1, String param2) {
        ProductProfile fragment = new ProductProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_profile, container, false);

        List<Product> list;
        if(!client)
            list = musicbeans.dataaccess.Product.getProducts();
        else
            list = musicbeans.dataaccess.Product.getProducts(Sesion.getInstance().getBand());
       /* list.add(new Product(1,"A",12));
        list.add(new Product(1,"B",11));
        list.add(new Product(1,"C",2));
        list.add(new Product(1,"D",34));
        list.add(new Product(1,"E",90));I*/
        //ImageManager manager = new ImageManager(uri,img);
        RecyclerView myvr = (RecyclerView)view.findViewById(R.id.product_profile_recycler);
        ProductProfileAdapter adapter = new ProductProfileAdapter(getActivity(),list);
        _adapter=adapter;

        myvr.setLayoutManager(new GridLayoutManager(getContext(),3));
        myvr.setAdapter(adapter);

        FloatingActionButton btn = (FloatingActionButton)view.findViewById(R.id.newProduct);
        if(client)
        {
            btn.setVisibility(View.INVISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newProduct(view);
            }
        });
        //manager.execute();
        return view;
    }
    public void newProduct(View v)
    {

        Intent intent = new Intent(getActivity(),
                AddProduct.class);
        ViewBag.put("product",_adapter);
        startActivity(intent);
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
}
