package com.testapp.battlearena;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class PlayField extends ActionBarActivity implements View.OnClickListener{

    Button restart, next, quit, block, heal, tackle, headbutt;
    TextView textP, textM, playerHP, monsterHP;
    ImageView monsterIMG;
    private static int pHP = 100;
    private static int mHP = 100;
    private String names[] = {"Tackle", "Headbutt", "Block", "Heal"};
    private int[] skillDmg = {10, 20, 0, 10};
    private int[] icon = {R.drawable.spearman, R.drawable.swordman, R.drawable.mustache_man, R.drawable.mustache_man_icon};
    private int recoil = 10, monsterDmg, killCount = 1;
    private Random rand;
    boolean isPlaying, lost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_field);

        monsterIMG = (ImageView) findViewById(R.id.monster);
        textP = (TextView) findViewById(R.id.textView);
        textM = (TextView) findViewById(R.id.textView2);
        playerHP = (TextView) findViewById(R.id.playerHP);
        monsterHP = (TextView) findViewById(R.id.monsterHP);
        restart = (Button) findViewById(R.id.restart);
        next = (Button) findViewById(R.id.next);
        quit = (Button) findViewById(R.id.quit);
        tackle = (Button) findViewById(R.id.tackle);
        block = (Button) findViewById(R.id.block);
        headbutt = (Button) findViewById(R.id.headbutt);
        heal = (Button) findViewById(R.id.heal);
        rand = new Random();

        restart.setOnClickListener(this);
        quit.setOnClickListener(this);
        tackle.setOnClickListener(this);
        block.setOnClickListener(this);
        headbutt.setOnClickListener(this);
        heal.setOnClickListener(this);
        next.setOnClickListener(this);

        restart.setVisibility(View.GONE);
        quit.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        playerHP.setText("HP: " + pHP);
        monsterHP.setText("HP: " + mHP);
    }

    @Override
    public void onClick(View v){
            switch(v.getId()){
                case R.id.tackle:
                    mHP = mHP - skillDmg[0];
                    monsterAtt(0);
                    changeText(0, monsterDmg);
                    checkStatus();
                    break;

                case R.id.headbutt:
                    mHP = mHP - skillDmg[1];
                    pHP -= recoil;
                    monsterAtt(1);
                    changeText(1, monsterDmg);
                    checkStatus();
                    break;

                case R.id.block:
                    monsterAtt(2);
                    changeText(2, monsterDmg);
                    checkStatus();
                    break;

                case R.id.heal:
                    pHP = pHP + skillDmg[3];
                    if(pHP > 100)
                        pHP = 100;
                    monsterAtt(3);
                    changeText(3, monsterDmg);
                    checkStatus();
                    break;

                case R.id.next:
                    mHP = killCount * 100;
                    monsterIMG.setImageResource(icon[rand.nextInt(4)]);
                    next.setVisibility(View.GONE);
                    tackle.setVisibility(View.VISIBLE);
                    block.setVisibility(View.VISIBLE);
                    headbutt.setVisibility(View.VISIBLE);
                    heal.setVisibility(View.VISIBLE);
                    monsterHP.setText("HP: " + mHP);
                    playerHP.setText("HP: " + pHP);
                    textP.setText("Battle Start! Pick your move.");
                    break;

                case R.id.restart:
                    mHP = 100;
                    pHP = 100;
                    killCount = 1;
                    restart.setVisibility(View.GONE);
                    quit.setVisibility(View.GONE);
                    tackle.setVisibility(View.VISIBLE);
                    block.setVisibility(View.VISIBLE);
                    headbutt.setVisibility(View.VISIBLE);
                    heal.setVisibility(View.VISIBLE);
                    monsterHP.setText("HP: " + mHP);
                    playerHP.setText("HP: " + pHP);
                    textP.setText("Battle Start! Pick your move.");
                    textM.setText("");
                    break;

                case R.id.quit:
                    finish();
                    break;
            }
    }

    public void checkStatus(){
        if(mHP <= 0 || pHP <= 0){
            if(mHP <= 0 && pHP <= 0){
                mHP = 0;
                pHP += skillDmg[monsterDmg];
                textP.setText("Congrats! You have defeated the monster.");
                textM.setText("");
                next.setVisibility(View.VISIBLE);
            }
            else if(mHP <= 0){
                mHP = 0;
                killCount++;
                textP.setText("Congrats! You have defeated the monster.");
                textM.setText("");
                next.setVisibility(View.VISIBLE);
            }
            else if(pHP <= 0){
                pHP = 0;
                textP.setText("You got rekt.");
                textM.setText("");
                restart.setVisibility(View.VISIBLE);
                quit.setVisibility(View.VISIBLE);
            }

            tackle.setVisibility(View.GONE);
            block.setVisibility(View.GONE);
            headbutt.setVisibility(View.GONE);
            heal.setVisibility(View.GONE);
        }

        monsterHP.setText("HP: " + mHP);
        playerHP.setText("HP: " + pHP);
    }

    public void monsterAtt(int playerAtt){

        //If monster HP <= 15, heal only
        if(mHP <= 15){
            monsterDmg = 3;
            mHP += skillDmg[monsterDmg];
        }

        //If monster HP between 15 and 30, heal or block.
        else if(mHP > 15 && mHP <= 30){
            monsterDmg = 2 + rand.nextInt(2);

            //For heal skill
            if(monsterDmg == 3){
                //If monster HP hits 0, it dies.
                if(mHP <= 0){
                    mHP = 0;
                }
                //Else heal attack
                else{
                    mHP += skillDmg[monsterDmg];
                }
            }
            //For block skill
            else{
                if(playerAtt == 0){
                    mHP += skillDmg[playerAtt]/2 ;
                }
                else if(playerAtt == 1){
                    mHP += skillDmg[playerAtt]/2;
                }
            }
        }

        //If monster HP is between 21 and 50, use all abilities.
        else if(mHP > 30 && mHP <= 50){
            //Use headbutt if player HP <= 20
            if(pHP <= 20){
                monsterDmg = 1;
                pHP -= skillDmg[monsterDmg];
                mHP -= recoil;
            }

            //Otherwise, use any of the four skills
            else{
                monsterDmg = rand.nextInt(4);
                switch (monsterDmg){
                    case 0:
                        if(playerAtt == 2){
                            pHP -= skillDmg[monsterDmg]/2;
                        }
                        else{
                            pHP -= skillDmg[monsterDmg];
                        }
                        break;
                    case 1:
                        if(playerAtt == 2){
                            pHP -= skillDmg[monsterDmg]/2;
                            mHP -= recoil;
                        }
                        else{
                            pHP -= skillDmg[monsterDmg];
                            mHP -= recoil;
                        }
                        break;
                    case 2:
                        if(playerAtt == 0){
                            mHP += skillDmg[0]/2;
                        }
                        else if(playerAtt == 1){
                            mHP += skillDmg[1]/2;
                        }
                        break;
                    case 3:
                        mHP += skillDmg[monsterDmg];
                }
            }
        }

        //If monster HP greater than 50, disable block and heal ability.
        else if (mHP > 50){
            if(pHP <= 20){
                monsterDmg = 1;
                pHP -= skillDmg[monsterDmg];
                mHP -= recoil;
            }
            else{
                monsterDmg = rand.nextInt(2);
                switch (monsterDmg){
                    case 0:
                        if(playerAtt == 2){
                            pHP -= skillDmg[monsterDmg]/2;
                        }
                        else{
                            pHP -= skillDmg[monsterDmg];
                        }
                        break;
                    case 1:
                        if(playerAtt == 2){
                            pHP -= skillDmg[monsterDmg]/2;
                            mHP -= recoil;
                        }
                        else{
                            pHP -= skillDmg[monsterDmg];
                            mHP -= recoil;
                        }
                        break;
                }
            }
        }
    }

    public void changeText(int pMove, int mMove){
        switch(pMove){
            case 0:
                textP.setText("You use " + names[pMove] + " dealing " + skillDmg[pMove] + " damage.");
                break;
            case 1:
                textP.setText("You use " + names[pMove] + " dealing " + skillDmg[pMove] +
                        " damage and taking " + recoil + " damage.");
                break;
            case 2:
                textP.setText("You use " + names[pMove] + " negating " + skillDmg[mMove]/2 + " damage.");
                break;
            case 3:
                if(pHP == 100){
                    textP.setText("You already have full health. Cannot regenerate anymore.");
                }
                else{
                    textP.setText("You use " + names[pMove] + " regaining " + skillDmg[pMove] + " health.");
                }
                break;
        }

        switch(mMove){
            case 0:
                textM.setText("Enemy use " + names[mMove] + " dealing " + skillDmg[mMove] + " damage.");
                break;
            case 1:
                textM.setText("Enemy use " + names[mMove] + " dealing " + skillDmg[mMove]
                        + " damage and taking " + recoil + " damage.");
                break;
            case 2:
                if(pMove == 3){
                    textM.setText("Enemy use " + names[mMove] + " negating " + skillDmg[mMove] + " damage.");
                }
                else{
                    textM.setText("Enemy use " + names[mMove] + " negating " + skillDmg[pMove]/2 + " damage.");
                }
                break;
            case 3:
                textM.setText("Enemy use " + names[mMove] + " regaining " + skillDmg[mMove] + " health.");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_field, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
