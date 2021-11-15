package com.example.grocerytogo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.R;
import com.example.grocerytogo.model.DetailBarangPesanan;

import java.util.ArrayList;

public class DetailBarangPesananAdapter
        extends RecyclerView.Adapter<DetailBarangPesananAdapter.DetailBarangPesananViewHolder> {

    ArrayList<DetailBarangPesanan> listDetailBarangPesanan = new ArrayList<>();
    public void setListDetailBarangPesanan(ArrayList<DetailBarangPesanan> listDetailBarangPesanan){
        this.listDetailBarangPesanan = listDetailBarangPesanan;
    }

    @NonNull
    @Override
    public DetailBarangPesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_barang_pesanan, parent, false);
        DetailBarangPesananViewHolder viewHolder = new DetailBarangPesananViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailBarangPesananViewHolder viewHolder, int position) {
        DetailBarangPesanan detailBarangPesanan = listDetailBarangPesanan.get(position);
        viewHolder.textNamaProduk.setText(detailBarangPesanan.namaProduk.toString());
        viewHolder.textHargaProduk.setText(detailBarangPesanan.hargaProduk.toString());
        viewHolder.textKuantitas.setText(detailBarangPesanan.kuantitas.toString());
        viewHolder.imageGambarProduk.setImageResource(detailBarangPesanan.gambarProduk.intValue());
    }

    @Override
    public int getItemCount() {
        return listDetailBarangPesanan.size();
    }

    public class DetailBarangPesananViewHolder extends RecyclerView.ViewHolder{

        TextView textNamaProduk, textHargaProduk, textKuantitas, jumlah;
        ImageView imageGambarProduk;

        public DetailBarangPesananViewHolder(@NonNull View itemView) {
            super(itemView);
            textHargaProduk = itemView.findViewById(R.id.textHargaProduk);
            textKuantitas = itemView.findViewById(R.id.textKuantitas);
            textNamaProduk = itemView.findViewById(R.id.textNamaProduk);
            imageGambarProduk = itemView.findViewById(R.id.imageProduk);
        }
    }
}
