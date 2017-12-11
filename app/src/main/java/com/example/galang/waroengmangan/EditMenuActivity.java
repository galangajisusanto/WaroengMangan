package com.example.galang.waroengmangan;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditMenuActivity extends AppCompatActivity {

    RecyclerView rv;
    // menampung data dari firebase
    List<TampilMenus> tampilMenu;
    AdapterEditMenu adapter;

    Activity context=this;
    DatabaseReference reference,menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        //recylerViwe
        rv = (RecyclerView) findViewById(R.id.recycler_edit);
        //Set layout
        rv.setLayoutManager(new LinearLayoutManager(this));
        //Membuat ArryList
        tampilMenu = new ArrayList<>();
        //tampilMenu.add(new TampilMenus("foto","nama","chat","http://relinjose.com/directory/filename.png"));
        //deklarasi database firebase
        reference = FirebaseDatabase.getInstance().getReference();
        menu=reference.child("Menu");
        adapter = new AdapterEditMenu(context,tampilMenu);
        rv.setAdapter(adapter);


        menu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tampilMenu.removeAll(tampilMenu);
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    TampilMenus dataku = snapshot.getValue(TampilMenus.class);
                    tampilMenu.add(dataku);
                }
                // ke 18
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

