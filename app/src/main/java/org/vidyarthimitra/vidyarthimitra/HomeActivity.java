package org.vidyarthimitra.vidyarthimitra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    String myusername;
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    Context context;
    String res;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private  int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences( "prefs", Context.MODE_PRIVATE );
        myusername = sharedPreferences.getString( KEY_USERNAME, "" );






//        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        listItems = new ArrayList<>();
//        for (int i = 1; i <= 10; i++){
//            ListItem listItem = new ListItem("heading " + (i+1),"aaaaaaaaaaaaaaaaaaaaaaa");
//            listItems.add(listItem);
//
//        }
//        adapter = new MyAdapter(listItems, this);
//        recyclerView.setAdapter(adapter);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        // attaching bottom sheet behaviour - hide / show on scroll
       //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
       //layoutParams.setBehavior(new BottomNavigationBehavior());


        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        //Bundle bundlel = getIntent().getExtras();
        //res = bundlel.getString("username");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        break;
                    case R.id.search:
                        Bundle b1 = new Bundle();
                        ///  String str = getIntent().getExtras().getString( "username" );
                        b1.putString( "username", res );
                        Intent i=new Intent(HomeActivity.this, Search.class);
                        i.putExtras(b1);
                        startActivity(i);
                        break;
                    case R.id.profile:
                        String type = "profile";

                        BackgroundWorker backgroundWorker = new BackgroundWorker(HomeActivity.this);
                        backgroundWorker.execute(type, myusername);





                        // Intent iprofile = new Intent(HomeActivity.this,Profile_activity.class);
                        //startActivity(iprofile);
                        break;
                    case R.id.customize:
                       /* Bundle b = new Bundle();
                        b.putString("username",res);

                        Intent icustomize = new Intent(HomeActivity.this,CardActivity.class);
                        icustomize.putExtras(b);
                        startActivity(icustomize);*/

                        Intent intent=new Intent(HomeActivity.this, CardActivity.class);

                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0 && bottomNavigationView.isShown()) {
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else if (dy < 0 ) {
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i=0; i<dotscount;i++){
            dots[i]= new ImageView(this);
            dots[i].setImageDrawable( ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.active));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i=0; i<dotscount; i++){
                    dots[i].setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive));
                }

                dots[position].setImageDrawable( ContextCompat.getDrawable(getApplicationContext(), R.drawable.active));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000,4000);
    }
    public class MyTimerTask extends TimerTask {


        @Override
        public void run() {

            HomeActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });
        }
    }
    private Boolean exit = false;
    int backpressed = 0;
    @Override
    public void onBackPressed() {
        backpressed++;
        if(backpressed == 1) {
            Toast.makeText( getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT ).show();
        }

        if(backpressed >=2 ) {
            backpressed = 0;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }



}



