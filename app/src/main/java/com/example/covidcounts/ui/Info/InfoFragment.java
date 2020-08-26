package com.example.covidcounts.ui.Info;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.covidcounts.R;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;

public class InfoFragment extends Fragment{

    ImageView imageView;
    Button nextBtn, prevBtn;
    RelativeLayout relativeLayout;
    int[] imgID = new int[]{R.drawable.z_corona, R.drawable.z_wash_hands, R.drawable.z_social_distancing, R.drawable.z_communication, R.drawable.z_foot_shake, R.drawable.z_trust_info, R.drawable.z_donate};
    int n=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View infoRoot = inflater.inflate(R.layout.fragment_info, container, false);

        imageView = infoRoot.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.z_corona);

        nextBtn = infoRoot.findViewById(R.id.nextBtn);
        prevBtn = infoRoot.findViewById(R.id.prevBtn);

        relativeLayout = infoRoot.findViewById(R.id.infoRelative);

        imageView.setImageResource(imgID[0]);
        nextBtn.setVisibility(View.VISIBLE);
        prevBtn.setVisibility(View.GONE);
        relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSwitcher();
                if (n<6)
                    n++;
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSwitcher();
                if (n>0)
                    n--;
            }
        });

        return infoRoot;
    }

    public void imgSwitcher(){
        switch (n){
            case 0:
                imageView.setImageResource(imgID[0]);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.GONE);
                relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 1:
                imageView.setImageResource(imgID[1]);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.parseColor("#9ad8d8"));
                break;
            case 2:
                imageView.setImageResource(imgID[2]);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.parseColor("#6bc9c9"));
                break;
            case 3:
                imageView.setImageResource(imgID[3]);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.parseColor("#15bbaf"));
                break;
            case 4:
                imageView.setImageResource(imgID[4]);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.parseColor("#e1aab5"));
                break;
            case 5:
                imageView.setImageResource(imgID[5]);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 6:
                imageView.setImageResource(imgID[6]);
                nextBtn.setVisibility(View.GONE);
                prevBtn.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.parseColor("#f7c9d8"));
                break;
        }
    }
}