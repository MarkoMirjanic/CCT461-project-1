package com.example.hydrobuddy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.example.hydrobuddy.util.Constants;
import com.example.hydrobuddy.util.SharedPreferencesUtil;
import com.example.hydrobuddy.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private int goal = 0;
    private int reminder = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(getLayoutInflater(),container,false);

        SharedPreferencesUtil.initialize(requireActivity());
        goal = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,15);
        reminder = SharedPreferencesUtil.getInt(Constants.REMINDER_GOAL,15);
        binding.goal.setText(goal+"");
        binding.reminder.setText(reminder+"");
        binding.addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goal = goal+1;
                binding.goal.setText(goal+"");
                SharedPreferencesUtil.putInt(Constants.GLASS_GOAL,goal);
                Snackbar.make(binding.goal, "1 more glass added to goal!", Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.subGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goal>1){
                    goal = goal-1;
                    binding.goal.setText(goal+"");
                    SharedPreferencesUtil.putInt(Constants.GLASS_GOAL,goal);
                    Snackbar.make(binding.goal, "1 glass removed from goal!", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        binding.subReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reminder>1){
                    reminder = reminder-1;
                    binding.reminder.setText(reminder+"");
                    SharedPreferencesUtil.putInt(Constants.REMINDER_GOAL,goal);
                    Snackbar.make(binding.goal, "1 reminder removed!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        binding.addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    reminder = reminder+1;
                    binding.reminder.setText(reminder+"");
                    SharedPreferencesUtil.putInt(Constants.REMINDER_GOAL,goal);
                    Snackbar.make(binding.goal, "1 reminder added!", Snackbar.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}