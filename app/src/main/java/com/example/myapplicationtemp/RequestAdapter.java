package com.example.myapplicationtemp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RequestAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<RequestOfUser> req;
    private static LayoutInflater inflater = null;



    public RequestAdapter(Activity context,ArrayList<RequestOfUser> req) {
        this.context=context;
        this.req=req;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return  req.size();
    }

    @Override
    public RequestOfUser getItem(int position) {
        return req.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;
        itemView=(itemView==null)? inflater.inflate(R.layout.list_item_req,null):itemView;
        TextView reqText = (TextView) itemView.findViewById(R.id.reqTxt);
        RequestOfUser selectedReq=req.get(position);
        reqText.setText(selectedReq.getRequestText());
        return itemView;
    }
}