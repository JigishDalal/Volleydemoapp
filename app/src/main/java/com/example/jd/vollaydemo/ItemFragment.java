package com.example.jd.vollaydemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jd.vollaydemo.dummy.DummyContent;
import com.example.jd.vollaydemo.dummy.DummyContent.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView ;
    RequestQueue mRequestqeue;
    StringRequest request;
    MyItemRecyclerViewAdapter myadpter;
    String url = "https://api.myjson.com/bins/w86a";
    List<Newsfeeds> resultsList =new ArrayList<>();
    private StringRequest stringRequest;
    private OnListFragmentInteractionListener mListener;
    ProgressDialog progressDialog;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
//        RecyclerView recyclerView=null;
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            myadpter=new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener);
            recyclerView = (RecyclerView) view;

//            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
     // recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),this));
        progressDialog =new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Pleace wait...");
        progressDialog.setCancelable(false);
       sendRequest();


        return view;
    }


    private void sendRequest(){

        mRequestqeue= Volley.newRequestQueue(getActivity());
        progressDialog.show();
        stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response);
                        try {

                            JSONArray root=new JSONArray(response);

//                            Toast.makeText(getActivity(),"length"+root.length(),Toast.LENGTH_SHORT).show();
                            for (int i=0;i<root.length();i++)
                            {
                                JSONObject js=root.getJSONObject(i);
//                                Newsfeeds news=new Newsfeeds(js.getString("title"),js.getString("content"));
//                                ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,""+i);
//                                lv.setAdapter(adapter);
//                                DummyContent.addItem(new DummyItem(" "+i,js.getString("title")+"",js.getString("content")+""));
//                                DummyContent.addItem();
//                                Toast.makeText(getActivity(),"hi",Toast.LENGTH_SHORT).show();
                                DummyContent.addItem(new DummyItem(""+(i+1),js.getString("title")+" ",js.getString("content")+" "));
//                                Toast.makeText(getActivity(),""+js.getString("title")+" "+i,Toast.LENGTH_SHORT).show();



                            }
                            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    }
                });
        //The following lines add the request to the volley queue
        // These are very important
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
        mRequestqeue.add(stringRequest);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
    public class Mytask extends AsyncTask<Void,Void,Void>{
        ProgressDialog pr;
        Activity context;

        public Mytask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pr=new ProgressDialog(getActivity());
            pr.setTitle("Loading...");
            pr.setProgress(0);
            pr.setMax(10);
            pr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pr.setMessage("Pleace wait.....");
            pr.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pr.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... param) {
            final int[] total = {0};
            mRequestqeue=Volley.newRequestQueue(getActivity());
            request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response",response);
                    try {

                        JSONArray root=new JSONArray(response);
                        total[0] =Integer.parseInt(String.valueOf(root.length()));
//                            Toast.makeText(getActivity(),"length"+root.length(),Toast.LENGTH_SHORT).show();
                        for (int i=0;i<root.length();i++)
                        {
                            JSONObject js=root.getJSONObject(i);
//                                Newsfeeds news=new Newsfeeds(js.getString("title"),js.getString("content"));
//                                ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,""+i);
//                                lv.setAdapter(adapter);
//                                DummyContent.addItem(new DummyItem(" "+i,js.getString("title")+"",js.getString("content")+""));
//                                DummyContent.addItem();
//                                Toast.makeText(getActivity(),"hi",Toast.LENGTH_SHORT).show();
                            DummyContent.addItem(new DummyItem(""+(i+1),js.getString("title")+" ",js.getString("content")+" "));
//                                Toast.makeText(getActivity(),""+js.getString("title")+" "+i,Toast.LENGTH_SHORT).show();
//                                resultsList.add();


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });

            mRequestqeue.add(stringRequest);

//            return total[0] ;
            return null;
        }



    }
}
