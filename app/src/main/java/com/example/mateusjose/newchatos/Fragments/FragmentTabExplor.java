package com.example.mateusjose.newchatos.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Activities.ExchangeMessageActivity;
import com.example.mateusjose.newchatos.Adaptor.ItemAdaptor;
import com.example.mateusjose.newchatos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusjose on 1/9/18.
 */

public class FragmentTabExplor extends android.support.v4.app.Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.darkBlue);


        final List<Contact> listOfContacts=new ArrayList<>();

        listOfContacts.add(new Contact("amantes do fotebol","sibo","234234545542"));
        listOfContacts.add(new Contact("estudantes do ISPTEC","235354542"));
        //Mateus: call and set the card adaptor
        ItemAdaptor adaptor = new ItemAdaptor(getContext(),listOfContacts);


        final ListView listView = (ListView) page.findViewById(R.id.rl_background_woman);

        listView.setAdapter(adaptor);

        //mateus: set onclick listner for the list of items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ExchangeMessageActivity.class);
                startActivity(intent);
            }
        });


        return page;
    }
}
