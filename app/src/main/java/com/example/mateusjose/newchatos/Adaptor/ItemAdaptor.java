package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mateusjose on 1/9/18.
 */

public class ItemAdaptor extends ArrayAdapter<ItemBoutique> {

    List<ItemBoutique> listCard;
    LayoutInflater layoutInflater;
    ImageView ivItemImage;
    TextView tvTitle;
    public ItemAdaptor(@NonNull Context context, @NonNull List objects) {
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
        ivItemImage= (ImageView) convertView.findViewById(R.id.iv_item_image);
        TextView tvBrand= (TextView) convertView.findViewById(R.id.tv_brand);
        tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvPrice= (TextView) convertView.findViewById(R.id.tv_price);

        tvBrand.setText(listCard.get(position).getTitle());
        tvTitle.setText(listCard.get(position).getTitle());
        tvPrice.setText(Double.toString(listCard.get(position).getPrice()));


        FirebaseStorage storage = ConfigurationFirebase.getFirebaseStorage();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name
        //StorageReference pathReference = storageRef.child("boutique_items/"+listCard.get(position).getImagePath());

        if(listCard.get(position).getImagePath()!=null){





            StorageReference storageReference = storageRef.child(listCard.get(position).getImagePath());

            tvTitle.setText(listCard.get(position).getImagePath().toString());

            // Load the image using Glide
            Glide.with(this.getContext())
                    .load(storageReference)
                    .into(ivItemImage);

        }
        return convertView;
    }

}
