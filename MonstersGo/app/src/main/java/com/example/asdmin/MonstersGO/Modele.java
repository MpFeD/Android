package com.example.asdmin.MonstersGO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by m2sar on 17/01/2017.
 */

/*all data needed for the application
a name
a map that correspond to the monster name and the number of time you have caught it
a score for the monsters you caught
a challenge for the number of monsters you cuaght during the challenge
 */
public class Modele {

    private String name;
    private Map<String, Integer> map;
    private int score;
    private int challenge;


    public Modele(String name) {
        this.name = name;
        map = new HashMap<>();
        score = 0;
        challenge=0;
    }

    public void ajouterMonsters(String monster){
        if (!map.containsKey(monster))
            map.put(monster,1);
        else
            for ( Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                Integer tab = entry.getValue();
                if(key.equals(monster))
                    map.put(key,tab+1);
            }
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public Map<String, Integer> getMap(){
        return map;
    }


    public String toString(){
        String res ="";
        for ( Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer tab = entry.getValue();
            res += "Monsters : " + key + " attrapés : " + tab+"\n";
        }
        return res;
    }

    public int getNbCaught(){
        int cpt=0;
        for ( Map.Entry<String, Integer> entry : map.entrySet()) {
            Integer tab = entry.getValue();
            cpt+=tab;
        }
        return cpt;
    }

    public int getNbCaughtForKey(String monster){
        for ( Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer tab = entry.getValue();
            if(key.equals(monster))
                return tab;
        }
        return 0;
    }

    public void setChallenge(int n){this.challenge=n;}

    public int getChallenge(){
        return this.challenge;
    }

}
