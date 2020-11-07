package com.billy.cricketmvvm.view.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.billy.cricketmvvm.R;
import com.billy.cricketmvvm.models.matches.MatchDetailsModel;
import com.billy.cricketmvvm.repositories.CricketApi;
import com.billy.cricketmvvm.repositories.Presets;
import com.billy.cricketmvvm.repositories.RetrofitService;
import com.billy.cricketmvvm.view.fragments.SeriesFragment;
import com.billy.cricketmvvm.view.fragments.SeriesGamesFragment;
import com.billy.cricketmvvm.view.fragments.TeamsFragment;
import com.billy.cricketmvvm.view.fragments.UpcomingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    int flag;
    FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = (item)->{
                switch (item.getItemId()){
                    case R.id.upcoming:
                        flag = 0;
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_home, new UpcomingFragment()).commit();
                        return true;
                    case R.id.matches:
                        flag = 0;
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_home, new SeriesGamesFragment()).commit();
                        return true;
                    case R.id.teams:
                        flag = 0;
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_home, new TeamsFragment()).commit();
                        return true;
                    case R.id.series:
                        flag = 0;
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_home, new SeriesFragment()).commit();
                        return true;
                }
                return false;
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declarations
        bottomNavigationView = findViewById(R.id.main_navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.upcoming);
        flag = 0;

    }

    @Override
    public void onBackPressed() {
        if(flag == 0){
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
            flag++;
        }else{
            System.exit(0);
        }
    }
}