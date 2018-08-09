package com.example.mateusjose.newchatos.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Adaptor.AdaptorForListOfImages;
import com.example.mateusjose.newchatos.Adaptor.AdaptorForSavedItem;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SavedItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // check if the user is signed in. if it is not get out of this page
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Toast.makeText(this, "precisa estar logado para ver os seus produtos favoritos", Toast.LENGTH_SHORT).show();
            finish();
        }
        // get the list and set the adptor
        List<ItemBoutique> listItemBoutques=new ArrayList<>();
        //TODO: need to delete this later
        /*listItemBoutques.add(new ItemBoutique());
        listItemBoutques.add(new ItemBoutique());*/

        //get the list from the singleton object
        listItemBoutques = SingletonPatternForItemsSaved.getInstance().getListOfSavedItemBoutique();
        //Mateus: call and set the card adaptor
        AdaptorForSavedItem adaptor = new AdaptorForSavedItem(getBaseContext(),listItemBoutques);

        final ListView listView = (ListView)findViewById(R.id.lv_saved_items);
        listView.setAdapter(adaptor);

        //mateus: set onclick listner for the list of items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do something

            }
        });
    }

}
