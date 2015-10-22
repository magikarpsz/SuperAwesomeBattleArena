package com.testapp.battlearena;


public class Player extends Skills{

    private static int playerHP;
    private int type;

    public Player(){}

    public void initPlayer(int playerID){
        playerHP = 100;
        switch(playerID){
            case 0:
                type = 0;
                break;
            case 1:
                type = 1;
                break;
            case 2:
                type = 2;
                break;
        }
    }

    public void setPlayerHP(int playerHP){
        this.playerHP = playerHP;
    }

    public int getPlayerHP(){
        return playerHP;
    }

    public int getType() {
        return type;
    }

}
