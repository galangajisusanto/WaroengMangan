package com.example.galang.waroengmangan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by cia on 26/05/2017.
 */

//ketiga Adapter di extends kan
    // keempat klik kanan implement
public class AdapterEditMenu extends RecyclerView.Adapter<AdapterEditMenu.TampilDataViewHolder>{
    // kelima list
    List<TampilMenus> tampilMenus;
    Context ctx;
    // ke sebelas contructor klik kanan

    public AdapterEditMenu(Context ct, List<TampilMenus> tampilMenus)
    {   ctx=ct;
        this.tampilMenus = tampilMenus;
    }

    @Override
    public TampilDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_menu, parent, false);
        TampilDataViewHolder holder = new TampilDataViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TampilDataViewHolder holder, int position) {
        final TampilMenus mymenu = tampilMenus.get(position);
        holder.txtmJudul.setText(mymenu.getmNama());
        holder.txtmHarga.setText(mymenu.getmHarga());
        Picasso.with(ctx)
                .load(mymenu.getMimageURL())
                .into(holder.gambar);
        holder.gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImage(mymenu.getMimageURL());
            }

        });
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Apakah Anda Yakin Menghapus Menu Ini?")
                        .setCancelable(false)
                        .setPositiveButton("YA",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int id){
                                hapus(mymenu.getMimageURL(),mymenu.getmNama());
                            }
                        })
                        .setNegativeButton("TIDAk",null)
                        .show();


            }
        });
    }

    @Override
    public int getItemCount() {

        return tampilMenus.size();
    }

//Holder pengganti id
    public static class TampilDataViewHolder extends RecyclerView.ViewHolder{

        // ke dua TextView txtmNama;
        TextView txtmJudul, txtmHarga;
        CircleImageView gambar;
        ImageView popupGambar;
        Button edit;
        Button hapus;
        public TampilDataViewHolder(View itemView) {
            super(itemView);

            txtmJudul = (TextView)itemView.findViewById(R.id.menu_edit);
            txtmHarga = (TextView)itemView.findViewById(R.id.harga_edit);
            gambar=(CircleImageView) itemView.findViewById(R.id.foto_edit);
            hapus=(Button)itemView.findViewById(R.id.btn_hapus);
            edit=(Button)itemView.findViewById(R.id.btn_edit);
            popupGambar=(ImageView)itemView.findViewById(R.id.showgambar);
        }
    }

    // Image POP UP

    public void showImage(String URL) {
        Dialog builder = new Dialog(ctx);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(ctx);
        Picasso.with(ctx)
                .load(URL)
                .into(imageView);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    //Hapus file gambar dan database
    public void hapus(String URL, String Child)
    {
        ///Hapus Database
        DatabaseReference rev= FirebaseDatabase.getInstance().getReference();
        DatabaseReference menu=rev.child("Menu");
        menu.child(Child).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ctx,"Hapus Menu dari Database Sukses..",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx,"Hapus Menu dari Database Gagal..",Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference mStorageRef;

        ///Hapus File
        mStorageRef= FirebaseStorage.getInstance().getReferenceFromUrl(URL);
        mStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ctx,"Hapus Gambar Dari Storege Sukses..",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx,"Hapus Gambar Dari Storege Gagal..",Toast.LENGTH_SHORT).show();
            }
        });



    }



}
