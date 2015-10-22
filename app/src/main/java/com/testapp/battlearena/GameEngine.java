package com.testapp.battlearena;

import java.util.Random;

public class GameEngine{

    private static final String[] skillName = {"Normal Attack", "Special Attack", "Block", "Heal"};
    private static final int[] skillDmg = {10, 20, 0, 15};
    protected Player player;
    protected Enemy enemy;
    private int pAtt, mAtt, pType, mType;
    private double pBonus, mBonus;
    private boolean playing, lost, won, healed = false;
    private Random rand;

    public GameEngine(){
        player = new Player();
        enemy = new Enemy();
        rand = new Random();
    }

    public void initPlayer(int playerID){
        player.initPlayer(playerID);
        pType = player.getType();
    }

    public void initEnemy(int enemyID){
        enemy.setKillCount(enemy.getKillCount() + 1);
        enemy.initEnemy(enemyID);
        mType = enemy.getType();
        compareType();
    }

    public void compareType(){
        if(pType == 2 && mType == 0){
            pBonus = 0.5;
            mBonus = -0.25;
        }
        else if(pType == 0 && mType == 2){
            pBonus = -0.25;
            mBonus = 0.5;
        }
        else if(pType < mType){
            pBonus = 0.5;
            mBonus = -0.25;
        }
        else if (pType > mType){
            pBonus = -0.25;
            mBonus = 0.5;
        }
        else{
            pBonus = mBonus = 0;
        }
    }

    public void playerAtt(int att){
        if(getEnemyHP() <= 50){
            mAtt = 2 + rand.nextInt(2);
        }
        else if(getEnemyHP() >= 80){
            mAtt = rand.nextInt(2);
        }
        else{
            mAtt = rand.nextInt(3);
        }
        pAtt = att;
        switch (att){
            case 0:
                setEnemyHP(getEnemyHP() - (skillDmg[pAtt] + (int) Math.round(skillDmg[pAtt] * pBonus)));
                break;
            case 1:
                setEnemyHP(getEnemyHP() - (skillDmg[pAtt] + (int) Math.round(skillDmg[pAtt] * pBonus)));
                break;
            case 2:
                setPlayerHP(getPlayerHP() - (skillDmg[mAtt] + (int) Math.round(skillDmg[mAtt] * pBonus)) / 2);
                break;
            case 3:
                if((getPlayerHP() + skillDmg[3]) >= 100) {
                    setPlayerHP(100);
                }
                else{
                    setPlayerHP(getPlayerHP() + skillDmg[3]);
                }
                break;
        }
    }

    public void enemyAtt(){
        int percent = rand.nextInt(4);
        if(getEnemyHP() <= 15){
            if(percent == 0 || percent == 1){
                setEnemyHP(getEnemyHP() + skillDmg[3]);
                healed = true;
            }
        }

        //If monster HP between 15 and 30, heal or block.
        else if(getEnemyHP() > 15 && getEnemyHP() <= 50){
            //For heal skill
            if(mAtt == 3){
                //If monster HP hits 0, it dies.
                if(getEnemyHP() <= 0){
                    setEnemyHP(0);
                }
                //Else heal attack
                else{
                    if(percent == 0){
                        setEnemyHP(getEnemyHP() + skillDmg[3]);
                        healed = true;
                    }
                }
            }
            //For block skill
            else{
                if(pAtt == 0 || pAtt == 1){
                    setEnemyHP(getEnemyHP() + (skillDmg[pAtt] + (int) Math.round(skillDmg[pAtt] * pBonus))/2);
                }
            }
        }

        //Use all skill except heal.
        else{
            switch (mAtt){
                case 0:
                    if(pAtt == 2){
                        setPlayerHP(getPlayerHP() - (skillDmg[mAtt] + (int) Math.round(skillDmg[mAtt] * mBonus))/2);
                    }
                    else{
                        setPlayerHP(getPlayerHP() - (skillDmg[mAtt]) + (int) Math.round(skillDmg[mAtt] * mBonus));
                    }
                    break;
                case 1:
                    if(pAtt == 2){
                        setPlayerHP(getPlayerHP() - (skillDmg[mAtt] + (int) Math.round(skillDmg[mAtt] * mBonus)) / 2);
                    }
                    else{
                        setPlayerHP(getPlayerHP() - (skillDmg[mAtt] + (int) Math.round(skillDmg[mAtt] * mBonus)));
                    }
                    break;
                case 2:
                    if(pAtt == 0 || pAtt == 1) {
                        setEnemyHP(getEnemyHP() + (skillDmg[pAtt] + (int) Math.round(skillDmg[pAtt] * pBonus))/ 2);
                    }
                    break;
            }
        }
    }

    public void checkStatus(){
        if(getPlayerHP() <= 0){
            lost = true;
            setPlayerHP(0);
        }
        else if(getEnemyHP() <= 0){
            won = true;
            setEnemyHP(0);
        }
        else if(getPlayerHP() <= 0 && getEnemyHP() <= 0) {
            won = true;
            setEnemyHP(0);
            //setPlayerHP();
        }
    }

    public String changeText(){
        String text = "";
        if(!lost && !won){
            switch (pAtt){
                case 0:
                    text = "You use " + skillName[pAtt] + " dealing " + (skillDmg[pAtt] + (int) Math.round(skillDmg[pAtt] * pBonus))
                            + " damage.";
                    break;
                case 1:
                    text = "You use " + skillName[pAtt] + " dealing " + (skillDmg[pAtt] + (int) Math.round(skillDmg[pAtt] * pBonus)) +
                            " damage.";
                    break;
                case 2:
                    text = "You use " + skillName[pAtt] + " negating " + (skillDmg[mAtt]+ (int) Math.round(skillDmg[mAtt] * mBonus))/2
                            + " damage.";
                    break;
                case 3:
                    if(getPlayerHP() >= 100){
                        text = "You already have full health. Cannot regenerate anymore.";
                    }
                    else{
                        text = "You use " + skillName[pAtt] + " regaining " + skillDmg[pAtt] + " heath.";
                    }
                    break;
            }

            switch(mAtt){
                case 0:
                    text += "\nEnemy use " + skillName[mAtt] + " dealing " + (skillDmg[mAtt] + (int) Math.round(skillDmg[mAtt] * mBonus))
                            + " damage.";
                    break;
                case 1:
                    text += "\nEnemy use " + skillName[mAtt] + " dealing " + (skillDmg[mAtt] + (int) Math.round(skillDmg[mAtt] * mBonus))
                            + " damage.";
                    break;
                case 2:
                    if(pAtt == 3){
                        text += "\nEnemy use " + skillName[mAtt] + " negating " + skillDmg[mAtt] + " damage.";
                    }
                    else{
                        text += "\nEnemy use " + skillName[mAtt] + " negating " +
                                (skillDmg[pAtt]+ (int) Math.round(skillDmg[pAtt] * pBonus))/2 + " damage.";
                    }
                    break;
                case 3:
                    if(healed){
                        text += "\nEnemy use " + skillName[mAtt] + " regaining " + skillDmg[mAtt] + " health.";
                    }
                    else{
                        text += "\nEnemy tried to heal but failed, regaining 0 health.";
                    }
                    healed = false;
                    break;
            }
        }
        else if(won){
            text = "Congrats! You have defeated the enemy!";
        }
        else{
            text = "You got rekt m8.";
        }
        return text;
    }

    public int getPlayerHP(){
        return player.getPlayerHP();
    }

    public int getEnemyHP(){
        return enemy.getEnemyHP();
    }

    public void setPlayerHP(int hp){
        player.setPlayerHP(hp);
    }

    public void setEnemyHP(int hp){
        enemy.setEnemyHP(hp);
    }

    public void setPlaying(boolean playing){
        this.playing = playing;
    }

    public boolean getPlaying(){
        return playing;
    }

    public void setLost(boolean lost){
        this.lost = lost;
    }

    public boolean getLost(){
        return lost;
    }

    public void setWon(boolean won){
        this.won = won;
    }

    public boolean getWon(){
        return won;
    }
}
