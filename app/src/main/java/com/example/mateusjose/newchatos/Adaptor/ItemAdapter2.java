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
import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemAdapter2 extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    private ArrayList<ItemBoutique> listItems;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForSavedItems = database.child(ProjStrings.Users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ProjStrings.SavedItems);

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

        //set title
        if(listItems.get(position).getTitle().length()>20)
            holder.tvBrand.setText(listItems.get(position).getTitle().substring(0,10)+"...");
        else
            holder.tvBrand.setText(listItems.get(position).getTitle());

        //set price
        if(Double.toString(listItems.get(position).getPrice()).length()>20)
            holder.tvPrice.setText(Double.toString(listItems.get(position).getPrice()).substring(0,10)+"...");
        else
            holder.tvPrice.setText(Double.toString(listItems.get(position).getPrice()));

        //set something
        if (listItems.get(position).getDate()!=null){
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
            holder.tvTitle.setText(ft.format(listItems.get(position).getDate()).toString());
        }
        else {
            holder.tvTitle.setText("...");
        }

        //********************* check if the item has some photo url associated with it
        if(listItems.get(position).getPhotoUrl()!=null){
            Log.e("imageUrl",listItems.get(position).getPhotoUrl().toString());
            Glide.with(rowView.getContext())
                    .load(listItems.get(position).getPhotoUrl())
                    .into(holder.ivItemImage);
        }
        else{
            holder.ivItemImage.setImageResource(R.drawable.roupa1);
        }
        // set the liked button state
        if(LoggedUserSingleton.getInstance().getBoutiqueUser().isItemSaved(listItems.get(position).getItemID())){
            holder.spark_button.setChecked(true);            }
        else{
            holder.spark_button.setChecked(false);
        }

        final ItemBoutique itemBoutique= listItems.get(position);
        // set the button event listener
        holder.spark_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (SingletonPatternForItemsSaved.getInstance() != null) {

                    if (holder.spark_button.isChecked()) {
                        LoggedUserSingleton.getInstance().getBoutiqueUser().addSavedItem(itemBoutique.getItemID());
                        Toast.makeText(context, ProjStrings.FavariteItemAdd, Toast.LENGTH_SHORT).show();
                    } else {
                        LoggedUserSingleton.getInstance().getBoutiqueUser().deleteSavedItem(itemBoutique.getItemID());
                        Toast.makeText(context, ProjStrings.FavariteItemDelete, Toast.LENGTH_SHORT).show();
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
