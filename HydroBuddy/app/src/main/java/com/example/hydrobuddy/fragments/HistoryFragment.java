package com.example.hydrobuddy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.hydrobuddy.util.Constants;
import com.example.hydrobuddy.util.SharedPreferencesUtil;
import com.example.hydrobuddy.util.WaterIntakePreferences;
import com.example.hydrobuddy.databinding.FragmentHistoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.util.Set;

public class HistoryFragment extends Fragment {

   private FragmentHistoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentHistoryBinding.inflate(getLayoutInflater(),container,false);
        Set<String> completionDates = WaterIntakePreferences.getGoalCompletionDates(requireActivity());
        List<String> goalDates = new ArrayList<>(completionDates);
        Map<String, Integer> goalsMap = new HashMap<>();
        Map<String, Integer> glassesRankMap = new HashMap<>();

        for (String date : goalDates) {
            int goal = WaterIntakePreferences.getGoalForDate(requireActivity(), date);
            int glassesRank = WaterIntakePreferences.getGlassesRankForDate(requireActivity(), date);
            goalsMap.put(date, goal);
            glassesRankMap.put(date, glassesRank);
        }

        // Fetch and set daily intake data from SharedPreferences
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(date);
        int goal = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,15);
        int glassesDrank = SharedPreferencesUtil.getInt(Constants.WATER_DRANK,15);

        binding.result.setText("Selected Date: " + currentDate);
        updateProgressData(goal,glassesDrank);
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

                // Retrieve data for the selected date
                int goal = WaterIntakePreferences.getGoalForDate(requireActivity(), selectedDate);
                int glassesDrank = WaterIntakePreferences.getGlassesRankForDate(requireActivity(), selectedDate);

                binding.result.setText("Selected Date: " + selectedDate);
                updateProgressData(goal,glassesDrank);
            }
        });
        return binding.getRoot();
    }

    private void updateProgressData(int goal,int glasses) {

        // Update TextView and ProgressBar with the retrieved data
        binding.result1.setText("Glasses Drank: " + glasses + " / Goal: " + goal);
        binding.progressBar.setMax(goal);
        binding.progressBar.setProgress(glasses);
    }

//    String selectedDate = selectedDateTextView.getText().toString().substring(15); // Extract the date
//    int glassesDrank = /* Get the glasses drank value */;
//    int goal = /* Get the goal value */;
//                WaterIntakePreferences.saveGoalCompletion(getContext(), selectedDate, goal, glassesDrank);
//    updateProgressData(selectedDate);
}