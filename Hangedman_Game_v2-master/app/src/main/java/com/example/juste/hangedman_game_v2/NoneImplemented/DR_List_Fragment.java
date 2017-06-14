package com.example.juste.hangedman_game_v2.NoneImplemented;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juste.hangedman_game_v2.R;

import java.util.ArrayList;


/**
 * Created by Konstantin on 17-01-2016.
 */

public class DR_List_Fragment extends Fragment {
    LayoutInflater inflater;
    ListView listView;
    ArrayList<String> drList;
    OnDRListener dCallBack;
    private OnDRListener mListener;

    // Interface to callback communication to fragments
    public interface OnDRListener{
        public void onDRListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Sikrer at aktiviteten der skal callbackes fra har implementeret interface
        try{
            dCallBack = (OnDRListener) getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " shall implements In DR Listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View drFragmentView = inflater.inflate(R.layout.list_fragment, container, false);
        dCallBack.onDRListener();
        listView = (ListView) drFragmentView.findViewById(R.id.drlisteview);
        listView.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, drList));

        return drFragmentView;
    }

    // Metode til fragmentcontroller for at give fragmentet en arrayliste af ord
    public void populateDrList(ArrayList<String> list){
        drList = list;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnDRListener) {
            mListener = (OnDRListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }
}