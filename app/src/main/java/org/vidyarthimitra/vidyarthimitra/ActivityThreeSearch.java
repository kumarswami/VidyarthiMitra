package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityThreeSearch extends AppCompatActivity {

    TextView Searchtextview;
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_search);

        Searchtextview = (TextView)findViewById(R.id.search_id);
        Searchtextview.setText("This is search activity");


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        Bundle bundlel = getIntent().getExtras();
        res = bundlel.getString("username");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Bundle b2=new Bundle();
                        b2.putString("username",res);
                        Intent ihome = new Intent(ActivityThreeSearch.this,HomeActivity.class);
                        ihome.putExtras(b2);
                        startActivity(ihome);
                        break;
                    case R.id.search:

                        break;
                    case R.id.profile:
                        String type = "profile";

                        BackgroundWorker backgroundWorker = new BackgroundWorker(ActivityThreeSearch.this);
                        backgroundWorker.execute(type, res);
                        break;
                    case R.id.customize:
                        Bundle b = new Bundle();

                        b.putString( "username", res );
                        Intent intent=new Intent(ActivityThreeSearch.this,CardActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
    }
}
