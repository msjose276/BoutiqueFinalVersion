package com.example.mateusjose.newchatos.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.ItemFeatured;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;

public class adapterFeatured extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    private ArrayList<ItemFeatured> listItems;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForSavedItems = database.child(ProjStrings.Users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ProjStrings.SavedItems);

    static class ViewHolder{
        TextView tvQuantity;
        TextView tvTopic;
        RelativeLayout rlAdapterFeatured;
        ImageView ivBackground;
    }

    public adapterFeatured (Context context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItems = new ArrayList<ItemFeatured>();
        this.context = context;
    }

    public void addItem(ItemFeatured row) {
        listItems.add(row);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.card_adapter_featured, null);
            holder = new ViewHolder();
            // get the views
            holder.tvQuantity= (TextView) rowView.findViewById(R.id.tv_quantity);
            holder.tvTopic= (TextView) rowView.findViewById(R.id.tv_topic);
            holder.rlAdapterFeatured= (RelativeLayout) rowView.findViewById(R.id.rl_adapter_featured);
            holder.ivBackground= (ImageView) rowView.findViewById(R.id.iv_background);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder)rowView.getTag();
        }

        if(listItems.get(position).getTopic()!=null)
            holder.tvTopic.setText(listItems.get(position).getTopic());

        if (Integer.toString(listItems.get(position).getQuantity())!=null)
            holder.tvQuantity.setText(Integer.toString(listItems.get(position).getQuantity())+" itens");
        // set the background of the adapter
        holder.ivBackground.setBackgroundResource(R.drawable.man_bg);

        //********************* check if the item has some photo url associated with it
        /*if(listItems.get(position).getPhotoUrl()!=null){
            Log.e("imageUrl",listItems.get(position).getPhotoUrl().toString());
            Glide.with(rowView.getContext())
                    .load(listItems.get(position).getPhotoUrl())
                    .into(holder.ivItemImage);
        }
        else{
            holder.ivItemImage.setImageResource(R.drawable.roupa1);
        }*/


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
