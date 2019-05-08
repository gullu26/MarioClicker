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

public class PaperMarioFragment extends Fragment {

    private static final float DISTANCE_JUMP = 100;
    private static final long TIME_JUMP = 100;
    private static final long SPARK_TIME = 300;
    private static final float SPARK_RISE = 150;
    private static final int PASSIVE_INCOME_TIMES_PER_SECOND = 10;

    ImageView marioImage;
    ImageView questionBlockImage;
    ConstraintLayout constraintLayout;

    LinearLayout linearLayout;

    TextView counter;
    AtomicFloat counterNum=new AtomicFloat();
    AtomicFloat passiveSparksPerSecond = new AtomicFloat();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_paper_mario,null);

        super.onCreate(savedInstanceState);
        constraintLayout = rootView.findViewById(R.id.id_layout);
        marioImage = rootView.findViewById(R.id.id_imageView_pMario);
        questionBlockImage = rootView.findViewById(R.id.id_imageView_eBlock);
        linearLayout = rootView.findViewById(R.id.id_linearLayout);
        counter = rootView.findViewById(R.id.id_textView_counter);


        marioImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSparks(1);
                jump();
            }
        });

        addHelperThreads();
        return rootView;
    }



    private void addHelperThreads() {
        Thread passiveSparks = new Thread("Passive Spark Production")
        {
            public void run()
            {
                while (true)
                {
                    counterNum.addAndGet(passiveSparksPerSecond.get()/PASSIVE_INCOME_TIMES_PER_SECOND);
                    try {
                        sleep(1000/PASSIVE_INCOME_TIMES_PER_SECOND);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        };

        passiveSparks.start();


        Thread updateCount = new Thread("UpdateSparks")
        {
            public void run()
            {

                while (true)
                {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            String sparksOrspark = counterNum.getInt() == 1 ? " Spark" : " Sparks";
                            counter.setText(counterNum.getInt() + sparksOrspark);
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
        return "Paper Mario";
    }


    private void addSparks(final int numberOfSparks) {

        Thread t1 = new Thread("Increment")
        {
            public void run()
            {
                counterNum.addAndGet(numberOfSparks);

                //passiveSparksPerSecond.addAndGet(numberOfSparks);
            }

        };
        t1.start();





    }

    private void animateSpark() {


        final ImageView sparkInCode = new ImageView(getContext());
        sparkInCode.setId(View.generateViewId());
        sparkInCode.setImageResource(R.drawable.spark);
        //ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams((int)(questionBlockImage.getWidth()*0.8), (int)(questionBlockImage.getWidth()*0.8));
        ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams(100, 100);

        sparkInCode.setLayoutParams(p);
        constraintLayout.addView(sparkInCode);






        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(sparkInCode.getId(), ConstraintSet.BOTTOM, linearLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(sparkInCode.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(sparkInCode.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

        constraintSet.applyTo(constraintLayout);

        AnimationSet animationSetText = new AnimationSet(false);
        AnimationSet animationSetSpark = new AnimationSet(false);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());

        int distanceOffset=(int) (Math.random()*50 -25);
        Animation sparkRise = new TranslateAnimation(sparkInCode.getTranslationX()+distanceOffset, sparkInCode.getTranslationX()+distanceOffset, sparkInCode.getTranslationY(), sparkInCode.getTranslationY()-SPARK_RISE);



        animationSetText.addAnimation(fadeOut);

        animationSetSpark.addAnimation(sparkRise);
        animationSetSpark.addAnimation(fadeOut);

        animationSetText.setDuration(SPARK_TIME);
        animationSetSpark.setDuration(SPARK_TIME);


        sparkInCode.setAnimation(animationSetSpark);

        animationSetSpark.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //constraintLayout.removeView(textViewInCode);
                sparkInCode.setVisibility(View.GONE);

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
                marioImage.setImageResource(R.drawable.paper_mario_jump);
                questionBlockImage.startAnimation(blockBump);
                animateSpark();

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

                marioImage.setImageResource(R.drawable.paper_mario_stand);
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
        passiveSparksPerSecond.addAndGet(passive);
    }

    public void pay(int cost)
    {
        counterNum.set(counterNum.get()-cost);
    }

    public void addPassiveIncome(float amount)
    {
        passiveSparksPerSecond.addAndGet(amount);
    }

    public int getSparks() {
        return counterNum.getInt();
    }


}