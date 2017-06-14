package com.example.juste.hangedman_game_v2.NoneImplemented;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.juste.hangedman_game_v2.HangedmanLogic;
import com.example.juste.hangedman_game_v2.NoneImplemented.DR_List_Fragment;
import com.example.juste.hangedman_game_v2.R;

/**
 * Created by Konstantin on 21-01-2016.
 */
public class ListWords_Activity extends ActionBarActivity implements DR_List_Fragment.OnDRListener{

    DR_List_Fragment fragment;
    HangedmanLogic logic = new HangedmanLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_fragment);
        fragment = (DR_List_Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.drlisteview);
    }


    // Now we can define the action to take in the activity when the fragment event fires
    @Override
    public void onDRListener() {
        DR_List_Fragment drFragment = (DR_List_Fragment)
                getSupportFragmentManager().findFragmentById(R.id.drlisteview);
        drFragment.populateDrList(logic.getpossibleWord());
        }
}