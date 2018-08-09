package com.example.mateusjose.newchatos.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FragCamisas extends android.support.v4.app.Fragment{

    private DatabaseReference mDatabase;
    FirebaseListAdapter<ItemBoutique> firebaseListAdapter;
    ListView mListView;
    public static final String ITEM_ID_STRING = "ITEM_ID";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.blue1);



        mListView=(ListView) page.findViewById(R.id.rl_background_woman);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("ItemBoutique").limitToLast(50);

        FirebaseListOptions<ItemBoutique> options=new FirebaseListOptions.Builder<ItemBoutique>()
//                .setLayout(R.layout.activity_main2)
                .setLayout(R.layout.card)
                .setQuery(query, ItemBoutique.class)
                .setLifecycleOwner(this)
                .build();


        firebaseListAdapter = new FirebaseListAdapter<ItemBoutique>(options){

            @Override
            protected void populateView(View v, ItemBoutique model, int position) {

                ImageView itemImage= (ImageView)v.findViewById(R.id.iv_item_image);
                TextView price=(TextView) v.findViewById(R.id.et_price);
                TextView brand=(TextView) v.findViewById(R.id.et_brand);
                TextView boutique=(TextView) v.findViewById(R.id.et_boutique);
                TextView location=(TextView) v.findViewById(R.id.et_location);

                //itemImage.setImageBitmap();
                if(Double.toString(model.getPrice())!=null)
                    price.setText("Preco: "+Double.toString(model.getPrice()));
                if(model.getTitle()!=null)
                    brand.setText("Titulo: "+model.getTitle());
                if(model.getType()!=null)
                    boutique.setText("Boutique: "+model.getType());
                //if(model.getAddress().getProvince()!=null && model.getAddress().getMonicipio()!=null)
                //    boutique.setText("Location: "+model.getAddress().getProvince() + ", " + model.getAddress().getMonicipio());
                boutique.setText("Location: Luanda, Maianga" );

            }
        };

        mListView.setAdapter(firebaseListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), ItemDetail.class);
                //pass the user email and password to the next activity
                intent.putExtra(ITEM_ID_STRING,firebaseListAdapter.getItem(position).getItemID());
                startActivity(intent);
                startActivity(intent);
            }
        });


        return page;
    }
}
