package com.example.jd.vollaydemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.spec.ECField;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private RequestQueue mRequestqeue;
    TextView textView,text2,text3;
    Button  button;
    private static final String TAG = MainActivity.class.getName();
    private StringRequest stringRequest;
    String url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
//String url ="https://maps.googleapis.com/maps/api/place/search/json?location=22.3221027,73.1730862&radius=5000&type=police&key=AIzaSyCvvmu5OxfIJIh9vHBK4dGaDxJLR4k-tkA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =(TextView)findViewById(R.id.text);
        text2=(TextView)findViewById(R.id.text1);
        text3=(TextView)findViewById(R.id.text2);
        button=(Button)findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),VolleyRecycle.class);
                startActivity(i);
            }
        });
        new Datafatch().execute();

    }
    /**
     * Async task class to get json by making vollay  call
     */
    class Datafatch extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.setMax(100);
//            pDialog.show();
            Toast.makeText(getApplicationContext(),"pre",Toast.LENGTH_SHORT).show();
        }



        @Override
        protected Void doInBackground(Void... voids) {
            mRequestqeue= Volley.newRequestQueue(MainActivity.this);

            StringRequest stringrequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"Response ="+response.toString());


                    try {
                       JSONObject root = new JSONObject(response);
                       JSONObject child=root.getJSONObject("metadata");
                        String api=child.getString("api");
                        String count=child.getString("count");
                        JSONArray jsonArray=root.getJSONArray("features");
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String type=jsonObject.getString("type");
                        JSONObject jsonObject1=jsonObject.getJSONObject("properties");
                        String mag=jsonObject1.getString("mag");
//                        JSONArray array=child.getJSONArray("features");
//                        JSONObject fect= array.getJSONObject(0);
//                        String mag= fect.getString("mag");
//                        String place=fect.getString("place");
                        textView.setText(api+" "+count);
                        text2.setText(type);
                        text3.setText(mag);
                        Toast.makeText(getApplicationContext(),""+api+ ""+count,Toast.LENGTH_SHORT).show();


                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Something went wrong while parsing the JSON response.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                }
            });

            mRequestqeue.add(stringrequest);
            return  null;
        }




        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this,"Postexecute",Toast.LENGTH_SHORT).show();
            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();

        }

    }

}
/**
 * 06-09 15:19:04.411 19292-19292/com.example.jd.vollaydemo I/com.example.jd.vollaydemo.MainActivity: Response ={"type":"FeatureCollection","metadata":{"generated":1497001673000,"url":"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02","title":"USGS Earthquakes","status":200,"api":"1.5.7","count":291},"features":[{"type":"Feature","properties":{"mag":1.29,"place":"10km SSW of Idyllwild, CA","time":1388620296020,"updated":1457728844428,"tz":-480,"url":"https://earthquake.usgs.gov/earthquakes/eventpage/ci11408890","detail":"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci11408890&format=geojson","felt":null,"cdi":null,"mmi":null,"alert":null,"status":"reviewed","tsunami":0,"sig":26,"net":"ci","code":"11408890","ids":",ci11408890,","sources":",ci,","types":",cap,focal-mechanism,general-link,geoserve,nearby-cities,origin,phase-data,scitech-link,","nst":39,"dmin":0.06729,"rms":0.09,"gap":51,"magType":"ml","type":"earthquake","title":"M 1.3 - 10km SSW of Idyllwild, CA"},"geometry":{"type":"Point","coordinates":[-116.7776667,33.6633333,11.008]},"id":"ci11408890"},
 {"type":"Feature","properties":{"mag":1.1,"place":"117km NW of Talkeetna, Alaska","time":1388620046000,"updated":1389117322816,"tz":-540,"url":"https://earthquake.usgs.gov/earthquakes/eventpage/ak10992887","detail":"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak10992887&format=geojson","felt":null,"cdi":null,"mmi":null,"alert":null,"status":"reviewed","tsunami":0,"sig":19,"net":"ak","code":"10992887","ids":",ak10992887,","sources":",ak,","types":",general-link,general-link,geoserve,nearby-cities,origin,tectonic-summary,","nst":null,"dmin":null,"rms":0.57,"gap":null,"magType":"ml","type":"earthquake","title":"M 1.1 - 117km NW of Talkeetna, Alaska"},"geometry":{"type":"Point","coordinates":[-151.6458,63.102,14.1]},"id":"ak10992887"},
 {"type":"Feature","properties":{"mag":1.3,"place":"6km SSW of Big Lake, Alaska","time":1388619956000,"updated":1388629238196,"tz":-540,"url":"https://earthquake.usgs.gov/earthquakes/eventpage/ak10934318","detail":"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak10934318&format=geojson","felt":null,"cdi":null,"mmi":null,"alert":null,"status":"reviewed","tsunami":0,"sig":26,"net":"ak","code":"10934318","ids":",ak10934318,","sources":",ak,","types":",cap,general-link,general-link,geoserve,nearby-cities,origin,tectonic-summary,","nst":null,"dmin":null,"rms":0.65,"gap":null,"magType":"ml","type":"earthquake","title":"M 1.3 - 6km SSW of Big Lake, Alaska"},"geometry":{"type":"Point","coordinates":[-149.9849,61.4624,46.7]},"id":"ak10934318"},
 {"type":"Feature","properties":{"mag":1.4,"place":"63km NW of Talkeetna, Alaska","time":1388619763000,"updated":1389117316113,"tz":-540,"url":"https://earthquake.usgs.gov/earthquakes/eventpage/ak10992885","detail":"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak10992885&format=geojson","felt":null,"cdi":null,"mmi":null,"alert":null,"status":"reviewed","tsunami":0,"sig":30,"net":"ak","code":"10992885","ids":",ak10992885,","sources":",ak,","types":",general-link,general-link,geoserve,nearby-cities,origin,tectonic-summary,","nst":null,"dmin":null,"rms":0.35,"gap":null,"magType":"ml","type":"earthquake","title":"M 1.4 - 63km NW of Talkeetna, Alaska"},"geometry":{"type":"Point","coordinates":[-150.8275,62.7884,87.6]},"id":"ak10992885"},
 {"type":"Feature","properties":{"mag":4,"place":"27km WNW of Coquimbo, Chile","time":1388619735000,"updated":1394151954000,"tz":-300,"url":"https://earthquake.usgs.gov/earthquakes/eventpage/usc000mnnn","detail":"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=usc000mnnn&format=geojson","felt":null,"cdi":null,"mmi":null,"alert":null,"status":"reviewed","tsunami":0,"sig":246,"net":"us","code":"c000mnnn","ids":",usc000mnnn,","sources":",us,","types":",general-link,geoserve,nearby-cities,origin,phase-data,tectonic-summary,","nst":null,"dmin":null,"rms":3.09,"gap":null,"magType":"ml","type":"earthquake","title":"M 4.0 - 27km WNW of Coquimbo, Chile"},"geometry":{"type":"Point","coordinates":[-71.621,-29.888,40]},"id":"us

 */

