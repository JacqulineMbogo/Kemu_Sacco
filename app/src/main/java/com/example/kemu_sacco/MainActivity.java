package com.example.kemu_sacco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kemu_sacco.Contributions.contributions_home;
import com.example.kemu_sacco.Loans.loans_home;

public class MainActivity extends AppCompatActivity {

    Button contributions, loans;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contributions = findViewById(R.id.contributions);
        loans = findViewById(R.id.loans);

        contributions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( MainActivity.this, contributions_home.class);
                startActivity(intent);

            }
        });

        loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent( MainActivity.this, loans_home.class);
                startActivity(intent1);
            }
        });

    }
}
