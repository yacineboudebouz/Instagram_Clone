package com.example.newfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.newfirebase.Fragments.HomeFragment;
import com.example.newfirebase.Fragments.NotificationFragment;
import com.example.newfirebase.Fragments.ProfileFragment;
import com.example.newfirebase.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreen extends AppCompatActivity {
    private Fragment fragmentSelected;
    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_valid);
        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        fragmentSelected = new HomeFragment();
                        break;
                    case R.id.search:
                        fragmentSelected = new SearchFragment();
                        break;
                    case R.id.add:
                        startActivity(new Intent(MainScreen.this,PostActivity.class));
                        break;
                    case R.id.notification:
                        fragmentSelected = new NotificationFragment();
                        break;
                    case R.id.profile:
                        fragmentSelected = new ProfileFragment();
                        break;
                }
                if (fragmentSelected != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentsContainer,fragmentSelected).commit();
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentsContainer,new HomeFragment()).commit();
    }
}