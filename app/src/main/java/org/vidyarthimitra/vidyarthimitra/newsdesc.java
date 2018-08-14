package org.vidyarthimitra.vidyarthimitra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newsdesc extends AppCompatActivity {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work


    String URL_PRODUCTS = "http://vidyarthimitra.org/phpmailer/newsdesc.php";


    TextView title, desc;
    ImageView imgview;
    String URL, TempHolder, newString;


    Toolbar toolbar;
    //a list to store all the products

    EditText newssearch;
    //the recyclerview
    RecyclerView recyclerView, newsrecycle;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdesc);

        //ImageLoader class instance

        title = (TextView) findViewById(R.id.title);
        imgview = (ImageView) findViewById(R.id.newsimg);
        Bundle bundlel = getIntent().getExtras();
        String resvalue = bundlel.getString("Title");
        title.setText(resvalue);      //Get the news title on screen


        URL = URL_PRODUCTS + "?title=" + resvalue;
        newString = URL.replaceAll(" ", "%20");
        desc = (TextView) findViewById(R.id.desc);
        desc.setMovementMethod( new ScrollingMovementMethod() );
        //getting the recyclerview from xml

        //initializing the productlist
        //productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
    }


    public void loadProducts() {


        final int loader = R.drawable.news;



        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        StringRequest stringRequest = new StringRequest(Request.Method.GET, newString,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {




                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);
                                String pp = product.getString("ne_desc").toString();
                                String img = product.getString("sd_image").toString();


                                desc.setText(pp);

                                //Loading Image from URL
                                Picasso.with(getApplicationContext())
                                        .load(img)
                                        .placeholder(R.drawable.news)
                                        .into(imgview);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}