package org.vidyarthimitra.vidyarthimitra;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    ListView SubjectListView;
    ProgressBar progressBarSubject;
    String ServerURL = "http://vidyarthimitra.org/phpmailer/newdisplay.php";
    EditText editText;
    List<String> listString = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        SubjectListView = (ListView) findViewById(R.id.listview1);

        progressBarSubject = (ProgressBar) findViewById(R.id.progressBar);

        //searchview = (SearchView) findViewById(R.id.searchView);
        editText = (EditText)findViewById(R.id.edittext1);


        new GetHttpResponse(Search.this).execute();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Search.this.arrayAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        SubjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Search.this, newsdesc.class);

                //intent.putExtra("ne_title", ne8[position]);
                String title = String.valueOf(parent.getItemAtPosition(position));
                intent.putExtra("Title", title);
                startActivity(intent);
            }
        });

    }


    private class GetHttpResponse extends AsyncTask<Void, Void, String> {
        public Context context;

        String ResultHolder;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {

            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try {
                httpServiceObject.ExecutePostRequest();

                if (httpServiceObject.getResponseCode() == 200) {
                    ResultHolder = httpServiceObject.getResponse();

                    if (ResultHolder != null) {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(ResultHolder);

                            JSONObject jsonObject;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);

                                listString.add(jsonObject.getString("ne_title").toString());

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected void onPostExecute(String result)

        {
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            progressBarSubject.setVisibility(View.GONE);

            SubjectListView.setVisibility(View.VISIBLE);

            arrayAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_2, android.R.id.text1, listString);

            SubjectListView.setAdapter(arrayAdapter);

        }
    }


    public class HttpServicesClass {
        public int responseCode;

        public String message;

        public String response;

        public ArrayList<NameValuePair> ArrayListParams;

        public ArrayList<NameValuePair> headers;

        public String UrlHolder;

        public String getResponse() {
            return response;
        }

        public String getErrorMessage() {
            return message;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public HttpServicesClass(String url) {
            this.UrlHolder = url;

            ArrayListParams = new ArrayList<NameValuePair>();

            headers = new ArrayList<NameValuePair>();
        }

        public void AddParam(String name, String value) {
            ArrayListParams.add(new BasicNameValuePair(name, value));
        }

        public void AddHeader(String name, String value) {
            headers.add(new BasicNameValuePair(name, value));
        }

        public void ExecuteGetRequest() throws Exception {
            String MixParams = "";

            if (!ArrayListParams.isEmpty()) {
                MixParams += "?";

                for (NameValuePair p : ArrayListParams) {
                    String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");

                    if (MixParams.length() > 1) {
                        MixParams += "&" + paramString;
                    } else {
                        MixParams += paramString;
                    }
                }
            }

            HttpGet httpGet = new HttpGet(UrlHolder + MixParams);

            for (NameValuePair h : headers) {
                httpGet.addHeader(h.getName(), h.getValue());
            }

            executeRequest(httpGet, UrlHolder);
        }

        public void ExecutePostRequest() throws Exception {
            HttpPost httpPost = new HttpPost(UrlHolder);
            for (NameValuePair h : headers) {
                httpPost.addHeader(h.getName(), h.getValue());
            }

            if (!ArrayListParams.isEmpty()) {
                httpPost.setEntity(new UrlEncodedFormEntity(ArrayListParams, HTTP.UTF_8));
            }

            executeRequest(httpPost, UrlHolder);
        }

        private void executeRequest(HttpUriRequest request, String url) {
            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);

            HttpConnectionParams.setSoTimeout(httpParameters, 10000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpResponse httpResponse;
            try {
                httpResponse = httpClient.execute(request);
                responseCode = httpResponse.getStatusLine().getStatusCode();
                message = httpResponse.getStatusLine().getReasonPhrase();

                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();

                    response = convertStreamToString(inputStream);

                    inputStream.close();
                }
            } catch (ClientProtocolException e) {
                httpClient.getConnectionManager().shutdown();
                e.printStackTrace();
            } catch (IOException e) {
                httpClient.getConnectionManager().shutdown();
                e.printStackTrace();
            }
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        }

    }
}

