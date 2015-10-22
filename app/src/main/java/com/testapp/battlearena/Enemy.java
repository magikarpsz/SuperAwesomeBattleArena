package com.testapp.battlearena;

public class Enemy{

    private static int enemyHP;
    private int type;
    private int killCount = -1;

    public Enemy(){}

    public void initEnemy(int enemyID){
        enemyHP = 100 + (killCount * 25);
        switch(enemyID){
            case 0:
                type = 0;
                break;
            case 1:
                type = 1;
                break;
            case 2:
                type = 2;
                break;
            case 3:
                type = 0;
                break;
            case 4:
                type = 2;
                break;
        }
    }

    public void setEnemyHP(int enemyHP){
        this.enemyHP = enemyHP;
    }

    public int getEnemyHP(){
        return enemyHP;
    }

    public int getType() {
        return type;
    }

    public void setKillCount(int killCount){
        this.killCount = killCount;
    }

    public int getKillCount(){
        return killCount;
    }

}
