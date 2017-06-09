package com.example.jd.vollaydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jd.vollaydemo.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyRecycle extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    RequestQueue mRequestqeue;
    String s1[],s2[];
    String url = "https://api.myjson.com/bins/w86a";
    RecyclerView recyclerView;
    List<Newsfeeds> resultsList =new ArrayList<>();
    private StringRequest stringRequest;
    private String TAG;
    MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_recycle);
        getFragmentManager().beginTransaction().replace(R.id.main_content,new ItemFragment()).commit();
//        List<Newsfeeds> feedsList = new ArrayList<Newsfeeds>();

        //Initialize RecyclerView
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        adapter=new MyRecyclerAdapter(this,resultsList);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//      mRequestqeue= Volley.newRequestQueue(this);
//        sendRequest();
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response);
                        try {

                            JSONArray root=new JSONArray(response);
                            for (int i=0;i<root.length();i++)
                            {
                                JSONObject js=root.getJSONObject(i);
                                Newsfeeds news=new Newsfeeds(js.getString("title"),js.getString("content"));
                                Toast.makeText(getApplicationContext(),""+js.getString("title")+" "+i,Toast.LENGTH_SHORT).show();
                                resultsList.add(news);


                            }
                            for (int i = 0; i < resultsList.size(); i++) {
                                Log.v(TAG, "Result #" + (i + 1) + ": " + resultsList.get(i).toString());
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
         //The following lines add the request to the volley queue
        // These are very important
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
        mRequestqeue.add(stringRequest);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
