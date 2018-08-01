package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MaterialAdaptor extends ArrayAdapter<ItemBoutique> {

    List<ItemBoutique> listCard;
    LayoutInflater layoutInflater;

    public MaterialAdaptor(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.item_card, objects);
        listCard=objects;
        layoutInflater=LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView=layoutInflater.inflate(R.layout.item_card,parent,false);
        }
        // get the views
        ImageView ivItemImage= (ImageView) convertView.findViewById(R.id.iv_item_image);
        TextView tvBrand= (TextView) convertView.findViewById(R.id.tv_brand);
        TextView tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvPrice= (TextView) convertView.findViewById(R.id.tv_price);

        tvBrand.setText(listCard.get(position).getTitle());
        tvTitle.setText(listCard.get(position).getTitle());
        tvPrice.setText(Double.toString(listCard.get(position).getPrice()));

        return convertView;
    }

}
