package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityTwoProfile extends AppCompatActivity {

    TextView profiletextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_profile);

        profiletextview = (TextView)findViewById(R.id.profile_id);
        profiletextview.setText("This is profile activity");


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent ihome = new Intent(ActivityTwoProfile.this,HomeActivity.class);
                        startActivity(ihome);
                        break;
                    case R.id.search:
                        Intent isearch = new Intent(ActivityTwoProfile.this,ActivityThreeSearch.class);
                        startActivity(isearch);
                        break;
                    case R.id.profile:

                        break;
                    case R.id.customize:
                        Intent icustomize = new Intent(ActivityTwoProfile.this,ActivityFourCustomize.class);
                        startActivity(icustomize);
                        break;

                }
                return false;
            }
        });
    }
}
