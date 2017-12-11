package com.example.galang.waroengmangan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position==0)
                {
                    Intent intent=new Intent(MainActivity.this,MasukanMenuActivity.class);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Intent intent=new Intent(MainActivity.this,EditMenuActivity.class);
                    startActivity(intent);
                }

               Toast.makeText(MainActivity.this, "" + position,
                       Toast.LENGTH_SHORT).show();

            }
        });




    }


}
