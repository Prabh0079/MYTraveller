package com.example.mytraveller.AllClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytraveller.Auth.LoginActivity;
import com.example.mytraveller.Auth.RegistrationActivity;
import com.example.mytraveller.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listener for the "I have an account" button
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity (Username and Password for existing users)
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the "Get Started" button
        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RegistrationActivity (Username and Password for new users)
                Intent intent = new Intent(IntroActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
