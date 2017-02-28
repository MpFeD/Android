package com.example.asdmin.MonstersGO;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asdmin.MonstersGO.R;

import java.util.Map;

/**
 * Created by Toty on 23/01/2017.
 */

public class FragmentMonsters extends Fragment{

    Modele modele;

    ImageView monster1;
    TextView monsternName1;
    View line1;
    ImageView monster2;
    TextView monsternName2;
    View line2;
    ImageView monster3;
    TextView monsternName3;
    View line3;
    ImageView monster4;
    TextView monsternName4;
    View line4;
    ImageView monster5;
    TextView monsternName5;
    View line5;

    TextView scoreTextView;
    TextView challengeTextView;

    //get the view from xml layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monsters_fragment, container, false);
        return view;
    }


    //instantiate data from the xml
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        monster1 = (ImageView) ((view.findViewById(R.id.monster1)));
        monsternName1 = (TextView)((view.findViewById(R.id.monsterName1)));
        line1 = ((view.findViewById(R.id.ligne1)));

        monster2 = (ImageView) ((view.findViewById(R.id.monster2)));
        monsternName2 = (TextView)((view.findViewById(R.id.monsterName2)));
        line2 = ((view.findViewById(R.id.ligne2)));

        monster3 = (ImageView) ((view.findViewById(R.id.monster3)));
        monsternName3 = (TextView)((view.findViewById(R.id.monsterName3)));
        line3 = ((view.findViewById(R.id.ligne3)));

        monster4 = (ImageView) ((view.findViewById(R.id.monster4)));
        monsternName4 = (TextView)((view.findViewById(R.id.monsterName4)));
        line4 = ((view.findViewById(R.id.ligne4)));

        monster5 = (ImageView) ((view.findViewById(R.id.monster5)));
        monsternName5 = (TextView)((view.findViewById(R.id.monsterName5)));
        line5 = ((view.findViewById(R.id.ligne5)));

        challengeTextView = (TextView)((view.findViewById(R.id.challenge)));
        scoreTextView = (TextView)((view.findViewById(R.id.score)));


        monster1.setVisibility(View.INVISIBLE);
        monsternName1.setVisibility(View.INVISIBLE);
        line1.setVisibility(View.INVISIBLE);

        monster2.setVisibility(View.INVISIBLE);
        monsternName2.setVisibility(View.INVISIBLE);
        line2.setVisibility(View.INVISIBLE);

        monster3.setVisibility(View.INVISIBLE);
        monsternName3.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);

        monster4.setVisibility(View.INVISIBLE);
        monsternName4.setVisibility(View.INVISIBLE);
        line4.setVisibility(View.INVISIBLE);

        monster5.setVisibility(View.INVISIBLE);
        monsternName5.setVisibility(View.INVISIBLE);
        line5.setVisibility(View.INVISIBLE);



        /* This loop is used to display the monsters in right order.
        If you get a monster A then a monster D, the result in the layout will be :
        A
        _
        D
        _
         */
        int id=0;
        int cpt=0;
        ImageView monsterTab[] = {monster1,monster2,monster3,monster4,monster5};
        TextView monsterNameTab[] = {monsternName1,monsternName2,monsternName3,monsternName4,monsternName5};
        View lineTab[]={line1,line2,line3,line4,line5};

        for ( Map.Entry<String, Integer> entry : modele.getMap().entrySet()) {
            String key = entry.getKey();

            monsterTab[cpt].setVisibility(View.VISIBLE);
            monsterNameTab[cpt].setVisibility(View.VISIBLE);
            monsterNameTab[cpt].setText(key.toString());
            lineTab[cpt].setVisibility(View.VISIBLE);

            if(key.equals(getResources().getString(R.string.pk_d))) {
                id = getResources().getIdentifier("dackass", "drawable", getActivity().getPackageName());
            }

            if(key.equals(getResources().getString(R.string.pk_f))){
                id = getResources().getIdentifier("fantomaxus", "drawable", getActivity().getPackageName());
            }

            if(key.equals(getResources().getString(R.string.pk_p))){
                id = getResources().getIdentifier("puzzleyes", "drawable", getActivity().getPackageName());
             }

            if(key.equals(getResources().getString(R.string.pk_r))){
                id = getResources().getIdentifier("racailleu", "drawable", getActivity().getPackageName());
            }

            if(key.equals(getResources().getString(R.string.pk_t))){
                id = getResources().getIdentifier("taupiqure", "drawable", getActivity().getPackageName());
            }

            monsterTab[cpt].setImageResource(id);
            cpt++;
        }

        challengeTextView.setText(getResources().getString(R.string.challengeValue)+" "+modele.getChallenge());
        scoreTextView.setText(getResources().getString(R.string.scoreValue)+" "+modele.getNbCaught());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //on create get the model
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modele = ((ActivityMap)getActivity()).getModele();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
