package com.example.kevin.cs3270a5;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMax extends DialogFragment {

    Button btnSave;
    View rootView;
    EditText edtMaxChange;

    public ChangeMax() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_change_max, container, false);


        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        edtMaxChange = (EditText) rootView.findViewById(R.id.edtMaxChange);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(), getView());
                MainActivity ma = (MainActivity) getActivity();
                String newMax = edtMaxChange.getText().toString();
                if (!newMax.isEmpty()) {
                    double maxChange = Double.parseDouble(newMax);
                    ma.setMaxChange(maxChange);
                    ma.resetChange();
                    ma.showMainScreen();
                }

            }
        });

        return rootView;
    }

    // Implemented from answer by user @rmirabelle on stackflow thread - https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
