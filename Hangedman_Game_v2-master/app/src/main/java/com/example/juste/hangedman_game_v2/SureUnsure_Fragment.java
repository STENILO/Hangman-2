package com.example.juste.hangedman_game_v2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class SureUnsure_Fragment extends DialogFragment{

    LayoutInflater inflater;
    View v;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.sure_unsure, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SUListener.onComplete("Yes");
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SUListener.onComplete("No");
            }
        });
        return builder.create();
    }

    public interface SureUnsureListenerDone {
        void onComplete(String word);
    }

    private SureUnsureListenerDone SUListener;

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.SUListener = (SureUnsureListenerDone)activity;
        }
        catch (final ClassCastException e){
            throw new ClassCastException(activity.toString() +"Implement listener");
        }
    }

}

