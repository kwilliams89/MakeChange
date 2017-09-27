package com.example.kevin.cs3270a5;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    Integer timeRemaining;
    Integer correctCount;
    Double rand;
    Double _changeToMake;
    Double _changeSoFar;
    Double maxChange;
    SharedPreferences sp;
    ChangeActions changeActions;
    ChangeResults changeResults;
    ChangeButtons changeButtons;
    ChangeMax changeMax;

    String sharedPrefFile = "com.example.android.sharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _changeSoFar = 0.0;
        correctCount = 0;
        maxChange = 50.0;

        changeActions = new ChangeActions();
        changeButtons = new ChangeButtons();
        changeResults = new ChangeResults();
        changeMax = new ChangeMax();

        getSupportFragmentManager().beginTransaction().replace(R.id.containerChangeResults, changeResults, "CR").addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerChangeButtons, changeButtons, "CB").addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerChangeActions, changeActions, "CA").addToBackStack(null).commit();
        randomAmount();
    }


    public Double getChangeToMake(){
        return _changeToMake;
    }

    public void setMaxChange(double newMax){
        DecimalFormat df = new DecimalFormat("#.00");
        maxChange = Double.parseDouble(String.valueOf(df.format(newMax)));
    }

    public void showMainScreen(){
        getSupportFragmentManager().beginTransaction().hide(changeMax).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerChangeResults, changeResults, "CR").addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction().show(changeResults).commit();
        getSupportFragmentManager().beginTransaction().show(changeButtons).commit();
        getSupportFragmentManager().beginTransaction().show(changeActions).commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.mnuZeroCorrectCount:
                resetCount();
                return true;
            case R.id.mnuSetChangeMax:
                if (changeResults != null) {
                    changeResults.stopTimer();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.containerChangeResults, changeMax, "CM").addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().hide(changeResults).commit();
                getSupportFragmentManager().beginTransaction().hide(changeButtons).commit();
                getSupportFragmentManager().beginTransaction().hide(changeActions).commit();
                getSupportFragmentManager().beginTransaction().show(changeMax).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveState() {
        sp = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        ChangeResults crf = (ChangeResults) getSupportFragmentManager().findFragmentByTag("CR");
        if (crf != null) {
            timeRemaining = crf.getTimeRemaining();
        }
        spEditor.putInt("timeRemaining", timeRemaining);
        spEditor.putInt("correctCount", correctCount);
        spEditor.putLong("changeToMake", Double.doubleToRawLongBits(_changeToMake));
        spEditor.putLong("changeSoFar", Double.doubleToRawLongBits(_changeSoFar));
        spEditor.putLong("maxChange", Double.doubleToRawLongBits(maxChange));

        spEditor.commit();

    }


    public void restoreState() {
        sp = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        timeRemaining = sp.getInt("timeRemaining", 30);
        correctCount = sp.getInt("correctCount", 0);
        _changeToMake = Double.longBitsToDouble(sp.getLong("changeToMake", Double.doubleToLongBits(_changeToMake)));
        _changeSoFar = Double.longBitsToDouble(sp.getLong("changeSoFar", Double.doubleToLongBits(_changeSoFar)));
        maxChange = Double.longBitsToDouble(sp.getLong("maxChange", Double.doubleToLongBits(maxChange)));

        ChangeResults crf = (ChangeResults) getSupportFragmentManager().findFragmentByTag("CR");
        if (crf != null) {
            crf.stopTimer();
            crf.setChangeMade(_changeSoFar);
            crf.setChangeToMake();
            crf.setTimer(timeRemaining * 1000);
        }
        updateCountTimer();

    }

    public void calcChange(String buttonClicked){

        double buttonAmount;
        DecimalFormat df = new DecimalFormat("#.00");

        _changeSoFar = Double.parseDouble(String.valueOf(df.format(_changeSoFar)));

        if (buttonClicked.contains("$")) {
            buttonAmount = Double.valueOf(buttonClicked.replace("$", ""));
            buttonAmount = Double.parseDouble(String.valueOf(df.format(buttonAmount)));
            _changeSoFar += buttonAmount;
            _changeSoFar = Double.parseDouble(String.valueOf(df.format(_changeSoFar)));
        }
        else{
            buttonAmount = Double.valueOf(buttonClicked);
            buttonAmount = Double.parseDouble(String.valueOf(df.format(buttonAmount)));
            _changeSoFar += buttonAmount;
            _changeSoFar = Double.parseDouble(String.valueOf(df.format(_changeSoFar)));
        }
        if (_changeSoFar.equals(_changeToMake)){

            correctCount++;
            updateCountTimer();
            ChangeResults crf = (ChangeResults) getSupportFragmentManager().findFragmentByTag("CR");
            if (crf != null) {
                crf.setChangeMade(_changeSoFar);
            }
            OkayDialogFragment dialog = new OkayDialogFragment();
            dialog.setCancelable(false);
            dialog.setTitleMessage("You did it!", "You were able to make the correct change.");
            dialog.show(getSupportFragmentManager(), "Alert Win");


        }
        else if (_changeSoFar > _changeToMake){

            correctCount = 0;
            updateCountTimer();
            ChangeResults crf = (ChangeResults) getSupportFragmentManager().findFragmentByTag("CR");
            if (crf != null) {
                crf.setChangeMade(_changeSoFar);
            }
            OkayDialogFragment dialog = new OkayDialogFragment();
            dialog.setCancelable(false);
            dialog.setTitleMessage("That's too much change.", "You should try again.");
            dialog.show(getSupportFragmentManager(), "Alert Lose");
        }
        else {
            _changeSoFar = Double.parseDouble(String.valueOf(df.format(_changeSoFar)));
            ChangeResults crf = (ChangeResults) getSupportFragmentManager().findFragmentByTag("CR");
            if (crf != null) {
                crf.setChangeMade(_changeSoFar);
            }
        }

    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            showMainScreen();
        } else {
            super.onBackPressed();
        }
    }

    public void resetCount(){
        correctCount = 0;
        updateCountTimer();
    }

    public void updateCountTimer(){

        ChangeActions caf = (ChangeActions) getSupportFragmentManager().findFragmentByTag("CA");
        if (caf != null) {
            caf.setCorrectCount(correctCount);
        }
    }

    public void resetChange(){

        _changeSoFar = 0.00;
        ChangeResults crf = (ChangeResults) getSupportFragmentManager().findFragmentByTag("CR");
        if (crf != null) {
            crf.setChangeToMake();
            crf.setChangeMade(_changeSoFar);
            crf.stopTimer();
            crf.setTimer(30000);
        }
    }

    public void randomAmount(){
        rand = ThreadLocalRandom.current().nextDouble(0.0, maxChange);
        DecimalFormat df = new DecimalFormat("#.00");
        _changeToMake = Double.parseDouble(String.valueOf(df.format(rand)));
    }

}
