package com.example.m2sar.david_toty_tp1;

import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import android.content.DialogInterface;
import android.content.Context;



public class MainActivity extends AppCompatActivity {

    final Context context = this;

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;

    ArrayList<Integer> Maliste;
    ArrayList<Integer> AuthorizedPosition; //Déplacements actuels autorisée

    ArrayList<Integer> AuthorizedPositionb1;
    ArrayList<Integer> AuthorizedPositionb2;
    ArrayList<Integer> AuthorizedPositionb3;
    ArrayList<Integer> AuthorizedPositionb4;
    ArrayList<Integer> AuthorizedPositionb5;
    ArrayList<Integer> AuthorizedPositionb6;
    ArrayList<Integer> AuthorizedPositionb7;
    ArrayList<Integer> AuthorizedPositionb8;
    ArrayList<Integer> AuthorizedPositionb9;
    ArrayList<Integer> AuthorizedPositionb10;

    Button bVide;
    TextView tv;

    /**
     * Sauvegarde toutes données utiles pour la rotation
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("b1", String.valueOf(b1.getText()));
        savedInstanceState.putString("b2", String.valueOf(b2.getText()));
        savedInstanceState.putString("b3", String.valueOf(b3.getText()));
        savedInstanceState.putString("b4", String.valueOf(b4.getText()));
        savedInstanceState.putString("b5", String.valueOf(b5.getText()));
        savedInstanceState.putString("b6", String.valueOf(b6.getText()));
        savedInstanceState.putString("b7", String.valueOf(b7.getText()));
        savedInstanceState.putString("b8", String.valueOf(b8.getText()));
        savedInstanceState.putString("b9", String.valueOf(b9.getText()));
        savedInstanceState.putInt("bv",bVide.getId());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView2);
        tv.setVisibility(View.INVISIBLE);

        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(startListener);
        b1.setBackgroundColor(Color.LTGRAY);

        b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(startListener);
        b2.setBackgroundColor(Color.LTGRAY);

        b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(startListener);
        b3.setBackgroundColor(Color.LTGRAY);

        b4 = (Button)findViewById(R.id.button4);
        b4.setOnClickListener(startListener);
        b4.setBackgroundColor(Color.LTGRAY);

        b5 = (Button)findViewById(R.id.button5);
        b5.setOnClickListener(startListener);
        b5.setBackgroundColor(Color.LTGRAY);


        b6 = (Button)findViewById(R.id.button6);
        b6.setOnClickListener(startListener);
        b6.setBackgroundColor(Color.LTGRAY);

        b7 = (Button)findViewById(R.id.button7);
        b7.setOnClickListener(startListener);
        b7.setBackgroundColor(Color.LTGRAY);

        b8 = (Button)findViewById(R.id.button8);
        b8.setOnClickListener(startListener);
        b8.setBackgroundColor(Color.LTGRAY);


        b9 = (Button)findViewById(R.id.button9);
        b9.setOnClickListener(startListener);
        b9.setBackgroundColor(Color.LTGRAY);

        AuthorizedPositionb1 = new ArrayList<>();
        AuthorizedPositionb2= new ArrayList<>();
        AuthorizedPositionb3= new ArrayList<>();
        AuthorizedPositionb4= new ArrayList<>();
        AuthorizedPositionb5= new ArrayList<>();
        AuthorizedPositionb6= new ArrayList<>();
        AuthorizedPositionb7= new ArrayList<>();
        AuthorizedPositionb8= new ArrayList<>();
        AuthorizedPositionb9= new ArrayList<>();
        AuthorizedPositionb10= new ArrayList<>();

        AuthorizedPositionb1.add(b2.getId());
        AuthorizedPositionb1.add(b4.getId());
        AuthorizedPositionb2.add(b1.getId());
        AuthorizedPositionb2.add(b5.getId());
        AuthorizedPositionb2.add(b3.getId());
        AuthorizedPositionb3.add(b2.getId());
        AuthorizedPositionb3.add(b6.getId());
        AuthorizedPositionb4.add(b1.getId());
        AuthorizedPositionb4.add(b5.getId());
        AuthorizedPositionb4.add(b7.getId());
        AuthorizedPositionb5.add(b2.getId());
        AuthorizedPositionb5.add(b4.getId());
        AuthorizedPositionb5.add(b6.getId());
        AuthorizedPositionb5.add(b8.getId());
        AuthorizedPositionb6.add(b3.getId());
        AuthorizedPositionb6.add(b5.getId());
        AuthorizedPositionb6.add(b9.getId());
        AuthorizedPositionb7.add(b4.getId());
        AuthorizedPositionb7.add(b8.getId());
        AuthorizedPositionb8.add(b7.getId());
        AuthorizedPositionb8.add(b5.getId());
        AuthorizedPositionb8.add(b9.getId());
        AuthorizedPositionb9.add(b8.getId());
        AuthorizedPositionb9.add(b6.getId());

        /*
         * Si une sauvegarde existe, la reprendre
         */
        if(savedInstanceState != null){
            b1.setText(savedInstanceState.getString("b1"));
            b2.setText(savedInstanceState.getString("b2"));
            b3.setText(savedInstanceState.getString("b3"));
            b4.setText(savedInstanceState.getString("b4"));
            b5.setText(savedInstanceState.getString("b5"));
            b6.setText(savedInstanceState.getString("b6"));
            b7.setText(savedInstanceState.getString("b7"));
            b8.setText(savedInstanceState.getString("b8"));
            b9.setText(savedInstanceState.getString("b9"));

            int n = savedInstanceState.getInt("bv");
            if(b1.getId()==n) {
                bVide = b1;
                AuthorizedPosition = AuthorizedPositionb1;
                b1.setBackgroundColor(Color.GREEN);
            }
            if(b2.getId()==n)
            {
                bVide = b2;
                AuthorizedPosition = AuthorizedPositionb2;
                b2.setBackgroundColor(Color.GREEN);
            }
            if(b3.getId()==n)
            {
                bVide = b3;
                AuthorizedPosition = AuthorizedPositionb3;
                b3.setBackgroundColor(Color.GREEN);
            }
            if(b4.getId()==n)
            {
                bVide = b4;
                AuthorizedPosition = AuthorizedPositionb4;
                b4.setBackgroundColor(Color.GREEN);
            }
            if(b5.getId()==n)
            {
                bVide = b5;
                AuthorizedPosition = AuthorizedPositionb5;
                b5.setBackgroundColor(Color.GREEN);
            }
            if(b6.getId()==n)
            {
                bVide = b6;
                AuthorizedPosition = AuthorizedPositionb6;
                b6.setBackgroundColor(Color.GREEN);
            }
            if(b7.getId()==n)
            {
                bVide = b7;
                AuthorizedPosition = AuthorizedPositionb7;
                b7.setBackgroundColor(Color.GREEN);
            }
            if(b8.getId()==n)
            {
                bVide = b8;
                AuthorizedPosition = AuthorizedPositionb8;
                b8.setBackgroundColor(Color.GREEN);
            }
            if(b9.getId()==n)
            {
                bVide = b9;
                AuthorizedPosition = AuthorizedPositionb9;
                b1.setBackgroundColor(Color.GREEN);
            }


        }else {
            bVide = b9;
            Maliste = new ArrayList<>();

            while (Maliste.size() < 8) {
                int n = 1 + (int) (Math.random() * 8);
                if (!Maliste.contains(n)) {
                    Maliste.add(n);
                }
            }

            b1.setText(String.valueOf(Maliste.get(0)));
            b2.setText(String.valueOf(Maliste.get(1)));
            b3.setText(String.valueOf(Maliste.get(2)));
            b4.setText(String.valueOf(Maliste.get(3)));
            b5.setText(String.valueOf(Maliste.get(4)));
            b6.setText(String.valueOf(Maliste.get(5)));
            b7.setText(String.valueOf(Maliste.get(6)));
            b8.setText(String.valueOf(Maliste.get(7)));
            b9.setText("");
            b9.setBackgroundColor(Color.GREEN);
            AuthorizedPosition = AuthorizedPositionb9;
        }
    }


    /*
     * Clic sur boutton
     */
    private OnClickListener startListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if ( AuthorizedPosition.contains(v.getId())){

                bVide.setText(((Button)v).getText());
                bVide.setBackgroundColor(Color.LTGRAY);

                ((Button) v).setText("");
                v.setBackgroundColor(Color.GREEN);

                bVide = ((Button) v);

                if(v.getId() == b1.getId())
                    AuthorizedPosition = AuthorizedPositionb1;

                if(v.getId() == b2.getId())
                    AuthorizedPosition = AuthorizedPositionb2;

                if(v.getId() == b3.getId())
                    AuthorizedPosition = AuthorizedPositionb3;

                if(v.getId() == b4.getId())
                    AuthorizedPosition = AuthorizedPositionb4;

                if(v.getId() == b5.getId())
                    AuthorizedPosition = AuthorizedPositionb5;

                if(v.getId() == b6.getId())
                    AuthorizedPosition = AuthorizedPositionb6;

                if(v.getId() == b7.getId())
                    AuthorizedPosition = AuthorizedPositionb7;

                if(v.getId() == b8.getId())
                    AuthorizedPosition = AuthorizedPositionb8;

                if(v.getId() == b9.getId())
                    AuthorizedPosition = AuthorizedPositionb9;

                tv.setVisibility(View.INVISIBLE);

                //Conditions de victoire
                if(b1.getText().equals("1") && b2.getText().equals("2") && b3.getText().equals("3") &&
                        b4.getText().equals("4") && b5.getText().equals("5") && b6.getText().equals("6") &&
                        b7.getText().equals("7") && b8.getText().equals("8") ) {

                    tv.setVisibility(View.VISIBLE);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(R.string.end);
                    alertDialogBuilder.setMessage(R.string.replay).setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Maliste = new ArrayList<>();
                                    while (Maliste.size() < 8) {
                                        int n = 1 + (int) (Math.random() * 8);
                                        if (!Maliste.contains(n)) {
                                            Maliste.add(n);
                                        }
                                    }

                                    b1.setText(String.valueOf(Maliste.get(0)));
                                    b1.setBackgroundColor(Color.LTGRAY);
                                    b2.setText(String.valueOf(Maliste.get(1)));
                                    b2.setBackgroundColor(Color.LTGRAY);
                                    b3.setText(String.valueOf(Maliste.get(2)));
                                    b3.setBackgroundColor(Color.LTGRAY);
                                    b4.setText(String.valueOf(Maliste.get(3)));
                                    b4.setBackgroundColor(Color.LTGRAY);
                                    b5.setText(String.valueOf(Maliste.get(4)));
                                    b5.setBackgroundColor(Color.LTGRAY);
                                    b6.setText(String.valueOf(Maliste.get(5)));
                                    b6.setBackgroundColor(Color.LTGRAY);
                                    b7.setText(String.valueOf(Maliste.get(6)));
                                    b7.setBackgroundColor(Color.LTGRAY);
                                    b8.setText(String.valueOf(Maliste.get(7)));
                                    b8.setBackgroundColor(Color.LTGRAY);
                                    b9.setText("");
                                    b9.setBackgroundColor(Color.GREEN);
                                    bVide = b9;
                                    AuthorizedPosition = AuthorizedPositionb9;

                                    tv.setVisibility(View.INVISIBLE);

                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MainActivity.this.finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

        }
    };

}
