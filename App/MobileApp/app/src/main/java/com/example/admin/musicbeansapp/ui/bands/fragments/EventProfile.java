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

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.adapters.EventProfileAdapter;
import com.example.admin.musicbeansapp.ui.bands.InsertBand;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import musicbeans.dataaccess.Posts;
import musicbeans.entities.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EventProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EventProfile newInstance(String param1, String param2) {
        EventProfile fragment = new EventProfile();
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
        View view = inflater.inflate(R.layout.fragment_event_profile, container, false);

        List<Event> list = Posts.getEvents();//new ArrayList<>();

        /*java.sql.Date d= new Date(Calendar.getInstance().getTimeInMillis());
        list.add(new Event(d,"Parque 1","Title1","Descp","band1"));
        list.add(new Event(d,"Parque 1","Title1","Descp","band1"));
        list.add(new Event(d,"Parque 1","Title1","Descp","band1"));
        list.add(new Event(d,"Parque 1","Title1","Descp","band1"));*/

        RecyclerView myvr = (RecyclerView)view.findViewById(R.id.event_profile_recycler);
        EventProfileAdapter adapter = new EventProfileAdapter(getActivity(),list);
        myvr.setLayoutManager(new GridLayoutManager(getActivity(),3));
        myvr.setAdapter(adapter);

        FloatingActionButton btn = (FloatingActionButton)view.findViewById(R.id.newEvent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEvent(view);
            }
        });

        return view;
    }
    public void newEvent(View v)
    {
        try{
            Intent intent = new Intent(getActivity(),
                    AddEvent.class);
            startActivity(intent);
        }catch (Exception e)
        {
            System.err.println(e.toString());
        }


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
