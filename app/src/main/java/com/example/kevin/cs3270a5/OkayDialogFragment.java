package com.example.kevin.cs3270a5;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;


public class OkayDialogFragment extends DialogFragment {

    String title;
    String message;

    public OkayDialogFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Dialog dialog = builder.setMessage(message)
                .setCancelable(false)
                .setTitle(title)
                .setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity ma = (MainActivity) getActivity();
                                ma.resetChange();
                            }
                        }
                ).create();

        return dialog;
    }

    public void setTitleMessage(String myTitle, String myMessage){
        title = myTitle;
        message = myMessage;
    }

}
