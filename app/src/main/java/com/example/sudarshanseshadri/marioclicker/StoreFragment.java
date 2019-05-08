package com.example.sudarshanseshadri.marioclicker;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StoreFragment extends Fragment {

    // × is the times symbol

    ImageView up1, up2, up3, up4, up5, up6, down1, down2, down3, down4, down5, down6;
    TextView text1, text2, text3, text4, text5, text6;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;
    int count5 = 0;
    int count6 = 0;

    LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;

    ConstraintLayout constraintLayout;

    int cost1=100;
    int cost2=1000;
    int cost3=10000;


    double gen1=1;
    double gen2=20;
    double gen3=500;


    boolean[] itemsAvailable = new boolean[6];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.fragment_store,null);
        super.onCreate(savedInstanceState);

        constraintLayout = rootView.findViewById(R.id.constraintLayout);

        up1 = rootView.findViewById(R.id.imageView_up_1);
        up2 = rootView.findViewById(R.id.imageView_up_2);
        up3 = rootView.findViewById(R.id.imageView_up_3);
        up4 = rootView.findViewById(R.id.imageView_up_4);
        up5 = rootView.findViewById(R.id.imageView_up_5);
        up6 = rootView.findViewById(R.id.imageView_up_6);

        down1 = rootView.findViewById(R.id.imageView_down_1);
        down2 = rootView.findViewById(R.id.imageView_down_2);
        down3 = rootView.findViewById(R.id.imageView_down_3);
        down4 = rootView.findViewById(R.id.imageView_down_4);
        down5 = rootView.findViewById(R.id.imageView_down_5);
        down6 = rootView.findViewById(R.id.imageView_down_6);

        text1 = rootView.findViewById(R.id.textView_down_1);
        text2 = rootView.findViewById(R.id.textView_down_2);
        text3 = rootView.findViewById(R.id.textView_down_3);
        text4 = rootView.findViewById(R.id.textView_down_4);
        text5 = rootView.findViewById(R.id.textView_down_5);
        text6 = rootView.findViewById(R.id.textView_down_6);
        if (count1!=0) {
            text1.setText("×" + count1);
            down1.setImageResource(R.drawable.drill);
        }
        if (count2!=0) {
            text2.setText("×" + count2);
            down2.setImageResource(R.drawable.mine);
        }
        if (count3!=0) {
            text3.setText("×" + count3);
            down3.setImageResource(R.drawable.factory);
        }
        if (count4!=0) {
            text4.setText("×" + count4);
            down4.setImageResource(R.drawable.ackpow);
        }
        if (count5!=0) {
            text5.setText("×" + count5);
            down5.setImageResource(R.drawable.hamster_wheel);
        }
        if (count6!=0) {
            text6.setText("×" + count6);
            down6.setImageResource(R.drawable.slim);
        }


        layout1 = rootView.findViewById(R.id.linearLayout_1);
        layout2 = rootView.findViewById(R.id.linearLayout_2);
        layout3 = rootView.findViewById(R.id.linearLayout_3);
        layout4 = rootView.findViewById(R.id.linearLayout_4);
        layout5 = rootView.findViewById(R.id.linearLayout_5);
        layout6 = rootView.findViewById(R.id.linearLayout_6);


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItemsLocked()[0]==true) {
                    count1++;
                    text1.setText("×" + count1);
                    animateBoughtItem(1);
                }
                else
                {
                    Toast.makeText(getActivity(), "Not Enough Sparks.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItemsLocked()[1]==true) {
                    count2++;
                    text2.setText("×" + count2);
                    animateBoughtItem(2);
                }
                else
                {
                    Toast.makeText(getActivity(), "Not Enough Sparks.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItemsLocked()[2]==true) {
                    count3++;
                    text3.setText("×" + count3);
                    animateBoughtItem(3);
                }
                else
                {
                    Toast.makeText(getActivity(), "Not Enough Sparks.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItemsLocked()[3]==true) {
                    count4++;
                    text4.setText("×" + count4);
                    animateBoughtItem(4);
                }
                else
                {
                    Toast.makeText(getActivity(), "Not Enough Coins.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItemsLocked()[4]==true) {
                    count5++;
                    text5.setText("×" + count5);
                    animateBoughtItem(5);
                }
                else
                {
                    Toast.makeText(getActivity(), "Not Enough Coins.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setItemsLocked()[5]==true) {
                    count6++;
                    text6.setText("×" + count6);
                    animateBoughtItem(6);
                }
                else
                {
                    Toast.makeText(getActivity(), "Not Enough Coins.", Toast.LENGTH_SHORT).show();
                }
            }
        });





        return rootView;

    }

    private void animateBoughtItem(int itemNum) {
        final int resource;
        final ImageView fromImage, toImage;

        switch (itemNum) //set the picture
        {
            case 1:
            {
                resource = R.drawable.drill;
                fromImage = up1;
                toImage = down1;
                ((MainActivity)(getActivity())).addPassiveCoins(cost1, gen1);
            }
            break;

            case 2:
            {
                resource = R.drawable.mine;
                fromImage = up2;
                toImage = down2;
                ((MainActivity)(getActivity())).addPassiveCoins(cost2, gen2);
            }
            break;

            case 3:
            {
                resource = R.drawable.factory;
                fromImage = up3;
                toImage = down3;
                ((MainActivity)(getActivity())).addPassiveCoins(cost3, gen3);
            }
            break;

            case 4:
            {
                resource = R.drawable.ackpow;
                fromImage = up4;
                toImage = down4;
                ((MainActivity)(getActivity())).addPassiveSparks(cost1, gen1);
            }
            break;

            case 5:
            {
                resource = R.drawable.hamster_wheel;
                fromImage = up5;
                toImage = down5;
                ((MainActivity)(getActivity())).addPassiveSparks(cost2, gen2);
            }
            break;

            case 6:
            {
                resource = R.drawable.slim;
                fromImage = up6;
                toImage = down6;
                ((MainActivity)(getActivity())).addPassiveSparks(cost3, gen3);
            }
            break;

            default:
            {
                resource = R.drawable.drill;
                fromImage = up6;
                toImage = down6;
            }
            break;
        }




        toImage.setImageResource(resource);

        final ScaleAnimation scale = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f );
        scale.setDuration(400);


        toImage.setAnimation(scale);

        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                toImage.setImageResource(resource);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //constraintLayout.removeView(textViewInCode);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public String toString() {
        return "Store";
    }

    public void setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);   // 128 = 0.5
    }

    public void  setUnlocked(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }


    public boolean[] setItemsLocked()
    {
        for(int jeff = 0; jeff<6;jeff++)
        {
            itemsAvailable[jeff]=false;
        }
        int[] c = ((MainActivity)this.getActivity()).getCurrency();

        if (c[0]>=cost3)
        {

            itemsAvailable[2] = true;
        }

        else if (c[0]>=cost2)
        {
            itemsAvailable[1] = true;
        }

        else if (c[0]>=cost1)
        {
            itemsAvailable[0] = true;
        }

        if (c[1]>=cost3)
        {

            itemsAvailable[5] = true;
        }

        if (c[1]>=cost2)
        {
            itemsAvailable[4] = true;
        }

        else if (c[1]>=cost1)
        {
            itemsAvailable[3] = true;
        }

        return itemsAvailable;

    }
}