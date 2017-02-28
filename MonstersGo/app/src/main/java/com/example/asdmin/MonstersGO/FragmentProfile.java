package com.example.asdmin.MonstersGO;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.asdmin.MonstersGO.R;


/**
 * Created by m2sar on 16/01/2017.
 */

public class FragmentProfile extends Fragment {
    private Modele modele;
    private EditText usernameEdit;


    //get the view from xml layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        return view;
    }

    //instantiate data from the xml
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameEdit = (EditText)((view.findViewById(R.id.usernameEdit)));
        modele = ((ActivityMap)getActivity()).getModele();
        usernameEdit.setText(modele.getName());
        usernameEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                modele.setName(usernameEdit.getText().toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




    @Override
    public void onDetach() {
        super.onDetach();
    }

}
