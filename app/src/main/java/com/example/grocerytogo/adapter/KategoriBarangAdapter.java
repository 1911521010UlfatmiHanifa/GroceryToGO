package com.example.grocerytogo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.HomeFragment;
import com.example.grocerytogo.R;
import com.example.grocerytogo.model.KategoriBarang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KategoriBarangAdapter
        extends RecyclerView.Adapter<KategoriBarangAdapter.KategoriBarangViewHolder>{

    public interface KlikKategoriBarang{
        void onClick(View view, KategoriBarang kategoriBarang);
    }

    KlikKategoriBarang listener = null;

    public void setListener(HomeFragment listener) {
        this.listener = (KlikKategoriBarang) listener;
    }

    ArrayList<KategoriBarang> listKategori = new ArrayList<>();
    public void setListKategori(ArrayList<KategoriBarang> listKategori){
        this.listKategori = listKategori;
    }

    public class KategoriBarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textKategori;
        ImageView imageKategori;

        public KategoriBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            textKategori = itemView.findViewById(R.id.textKategori);
            imageKategori = itemView.findViewById(R.id.imageKategori);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onClick(view, listKategori.get(getAdapterPosition()));
            }
        }
    }

    @NonNull
    @Override
    public KategoriBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_kategori, parent, false);
        KategoriBarangViewHolder viewHolder = new KategoriBarangViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriBarangViewHolder viewHolder, int position) {
        KategoriBarang kategoriBarang = listKategori.get(position);
        viewHolder.textKategori.setText(kategoriBarang.kategori.toString());
        Picasso.get().load(kategoriBarang.gambar).into(viewHolder.imageKategori);
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

}
