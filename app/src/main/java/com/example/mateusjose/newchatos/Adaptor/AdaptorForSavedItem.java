package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;

import java.util.List;

public class AdaptorForSavedItem extends ArrayAdapter<ItemBoutique> {

    List<ItemBoutique> listCard;
    LayoutInflater layoutInflater;
    ImageView ivItemImage;
    TextView tvTitle;
    Context thisContext;
    public AdaptorForSavedItem(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.item_card_for_saved_items, objects);
        listCard=objects;
        layoutInflater=LayoutInflater.from(context);
        thisContext=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView=layoutInflater.inflate(R.layout.item_card_for_saved_items,parent,false);
        }
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.iv_item_image);
        TextView title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView subtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
        TextView price = (TextView) convertView.findViewById(R.id.tv_price);

        title.setText(listCard.get(position).getTitle());
        subtitle.setText(listCard.get(position).getSize());
        price.setText(String.valueOf(listCard.get(position).getPrice()));

        //TODO: the imageview needs to be set
        //itemImage
        return convertView;
    }


}
