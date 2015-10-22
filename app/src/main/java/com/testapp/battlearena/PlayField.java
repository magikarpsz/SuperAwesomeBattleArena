package com.testapp.battlearena;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class PlayField extends CharacterPage{

    private Button restart, next, quit, block, heal, attack, specialAtt;
    private TextView textP, playerHP, enemyHP;
    private ImageView playerIMG, enemyIMG;
    GameEngine gameEngine;
    private Random rand;
    private int[] icon = {R.drawable.swordman_2, R.drawable.axeman, R.drawable.spearman,
            R.drawable.swordman, R.drawable.roman_spearman};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_field);

        //Creating player, enemy, and random object
        rand = new Random();
        gameEngine = new GameEngine();

        //Creating Button, TextView, and ImageView object references
        restart = (Button) findViewById(R.id.restart);
        next = (Button) findViewById(R.id.next);
        quit = (Button) findViewById(R.id.quit);
        attack = (Button) findViewById(R.id.attack);
        block = (Button) findViewById(R.id.block);
        specialAtt = (Button) findViewById(R.id.specialAttack);
        heal = (Button) findViewById(R.id.heal);

        textP = (TextView) findViewById(R.id.textView);
        playerHP = (TextView) findViewById(R.id.playerHP);
        enemyHP = (TextView) findViewById(R.id.monsterHP);

        enemyIMG = (ImageView) findViewById(R.id.monster);
        playerIMG = (ImageView) findViewById(R.id.player);

        //Setup player and monster images
        Intent i = getIntent(); //Get player image
        playerIMG.setImageResource(icon[i.getIntExtra("playerIMG", 1)]);
        int value = rand.nextInt(icon.length);
        enemyIMG.setImageResource(icon[value]);

        //Initialize enemy
        gameEngine.initPlayer(icon[i.getIntExtra("playerIMG", 1)]);
        gameEngine.initEnemy(value);

        //Event listener for buttons
        restart.setOnClickListener(this);
        quit.setOnClickListener(this);
        attack.setOnClickListener(this);
        block.setOnClickListener(this);
        specialAtt.setOnClickListener(this);
        heal.setOnClickListener(this);
        next.setOnClickListener(this);

        //Set restart, quit, and next button to invisible.
        gameEngine.setPlaying(true);
        gameEngine.setLost(false);
        gameEngine.setWon(false);
        setView();
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.attack:
                gameEngine.playerAtt(0);
                gameEngine.enemyAtt();
                update();
                break;

            case R.id.specialAttack:
                gameEngine.playerAtt(1);
                gameEngine.enemyAtt();
                update();
                break;

            case R.id.block:
                gameEngine.playerAtt(2);
                gameEngine.enemyAtt();
                update();
                break;

            case R.id.heal:
                gameEngine.playerAtt(3);
                gameEngine.enemyAtt();
                update();
                break;

            case R.id.next:
                int value = rand.nextInt(icon.length);
                enemyIMG.setImageResource(icon[value]);
                gameEngine.initEnemy(value);
                gameEngine.setWon(false);
                gameEngine.setLost(false);
                textP.setText("Battle Start! Pick your move.");
                setView();
                break;

            case R.id.restart:
                gameEngine.setEnemyHP(100);
                gameEngine.setPlayerHP(100);
                gameEngine.setWon(false);
                gameEngine.setLost(false);
                textP.setText("Battle Start! Pick your move.");
                setView();
                break;

            case R.id.quit:
                gameEngine.setPlaying(false);
                Intent i = new Intent(PlayField.this, MainScreen.class);
                startActivity(i);
                finish();
                break;
            }
    }

    public void update(){
        gameEngine.checkStatus();
        textP.setText(gameEngine.changeText());
        setView();
    }

    public void setView(){
        if(gameEngine.getPlaying()){
            restart.setVisibility(View.GONE);
            quit.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            attack.setVisibility(View.VISIBLE);
            block.setVisibility(View.VISIBLE);
            specialAtt.setVisibility(View.VISIBLE);
            heal.setVisibility(View.VISIBLE);
            playerHP.setText("HP: " + gameEngine.getPlayerHP());
            enemyHP.setText("HP: " + gameEngine.getEnemyHP());

            if(gameEngine.getLost() || gameEngine.getWon()){
                attack.setVisibility(View.GONE);
                block.setVisibility(View.GONE);
                specialAtt.setVisibility(View.GONE);
                heal.setVisibility(View.GONE);
                if(gameEngine.getLost()){
                    restart.setVisibility(View.VISIBLE);
                    quit.setVisibility(View.VISIBLE);
                }
                else{
                    next.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
