package com.example.kevin.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeButtons extends Fragment {

    View rootView;
    Button btnFiftyDollar;
    Button btnTwentyDollar;
    Button btnTenDollar;
    Button btnFiveDollar;
    Button btnOneDollar;
    Button btnFiftyCent;
    Button btnQuarter;
    Button btnDime;
    Button btnNickel;
    Button btnPenny;

    public ChangeButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_buttons, container, false);

        btnFiftyDollar = (Button) rootView.findViewById(R.id.btnFiftyDollar);
        btnTwentyDollar = (Button) rootView.findViewById(R.id.btnTwentyDollar);
        btnTenDollar = (Button) rootView.findViewById(R.id.btnTenDollar);
        btnFiveDollar = (Button) rootView.findViewById(R.id.btnFiveDollar);
        btnOneDollar = (Button) rootView.findViewById(R.id.btnOneDollar);
        btnFiftyCent = (Button) rootView.findViewById(R.id.btnFiftyCent);
        btnQuarter = (Button) rootView.findViewById(R.id.btnQuarter);
        btnDime = (Button) rootView.findViewById(R.id.btnDime);
        btnNickel = (Button) rootView.findViewById(R.id.btnNickel);
        btnPenny = (Button) rootView.findViewById(R.id.btnPenny);

        btnFiftyDollar.setOnClickListener(buttonPress);
        btnTwentyDollar.setOnClickListener(buttonPress);
        btnTenDollar.setOnClickListener(buttonPress);
        btnFiveDollar.setOnClickListener(buttonPress);
        btnOneDollar.setOnClickListener(buttonPress);
        btnFiftyCent.setOnClickListener(buttonPress);
        btnQuarter.setOnClickListener(buttonPress);
        btnDime.setOnClickListener(buttonPress);
        btnNickel.setOnClickListener(buttonPress);
        btnPenny.setOnClickListener(buttonPress);

        return rootView;
    }

    View.OnClickListener buttonPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button clickedButton = (Button) v;
            String buttonCaption = clickedButton.getText().toString();
            calcChange(buttonCaption);
        }
    };

    private void calcChange(String buttonCaption){

        MainActivity ma = (MainActivity) getActivity();
        ma.calcChange(buttonCaption);

    }

}