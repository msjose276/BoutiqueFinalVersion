package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.net.Uri;
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

/**
 * Created by mateusjose on 1/9/18.
 */

public class AdaptorForListOfImages extends ArrayAdapter<Uri> {

    List<Uri> listCard;
    LayoutInflater layoutInflater;
    ImageView ivItemImage;
    TextView tvTitle;
    Context thisContext;
    public AdaptorForListOfImages(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.item_adaptor_for_list_of_images, objects);
        listCard=objects;
        layoutInflater=LayoutInflater.from(context);
        thisContext=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView=layoutInflater.inflate(R.layout.item_adaptor_for_list_of_images,parent,false);
        }

        ivItemImage = (ImageView) convertView.findViewById(R.id.iv_item_image);
        Glide.with(getContext())
                .load(listCard.get(position))
                .into(ivItemImage);

        return convertView;
    }


}
