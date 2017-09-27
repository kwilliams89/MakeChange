package com.example.kevin.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeActions extends Fragment {

    View rootView;
    Button btnNewAmount;
    Button btnStartOver;
    TextView txvCorrectChangeCount;

    public ChangeActions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_actions, container, false);
        btnNewAmount = (Button) rootView.findViewById(R.id.btnNewAmount);
        btnStartOver = (Button) rootView.findViewById(R.id.btnStartOver);
        txvCorrectChangeCount = (TextView) rootView.findViewById(R.id.txvCorrectChangeCount);

        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) getActivity();
                ma.resetChange();
            }
        });

        btnNewAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) getActivity();
                ma.randomAmount();
                ma.resetChange();
            }
        });

        return rootView;
    }


    public void setCorrectCount(Integer count){
        txvCorrectChangeCount.setText(String.valueOf(count));
    }

}
