package com.app.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgViewDice;
    private ImageView mImgViewDice2;
    private TextView mTxtResult;
    private TextView mTextView;
    private int[] imageViewId = {R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4, R.id.imageButton5, R.id.imageButton6};
    private int[] resoucesId = {R.drawable.dice01, R.drawable.dice02, R.drawable.dice03, R.drawable.dice04, R.drawable.dice05, R.drawable.dice06};

    @Override
    public void onClick(View v)  {
        System.out.println(R.id.imageButton1==v.getId());
        for(int i = 0;i < imageViewId.length; i++){
            if (v.getId() == imageViewId[i]){
                mImgViewDice2.setImageResource(resoucesId[i]);
                mTextView.setText(String.format(Locale.TAIWAN,"%s%d",getString(R.string.dice_result),i+1));
                mImgViewDice2.setTag(i + 1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgViewDice = findViewById(R.id.imgViewDice);
        mImgViewDice2 = findViewById(R.id.imgViewDice2);
        mTxtResult = findViewById(R.id.txtResult);
        mTextView = findViewById(R.id.textView);
        Button mBtnRollDice = findViewById(R.id.btnRollDice);
        mBtnRollDice.setOnClickListener(btnRollDiceOnClick);
        for(int id : imageViewId)
            ((ImageView)findViewById(id)).setOnClickListener(this);
        mImgViewDice2.setTag(1);
        mTextView.setText(String.format(Locale.TAIWAN,"%s%d",getString(R.string.dice_result),(int)mImgViewDice2.getTag()));
    }

    private View.OnClickListener btnRollDiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation anim = new Animation(getApplicationContext(),mImgViewDice,R.drawable.anim_roll_dice);
            anim.setListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart() {
                    mTxtResult.setText(getString(R.string.dice_result));
                }

                @Override
                public void onAnimationEnd() {
                    int iRand = (int)(Math.random()*6+1);
                    mImgViewDice.setImageResource(resoucesId[iRand - 1]);
                    mTxtResult.setText(String.format("%s%s",getString(R.string.dice_result),String.valueOf(iRand)));
                    try {
                        int num = (int)mImgViewDice2.getTag();
                        if(num > iRand)
                            Toast.makeText(MainActivity.this, "玩家獲勝", Toast.LENGTH_SHORT).show();
                        else if(num == iRand)
                            Toast.makeText(MainActivity.this, "平手", Toast.LENGTH_SHORT).show();
                        if(num < iRand)
                            Toast.makeText(MainActivity.this, "電腦獲勝", Toast.LENGTH_SHORT).show();
                    }
                    catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "在耍白癡嗎 沒按骰子啦", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            anim.start(1200);
        }
    };

}
