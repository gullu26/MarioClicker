package com.example.sudarshanseshadri.marioclicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class SuperMarioFragment extends Fragment {

    private static final float DISTANCE_JUMP = 100;
    private static final long TIME_JUMP = 100;
    private static final long COIN_TIME = 300;
    private static final float COIN_RISE = 150;
    private static final int PASSIVE_INCOME_TIMES_PER_SECOND = 10;

    ImageView marioImage;
    ImageView questionBlockImage;
    ConstraintLayout constraintLayout;

    LinearLayout linearLayout;

    TextView counter;
    AtomicFloat counterNum=new AtomicFloat();
    AtomicFloat passiveCoinsPerSecond = new AtomicFloat();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_super_mario,null);

        super.onCreate(savedInstanceState);
        constraintLayout = rootView.findViewById(R.id.id_layout);
        marioImage = rootView.findViewById(R.id.id_imageView_mario);
        questionBlockImage = rootView.findViewById(R.id.id_imageView_coinBlock);
        linearLayout = rootView.findViewById(R.id.id_linearLayout);
        counter = rootView.findViewById(R.id.id_textView_counter);


        marioImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoins(1);
                jump();
            }
        });

        addHelperThreads();
        return rootView;
    }



    private void addHelperThreads() {
        Thread passiveCoins = new Thread("Passive Coin Production")
        {
            public void run()
            {
                while (true)
                {
                    counterNum.addAndGet(passiveCoinsPerSecond.get()/PASSIVE_INCOME_TIMES_PER_SECOND);
                    try {
                        sleep(1000/PASSIVE_INCOME_TIMES_PER_SECOND);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        };

        passiveCoins.start();


        Thread updateCount = new Thread("UpdateCoins")
        {
            public void run()
            {

                while (true)
                {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            String coinsOrcoin = counterNum.getInt() == 1 ? " Coin" : " Coins";
                            counter.setText(counterNum.getInt() + coinsOrcoin);
                        }
                    });

                    try {
                        sleep(1000/PASSIVE_INCOME_TIMES_PER_SECOND);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        };
        updateCount.start();


    }

    @Override
    public String toString() {
        return "Super Mario";
    }


    private void addCoins(final int numberOfCoins) {

        Thread t1 = new Thread("Increment")
        {
            public void run()
            {
                counterNum.addAndGet(numberOfCoins);

                //passiveCoinsPerSecond.addAndGet(numberOfCoins);
            }

        };
        t1.start();





    }

    private void animateCoin() {


        final ImageView coinInCode = new ImageView(getContext());
        coinInCode.setId(View.generateViewId());
        coinInCode.setImageResource(R.drawable.coin);
        //ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams((int)(questionBlockImage.getWidth()*0.8), (int)(questionBlockImage.getWidth()*0.8));
        ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams(100, 100);

        coinInCode.setLayoutParams(p);
        constraintLayout.addView(coinInCode);






        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(coinInCode.getId(), ConstraintSet.BOTTOM, linearLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(coinInCode.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(coinInCode.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

        constraintSet.applyTo(constraintLayout);

        AnimationSet animationSetText = new AnimationSet(false);
        AnimationSet animationSetCoin = new AnimationSet(false);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());

        int distanceOffset=(int) (Math.random()*50 -25);
        Animation coinRise = new TranslateAnimation(coinInCode.getTranslationX()+distanceOffset, coinInCode.getTranslationX()+distanceOffset, coinInCode.getTranslationY(), coinInCode.getTranslationY()-COIN_RISE);



        animationSetText.addAnimation(fadeOut);

        animationSetCoin.addAnimation(coinRise);
        animationSetCoin.addAnimation(fadeOut);

        animationSetText.setDuration(COIN_TIME);
        animationSetCoin.setDuration(COIN_TIME);


        coinInCode.setAnimation(animationSetCoin);

        animationSetCoin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //constraintLayout.removeView(textViewInCode);
                coinInCode.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void jump()
    {

        final Animation animUp = new TranslateAnimation(marioImage.getTranslationX(), marioImage.getTranslationX(), marioImage.getTranslationY(), marioImage.getTranslationY()-DISTANCE_JUMP);
        final Animation animDown = new TranslateAnimation(marioImage.getTranslationX(), marioImage.getTranslationX(), marioImage.getTranslationY()-DISTANCE_JUMP, marioImage.getTranslationY());

        final Animation blockBump = new TranslateAnimation(questionBlockImage.getTranslationX(), questionBlockImage.getTranslationX(), questionBlockImage.getTranslationY(), questionBlockImage.getTranslationY()-50);
        blockBump.setStartOffset(50);
        blockBump.setDuration(50);


        animUp.setDuration(TIME_JUMP);
        animDown.setDuration(TIME_JUMP);

        animUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                marioImage.setImageResource(R.drawable.mario_jump);
                questionBlockImage.startAnimation(blockBump);
                animateCoin();

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                marioImage.startAnimation(animDown);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                marioImage.setImageResource(R.drawable.mario_stand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        marioImage.startAnimation(animUp);



    }

    public void buy(int cost, float passive)
    {
        counterNum.set(counterNum.get()-cost);
        passiveCoinsPerSecond.addAndGet(passive);
    }

    public void pay(int cost)
    {
        counterNum.set(counterNum.get()-cost);
    }

    public void addPassiveIncome(float amount)
    {
        passiveCoinsPerSecond.addAndGet(amount);
    }

    public int getCoins() {
        return counterNum.getInt();
    }


}