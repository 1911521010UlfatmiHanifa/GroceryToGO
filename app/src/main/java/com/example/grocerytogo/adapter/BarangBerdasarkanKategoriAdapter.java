package com.example.grocerytogo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.BarangKategoriActivity;
import com.example.grocerytogo.HomeFragment;
import com.example.grocerytogo.R;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BarangBerdasarkanKategoriAdapter
        extends RecyclerView.Adapter<BarangBerdasarkanKategoriAdapter.BarangBersarkanKategoriViewHolder> {

    ArrayList<BarangBerdasarKategori> listBarang = new ArrayList<>();
//    public BarangBerdasarkanKategoriAdapter(ArrayList<BarangBerdasarKategori> listBarang) {
//        this.listBarang = listBarang;
//    }

    public void setListBarang(ArrayList<BarangBerdasarKategori> listBarang){
        this.listBarang = listBarang;
    }

    public class BarangBersarkanKategoriViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView namaBarang, hargaBarang, jumlah;
        ImageView gambarBarang;
        Button tambah, kurang, sebelumTambah, tambahKeranjang;

        public BarangBersarkanKategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(R.id.textBarang);
            hargaBarang = itemView.findViewById(R.id.textHargaBarang);
            gambarBarang = itemView.findViewById(R.id.imageBarang);
            jumlah = itemView.findViewById(R.id.textJumlah);
            tambah = itemView.findViewById(R.id.btn_tambah1);
            kurang = itemView.findViewById(R.id.btn_kurang1);
            sebelumTambah = itemView.findViewById(R.id.btn_tambahProduk);
            tambahKeranjang = itemView.findViewById(R.id.tambah_keranjang);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onClick(view, listBarang.get(getAdapterPosition()));
            }
        }
    }

    public interface KlikBarang{
        void onClick(View view, BarangBerdasarKategori barangBerdasarKategori);
    }

    KlikBarang listener = null;

    public void setListener(KlikBarang listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public BarangBersarkanKategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_barang_kategori, parent, false);
        BarangBersarkanKategoriViewHolder viewHolder = new BarangBersarkanKategoriViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BarangBersarkanKategoriViewHolder viewHolder, int position) {
        BarangBerdasarKategori barang = listBarang.get(position);
        viewHolder.namaBarang.setText(barang.namaProduk);
        viewHolder.hargaBarang.setText(barang.hargaProduk.toString());
        Picasso.get().load(barang.gambar).into(viewHolder.gambarBarang);
//        viewHolder.gambarBarang.setImageResource(barang.gambar.intValue());

        viewHolder.tambah.setVisibility(View.GONE);
        viewHolder.kurang.setVisibility(View.GONE);
        viewHolder.jumlah.setVisibility(View.GONE);
        viewHolder.tambahKeranjang.setVisibility(View.GONE);
        viewHolder.sebelumTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.tambah.setVisibility(View.VISIBLE);
                viewHolder.kurang.setVisibility(View.VISIBLE);
                viewHolder.jumlah.setVisibility(View.VISIBLE);
                viewHolder.tambahKeranjang.setVisibility(View.VISIBLE);
                viewHolder.sebelumTambah.setVisibility(View.GONE);
                viewHolder.jumlah.setText(String.valueOf(1));
            }
        });

        viewHolder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer a = Integer.parseInt(viewHolder.jumlah.getText().toString());
                a++;
                viewHolder.jumlah.setText(String.valueOf(a));
            }
        });

        viewHolder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer a = Integer.parseInt(viewHolder.jumlah.getText().toString());
                a--;
                if(a == 0) {
                    viewHolder.tambah.setVisibility(View.GONE);
                    viewHolder.kurang.setVisibility(View.GONE);
                    viewHolder.tambahKeranjang.setVisibility(View.GONE);
                    viewHolder.jumlah.setVisibility(View.GONE);
                    viewHolder.sebelumTambah.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.jumlah.setText(String.valueOf(a));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }
}
