package com.example.myapplicationtemp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<Image> img;
    private static LayoutInflater inflater = null;



    public ImageAdapter(Activity context,ArrayList<Image> imgae) {
        this.context=context;
        this.img=imgae;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public Image getItem(int position) {
        return img.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;
        itemView=(itemView==null)? inflater.inflate(R.layout.list_item,null):itemView;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView5);
        Image selectedImg=img.get(position);
        Picasso.get().load(selectedImg.getImage()).into(imageView);
        return itemView;

    }
}