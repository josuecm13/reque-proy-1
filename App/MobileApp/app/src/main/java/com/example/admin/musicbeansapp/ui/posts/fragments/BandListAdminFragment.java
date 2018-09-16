package com.example.admin.musicbeansapp.ui.posts.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.musicbeansapp.adapters.BandListAdapter;
import com.example.admin.musicbeansapp.R;

import java.util.ArrayList;
import java.util.List;

import musicbeans.entities.Band;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BandListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BandListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BandListAdminFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    List<Band> bandList;

    public BandListAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BandListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BandListFragment newInstance(String param1, String param2) {
        BandListFragment fragment = new BandListFragment();
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
        View view = inflater.inflate(R.layout.fragment_band_list, container, false);

        bandList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        filllist();

        BandListAdapter adapter = new BandListAdapter(bandList,true);

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void filllist() {
        bandList.add(new Band("a","letter a",(byte) 5,new byte[45],"sadha"));
        bandList.add(new Band("b","letter b",(byte) 5,new byte[45],"sadha"));
        bandList.add(new Band("c","letter c",(byte) 5,new byte[45],"sadha"));
        bandList.add(new Band("d","letter d",(byte) 5,new byte[45],"sadha"));
        bandList.add(new Band("e","letter e",(byte) 5,new byte[45],"sadha"));
        bandList.add(new Band("f","letter f",(byte) 5,new byte[45],"sadha"));
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
