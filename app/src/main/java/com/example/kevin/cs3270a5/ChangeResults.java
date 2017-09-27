package com.example.kevin.cs3270a5;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeResults extends Fragment {

    View rootView;
    TextView txvChangeToMake;
    TextView txvTimeRemaining;
    TextView txvChangeTotalSoFar;
    CountDownTimer myTimer;
    OkayDialogFragment dialog;
    Integer timeRemaining;

    public ChangeResults() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_change_results, container, false);

        dialog = new OkayDialogFragment();
        txvChangeToMake = (TextView) rootView.findViewById(R.id.txvChangeToMake);
        txvChangeTotalSoFar = (TextView) rootView.findViewById(R.id.txvChangeTotalSoFar);
        txvTimeRemaining = (TextView) rootView.findViewById(R.id.txvTimeRemaining);
        timeRemaining = 0;

        setChangeToMake();
        if (myTimer == null) {
            setTimer(30000);
        }
        else{
            stopTimer();
        }
        return rootView;


    }

    public void setTimer(Integer milliseconds){
            myTimer = new CountDownTimer(milliseconds, 1000) {

                public void onTick(long millisUntilFinished) {
                    txvTimeRemaining.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {

                    dialog.setCancelable(false);
                    dialog.setTitleMessage("You took too long.", "You should try again.");
                    if (getFragmentManager() != null) {
                        dialog.show(getFragmentManager(), "Alert Time");
                    }
                    MainActivity ma = (MainActivity) getActivity();
                    if (ma != null) {
                        ma.resetCount();
                    }

                }
            }.start();

    }

    public int getTimeRemaining(){
        if (txvTimeRemaining.getText().toString() != "") {
            timeRemaining = Integer.parseInt(txvTimeRemaining.getText().toString());
        }
        return timeRemaining;
    }

    public void stopTimer(){
        if (myTimer != null)
            myTimer.cancel();
    }

    public void setChangeMade(double changeSoFar){
        txvChangeTotalSoFar.setText("$" + String.valueOf(changeSoFar));
    }

    public void setChangeToMake(){

        MainActivity ma = (MainActivity) getActivity();
        Double changeToMake =  ma.getChangeToMake();
        txvChangeToMake.setText("$" + String.valueOf(changeToMake));
    }

    @Override
    public void onPause(){
        super.onPause();
        myTimer = null;
        MainActivity ma = (MainActivity) getActivity();
        ma.saveState();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity ma = (MainActivity) getActivity();
        ma.restoreState();
    }
}
