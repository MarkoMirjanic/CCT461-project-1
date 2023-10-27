package com.example.hydrobuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hydrobuddy.databinding.ActivityMainBinding;
import com.example.hydrobuddy.service.WaterReminderService;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        startService(new Intent(this, WaterReminderService.class));
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFrag) {
                navController.navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.historyFrag) {
                navController.navigate(R.id.historyFragment);
                return true;
            } else if (itemId == R.id.settingFrag) {
                navController.navigate(R.id.settingFragment);
                return true;
            } else {
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Start the WaterReminderService when the app is closed
        startService(new Intent(this, WaterReminderService.class));
    }
}