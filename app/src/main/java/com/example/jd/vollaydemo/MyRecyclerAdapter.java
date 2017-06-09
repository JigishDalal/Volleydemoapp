package com.example.jd.vollaydemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JD on 09-06-2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.Myholder> {

    private List<Newsfeeds> feedsList;
    String title[];
    String details[];
    Context ctx;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context,List<Newsfeeds> feedsList) {
        this.title = title;
        this.details = details;
        this.ctx = context;
        this.feedsList=feedsList;
       inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyRecyclerAdapter.Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater myLayoutInflater=LayoutInflater.from(ctx);
        View myview =myLayoutInflater.inflate(R.layout.item,parent,false);
        return new Myholder(myview);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.Myholder holder, int position) {
//        Newsfeeds feeds = feedsList.get(position);
        holder.title.setText(feedsList.get(position).ptitle);
        holder.details.setText(feedsList.get(position).pdetails);

    }


    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView title;
        TextView details;

        public Myholder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title_view);
            details =(TextView)itemView.findViewById(R.id.details);

        }
    }
}
