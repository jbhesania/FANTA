package com.example.android.projectfanta;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 5/18/2018.
 */

public class InstructionFragment extends Fragment {

    TextView yourUsername;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.instruction_fragment,container,false);

        yourUsername = (TextView) view.findViewById(R.id.step31);

        yourUsername.setText(Information.information.getInfo().getUserName());

        return view;
    }

}
