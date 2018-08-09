package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;
import com.varunest.sparkbutton.SparkEventListener;

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
    Context thisContext;

    SparkButton spark_button;
    ItemBoutique itemBoutique;

    public ItemAdaptor(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.item_card, objects);
        listCard=objects;
        layoutInflater=LayoutInflater.from(context);
        thisContext=context;

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
        itemBoutique = listCard.get(position);

        // ******************** set title, price and brand. Also, decrease the number of characters if it is longer than 20
        if(itemBoutique.getTitle().length()>20)
            tvBrand.setText(itemBoutique.getTitle().substring(0,10)+"...");
        else
            tvBrand.setText(itemBoutique.getTitle());

        if(Double.toString(itemBoutique.getPrice()).length()>20)
            tvPrice.setText(Double.toString(itemBoutique.getPrice()).substring(0,10)+"...");
        else
            tvPrice.setText(Double.toString(itemBoutique.getPrice()));

        if (Integer.toString(itemBoutique.getItemPosition()).length()>20)
            tvTitle.setText(Integer.toString(itemBoutique.getItemPosition()).substring(0,10)+"...");
        else
            tvTitle.setText(Integer.toString(itemBoutique.getItemPosition()));

        //********************* check if the item has some photo url associated with it
        if(itemBoutique.getPhotoUrl()!=null){
            if (itemBoutique.getItemPosition()==position) {
                Glide.with(getContext())
                        .load(itemBoutique.getPhotoUrl())
                        .into(ivItemImage);
            } else {
                Glide.with(getContext()).clear(ivItemImage);
                ivItemImage.setImageResource(R.drawable.bonita);
            }
        }
        else{
            ivItemImage.setImageResource(R.drawable.roupa1);
        }


        //check if the user is logged before changing the state of the spark_button
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {

            spark_button = (SparkButton) convertView.findViewById(R.id.spark_button);
            // activate the heart buttom if the itemboutique is in the ListOfSavedItemBoutique
            if (SingletonPatternForItemsSaved.getInstance().searchByItemId(itemBoutique.getItemID())) {
                //spark_button.setActivated(true);
                spark_button.setChecked(true);
            }
            spark_button.setEventListener(new SparkEventListener() {
                @Override
                public void onEvent(ImageView button, boolean buttonState) {
                    if (SingletonPatternForItemsSaved.getInstance() != null) {
                        List<ItemBoutique> itemBoutiqueForSearch = SingletonPatternForItemsSaved.getInstance().getListOfSavedItemBoutique();

                        //check if the item is saved.
                        //if it is saved delete it from the list. Else, add it to the list
                        if (itemBoutiqueForSearch.contains(itemBoutique)) {
                            SingletonPatternForItemsSaved.getInstance().deleteSavedItemBoutique(itemBoutique);
                            Toast.makeText(thisContext, "disSave", Toast.LENGTH_SHORT).show();
                        } else {
                            SingletonPatternForItemsSaved.getInstance().addSavedItemBoutique(itemBoutique);
                            Toast.makeText(thisContext, "save", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onEventAnimationStart(ImageView button, boolean buttonState) { }
                @Override
                public void onEventAnimationEnd(ImageView button, boolean buttonState) { }
            });
        }

        return convertView;
    }


}
