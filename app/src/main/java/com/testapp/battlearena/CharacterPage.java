package com.testapp.battlearena;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterPage extends Activity implements View.OnClickListener{

    PlayField playField;
    Button swordman, axeman, spearman, enter;
    ImageView charIMG;
    TextView charDesc;
    int charID, enemyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_page);

        playField = new PlayField();
        swordman = (Button) findViewById(R.id.swordman);
        axeman = (Button) findViewById(R.id.axeman);
        spearman = (Button) findViewById(R.id.spearman);
        enter = (Button) findViewById(R.id.enter);
        charIMG = (ImageView) findViewById(R.id.charImage);
        charDesc = (TextView) findViewById(R.id.charDescription);

        swordman.setOnClickListener(this);
        axeman.setOnClickListener(this);
        spearman.setOnClickListener(this);
        enter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.swordman:
                charID = 0;
                charIMG.setImageResource(R.drawable.swordman_2);
                charDesc.setText("Man with a sword.\nStrong against: Axeman\nWeak against: Spearman");
                break;
            case R.id.axeman:
                charID = 1;
                charIMG.setImageResource(R.drawable.axeman);
                charDesc.setText("Man with an axe.\nStrong against: Spearman\nWeak against: Swordman");
                break;
            case R.id.spearman:
                charID = 2;
                charIMG.setImageResource(R.drawable.spearman);
                charDesc.setText("Man with a spear.\nStrong against: Swordman\nWeak against: Axeman");
                break;
            case R.id.enter:
                Intent i = new Intent(CharacterPage.this, PlayField.class);
                //Pass in the chosen character as a string
                i.putExtra("playerIMG", charID);
                startActivity(i);
                finish();
                break;
        }
    }
}
