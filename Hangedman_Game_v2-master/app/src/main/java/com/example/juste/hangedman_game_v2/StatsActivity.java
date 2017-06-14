package com.example.juste.hangedman_game_v2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class StatsActivity extends Activity implements AdapterView.OnItemClickListener, Runnable{
    List<String> names;
    List<Integer> score;
    Firebase myFBRef = new Firebase("https://hangedman-game.firebaseio.com/");
    ListView stats;
    Map<String, Integer> highscoreList = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FireBase
        Firebase.setAndroidContext(this);
        myFBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    UserLogic userlogic = postsnapshot.getValue(UserLogic.class);
                    if(highscoreList.containsKey(userlogic.getName())&& highscoreList.get(userlogic.getName())>userlogic.getScore()){
                        Log.d("wrong wrong", "set the highest player");
                    }else{highscoreList.put(userlogic.getName(), userlogic.getScore());}
                }
                Map<String, Integer> map = sortByValues((HashMap) highscoreList);
                names = new ArrayList<String>(map.keySet());
                score = new ArrayList<Integer>(map.values());
                Collections.reverse(names);
                Collections.reverse(score);
                run();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }
    @Override
    public void run() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.content_stats, R.id.list_name,names){
            @Override
            public View getView(int position, View cachedView, ViewGroup parent){
                View view = super.getView(position, cachedView, parent);
                TextView list_score = (TextView) view.findViewById(R.id.list_score);
                list_score.setText(score.get(position).toString());
                return view;
            }};
        stats = new ListView(this);
        stats.setAdapter(adapter);
        stats.setOnItemClickListener(this);
        setContentView(stats);

    }


    //sort data from firebase
    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object person1, Object person2) {
                return ((Comparable) ((Map.Entry) (person1)).getValue()).compareTo(((Map.Entry) (person2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //NA
    }


}

