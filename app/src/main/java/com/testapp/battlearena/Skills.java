package com.testapp.battlearena;

public class Skills {

    private static final String[] skillName = {"Tackle", "Headbutt", "Block", "Heal"};
    private static final int[] skillDmg = {10, 20, 0, 15};
    private int dmg = 0;

    public Skills(){

    }

    //Integer who indicates which player is attacking
    public int useSkill(int skill, int who){

        if(who == 1){
            return damageToEnemy(skill);
        }
        else{
            return 0; //damageToPlayer(skill);
        }
    }

    public int damageToPlayer(int skill){
        switch (skill){
            case 0:
                dmg = skillDmg[0];
                break;
            case 1:
                dmg = skillDmg[1];
                break;
            case 2:
                dmg = skillDmg[2];
                break;
            case 3:
                dmg = skillDmg[3];
                break;
        }

        return dmg;
    }

    public int damageToEnemy(int skill){
        switch (skill){
            case 0:
                dmg = skillDmg[0];
                break;
            case 1:
                dmg = skillDmg[1];
                break;
            case 2:
                dmg = skillDmg[2];
                break;
            case 3:
                dmg = skillDmg[3];
                break;
        }

        return dmg;
    }

    public String changeText(){

        return "";
    }
}
