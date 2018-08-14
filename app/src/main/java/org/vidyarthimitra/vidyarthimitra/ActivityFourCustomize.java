package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityFourCustomize extends AppCompatActivity {

    TextView customizetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_customize);
        customizetext = (TextView)findViewById(R.id.customize_id);
        customizetext.setText("This is Customize ACtivity");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent ihome = new Intent(ActivityFourCustomize.this,HomeActivity.class);
                        startActivity(ihome);
                        break;
                    case R.id.search:
                        Intent icustomize = new Intent(ActivityFourCustomize.this,ActivityThreeSearch.class);
                        startActivity(icustomize);
                        break;
                    case R.id.profile:
                        Intent iprofile = new Intent(ActivityFourCustomize.this,ActivityTwoProfile.class);
                        startActivity(iprofile);
                        break;
                    case R.id.customize:

                        break;

                }
                return false;
            }
        });
    }
}
