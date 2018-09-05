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

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemFeatured;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class adapterBoutiques extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    private ArrayList<BoutiqueUser> listItems;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForSavedItems = database.child(ProjStrings.Users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ProjStrings.SavedItems);

    static class ViewHolder{
        CircleImageView ciProfileImage;
        TextView tvBoutiqueName;
        TextView tvAddress;
        TextView tvItemPosted;
    }

    public adapterBoutiques(Context context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItems = new ArrayList<BoutiqueUser>();
        this.context = context;
    }

    public void addItem(BoutiqueUser row) {
        listItems.add(row);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        View rowView = convertView;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.card_adapter_boutiques, null);
            holder = new ViewHolder();
            // get the views
            holder.ciProfileImage= (CircleImageView) rowView.findViewById(R.id.ci_profile_image);
            holder.tvBoutiqueName= (TextView) rowView.findViewById(R.id.tv_boutique_name);
            holder.tvAddress= (TextView) rowView.findViewById(R.id.tv_address);
            holder.tvItemPosted= (TextView) rowView.findViewById(R.id.tv_item_posted);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder)rowView.getTag();
        }


        if(listItems.get(position).getFullName()!=null){
            holder.tvBoutiqueName.setText(listItems.get(position).getFullName());
        }
        else{
            holder.tvBoutiqueName.setText("Boutique Majestades");
        }
        if(listItems.get(position).getAddress()!=null){
            holder.tvAddress.setText(listItems.get(position).getAddress().getMonicipio());
        }
        else{
            holder.tvAddress.setText("Golfe 2, Luanda");
        }
        if(listItems.get(position).getPhoneNumber()!=null){
            holder.tvItemPosted.setText(listItems.get(position).getPhoneNumber());
        }
        else{
            holder.tvItemPosted.setText("431");
        }
        //********************* check if the item has some photo url associated with it
        if(listItems.get(position).getPhotoUrl()!=null){
            Log.e("imageUrl",listItems.get(position).getPhotoUrl().toString());
            Glide.with(rowView.getContext())
                    .load(listItems.get(position).getPhotoUrl())
                    .into(holder.ciProfileImage);
        }
        else{
            holder.ciProfileImage.setImageResource(R.drawable.user_general_image);
        }


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
