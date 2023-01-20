package am.solution.weddingplanner;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import am.solution.weddingplanner.model.User;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SummaryFragment summaryFragment = new SummaryFragment();
    TasksFragment tasksFragment = new TasksFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    GuestsFragment guestsFragment = new GuestsFragment();
    VendorsFragment vendorsFragment = new VendorsFragment();
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,summaryFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.summary:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,summaryFragment).commit();
                        return true;
                    case R.id.tasksLayout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,tasksFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;
                    case R.id.guests:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,guestsFragment).commit();
                        return true;
                    case R.id.vendors:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,vendorsFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }
}