package org.vidyarthimitra.vidyarthimitra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CardActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    public static RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    public static List<MyData> data_list;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        sharedPreferences = getSharedPreferences( "prefs", Context.MODE_PRIVATE );
        username = sharedPreferences.getString( "username", "" );

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list  = new ArrayList<>();
        load_data_from_server(0);

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this,data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    //load_data_from_server(data_list.get(data_list.size()-1).getId());
                }

            }
        });

       /* ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if(netinfo != null && netinfo.isConnected()) {
            Toast.makeText(getApplicationContext(), "Net"+netinfo.getTypeName(), Toast.LENGTH_SHORT ).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }*/



        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        // attaching bottom sheet behaviour - hide / show on scroll
        //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationBehavior());



        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        Intent i = new Intent(CardActivity.this,HomeActivity.class);
                        startActivity(i);
                        break;
                    case R.id.search:



                        Intent i1=new Intent(CardActivity.this,Search.class);

                        startActivity(i1);
                        break;
                    case R.id.profile:
                        String type = "profile";

                        BackgroundWorker backgroundWorker = new BackgroundWorker(CardActivity.this);
                        backgroundWorker.execute(type, username);


                        // Intent iprofile = new Intent(HomeActivity.this,Profile_activity.class);
                        //startActivity(iprofile);
                        break;
                    case R.id.customize:
                        break;





                }
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bottomNavigationView.isShown()) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    bottomNavigationView.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://vidyarthimitra.org/phpmailer/vmnc.php?nc_id="+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        MyData data = new MyData(object.getInt("nc_id"),object.getString("nc_name"),
                                object.getString("nc_image"));

                        data_list.add(data);
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }

    public static class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            MyData item = data_list.get(itemPosition);
            Toast.makeText(v.getContext(),item.getDescription(), Toast.LENGTH_LONG).show();
            //     v.getContext().startActivity(new Intent(v.getContext(), product_list.class));
//            Context context = v.getContext();
//            Intent intent = new Intent(context, product_list.class);
//            context.startActivity(intent);
            Intent intent = new Intent(context, categorysearch.class);
            Bundle b = new Bundle();
            b.putString("result", item.getDescription());
            intent.putExtras(b);
            v.getContext().startActivity( intent );
        }

    }
}
