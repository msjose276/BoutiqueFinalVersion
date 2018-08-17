package com.example.mateusjose.newchatos.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter2 extends BaseAdapter {


    private LayoutInflater inflater;
    Context context;
    private ArrayList<ItemBoutique> listItems;

    final String users = "Users";
    final String savedItems = "SavedItems";
    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForSavedItems = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);


    static class ViewHolder{
        ImageView ivItemImage;
        TextView tvBrand;
        TextView tvTitle;
        TextView tvPrice;
        SparkButton spark_button;
    }

    public ItemAdapter2 (Context context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItems = new ArrayList<ItemBoutique>();
        this.context = context;
    }

    public void addItem(ItemBoutique row) {
        listItems.add(row);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.item_card, null);
            holder = new ViewHolder();
            // get the views
            holder.ivItemImage= (ImageView) rowView.findViewById(R.id.iv_item_image);
            holder.tvBrand= (TextView) rowView.findViewById(R.id.tv_brand);
            holder.tvTitle= (TextView) rowView.findViewById(R.id.tv_title);
            holder.tvPrice= (TextView) rowView.findViewById(R.id.tv_price);
            holder.spark_button = (SparkButton) rowView.findViewById(R.id.spark_button);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder)rowView.getTag();
        }



        if(listItems.get(position).getTitle().length()>20)
            holder.tvBrand.setText(listItems.get(position).getTitle().substring(0,10)+"...");
        else
            holder.tvBrand.setText(listItems.get(position).getTitle());

        if(Double.toString(listItems.get(position).getPrice()).length()>20)
            holder.tvPrice.setText(Double.toString(listItems.get(position).getPrice()).substring(0,10)+"...");
        else
            holder.tvPrice.setText(Double.toString(listItems.get(position).getPrice()));

        if (Integer.toString(listItems.get(position).getItemPosition()).length()>20)
            holder.tvTitle.setText(Integer.toString(listItems.get(position).getItemPosition()).substring(0,10)+"...");
        else
            holder.tvTitle.setText(Integer.toString(listItems.get(position).getItemPosition()));

        //********************* check if the item has some photo url associated with it
        if(listItems.get(position).getPhotoUrl()!=null){
            //if (itemBoutique.getItemPosition()==position) {
            Log.e("imageUrl",listItems.get(position).getPhotoUrl().toString());

            holder.ivItemImage.setImageResource(R.drawable.roupa1);

            Glide.with(rowView.getContext())
                    .load(listItems.get(position).getPhotoUrl())
                    .into(holder.ivItemImage);
        }
        else{
            holder.ivItemImage.setImageResource(R.drawable.roupa1);
        }


        refForSavedItems.child(listItems.get(position).getItemID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    holder.spark_button.setChecked(true);
                }
                else{
                    holder.spark_button.setChecked(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        final ItemBoutique itemBoutique= listItems.get(position);

        holder.spark_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (SingletonPatternForItemsSaved.getInstance() != null) {

                    if (holder.spark_button.isChecked()) {
                        addSavedItemBoutique(itemBoutique.getItemID());
                    } else {
                        deleteSavedItemBoutique(itemBoutique.getItemID());
                    }
                }
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) { }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) { }
        });



        return rowView;
    }


    public void deleteSavedItemBoutique(String ItemID) {

        // delete itemID
        refForSavedItems.child(ItemID).removeValue().
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //return true;
                        Toast.makeText(context, "item deletado dos seus favoridos com sucesso ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //return false;
                    }
                });
    }

    public void addSavedItemBoutique(String ItemID) {
        //save itemID
        refForSavedItems.child(ItemID).setValue(ItemID);
        Toast.makeText(context, "item adicionado dos seus favoridos com sucesso ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
