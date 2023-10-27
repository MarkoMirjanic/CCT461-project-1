package com.example.hydrobuddy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.example.hydrobuddy.util.Constants;
import com.example.hydrobuddy.util.SharedPreferencesUtil;
import com.example.hydrobuddy.util.TimeUtil;
import com.example.hydrobuddy.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private int glasses = 0;
    private int goal = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        SharedPreferencesUtil.initialize(requireActivity());
        glasses = SharedPreferencesUtil.getInt(Constants.WATER_DRANK,0);
        goal = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,15);
        binding.progressBar.setMax(goal);
        updateProgressBar();
        binding.glasses.setText(glasses+"x");
        binding.result1.setText("Glasses Drank: " + glasses + " / Goal: " + goal);
        binding.timeStamp.setText(TimeUtil.getTimeAgo(SharedPreferencesUtil.getLong(Constants.TIME_STAMP_WATER_DRUNK,0)));

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGlass();
            }
        });

        return binding.getRoot();
    }
    private void addGlass() {
        glasses = glasses+1;
        SharedPreferencesUtil.putInt(Constants.WATER_DRANK,glasses);
        SharedPreferencesUtil.putLong(Constants.TIME_STAMP_WATER_DRUNK,System.currentTimeMillis());
        binding.glasses.setText(glasses+"x");
        binding.result1.setText("Glasses Drank: " + glasses + " / Goal: " + goal);
        binding.timeStamp.setText(TimeUtil.getTimeAgo(SharedPreferencesUtil.getLong(Constants.TIME_STAMP_WATER_DRUNK,0)));
        updateProgressBar();
        Snackbar.make(binding.glasses, "1 more glass drank!", Snackbar.LENGTH_SHORT).show();
    }

    private void updateProgressBar() {
        if (glasses<=goal){
            binding.progressBar.setProgress(glasses);
            binding.drankNo.setText("Keep it up! You've drank "+glasses +" out of "+goal);
        }else{
            binding.progressBar.setProgress(goal);
            binding.drankNo.setText("Keep it up! You've completed today's challenge!");
        }
    }
}