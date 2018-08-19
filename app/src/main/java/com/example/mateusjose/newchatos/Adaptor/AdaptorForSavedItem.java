package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;

public class AdaptorForSavedItem extends ArrayAdapter<ItemBoutique> {

    List<ItemBoutique> listCard;
    LayoutInflater layoutInflater;
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
        final ImageView itemImage = (ImageView) convertView.findViewById(R.id.iv_item_image);
        TextView title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView subtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
        TextView price = (TextView) convertView.findViewById(R.id.tv_price);

        // set the title
        if(listCard.get(position).getTitle()!=null){ title.setText(listCard.get(position).getTitle()); }
        else{ title.setText("**"); }
        //set the brand
        if(listCard.get(position).getBrand()!=null){ subtitle.setText(listCard.get(position).getBrand()); }
        else{ subtitle.setText("**"); }
        //set the price
        if(String.valueOf(listCard.get(position).getPrice())!=null){ price.setText(String.valueOf(listCard.get(position).getPrice())); }
        else{ price.setText("**"); }

        // set the image for each item
        if(listCard.get(position).getImagePath()!=null){
            //get the reference for the storage
            StorageReference storageReference = ConfigurationFirebase.getFirebaseStorage().getReference().child(listCard.get(position).getImagePath());
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext())
                            .load(uri)
                            .into(itemImage);
                    //Log.e("link for image",imageUri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //TODO: change this image to a general image
                    itemImage.setImageResource(R.drawable.roupa1);
                }
            });
        }
        else{
            //TODO: change this image to a general image
            itemImage.setImageResource(R.drawable.roupa1);
        }

        //set action for the liked button
        final ItemBoutique itemBoutique= listCard.get(position);
        final SparkButton spark_button = (SparkButton) convertView.findViewById(R.id.spark_button);
        spark_button.setChecked(true);
        spark_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (SingletonPatternForItemsSaved.getInstance() != null) {
                    //see if the user is logged before saving the liked item
                    if (LoggedUserSingleton.getInstance().getBoutiqueUser() != null){
                        if (spark_button.isChecked()) {
                            itemBoutique.addSavedItemBoutique();
                            Toast.makeText(thisContext, ProjStrings.FavariteItemAdd, Toast.LENGTH_SHORT).show();
                        } else {
                            itemBoutique.deleteSavedItemBoutique();
                            Toast.makeText(thisContext, ProjStrings.FavariteItemDelete, Toast.LENGTH_SHORT).show();
                        }
                    }// else, do nothing
                    else{
                        Toast.makeText(thisContext, ProjStrings.LoginToSavedItems, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) { }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) { }
        });

        return convertView;
    }
}
