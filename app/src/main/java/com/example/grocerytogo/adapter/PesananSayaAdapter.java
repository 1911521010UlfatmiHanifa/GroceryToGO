package com.example.grocerytogo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.R;
import com.example.grocerytogo.model.PesananSaya;

import java.util.ArrayList;

public class PesananSayaAdapter
        extends RecyclerView.Adapter<PesananSayaAdapter.PesananSayaViewHolder>{

    public interface KlikPesananSaya{
        void onClick(View view, PesananSaya pesananSaya);
    }

    KlikPesananSaya listener = null;

    public void setListener(KlikPesananSaya listener) {
        this.listener = listener;
    }

    ArrayList<PesananSaya> listPesananSaya = new ArrayList<>();
    public void setListPesananSaya(ArrayList<PesananSaya> listPesananSaya){
        this.listPesananSaya = listPesananSaya;
    }

    public class PesananSayaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textIdPesanan, textTanggalPesanan, textStatusPesanan, textHargaPesanan;

        public PesananSayaViewHolder(@NonNull View itemView) {
            super(itemView);
            textIdPesanan = itemView.findViewById(R.id.textIdPesanan);
            textTanggalPesanan = itemView.findViewById(R.id.textTanggal);
            textStatusPesanan = itemView.findViewById(R.id.textStatusPesanan);
            textHargaPesanan = itemView.findViewById(R.id.textHarga);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onClick(view, listPesananSaya.get(getAdapterPosition()));
            }
        }
    }

    @NonNull
    @Override
    public PesananSayaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_pesanan_saya, parent, false);
        PesananSayaViewHolder viewHolder = new PesananSayaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PesananSayaViewHolder viewHolder, int position) {
        PesananSaya kategoriBarang = listPesananSaya.get(position);
        viewHolder.textIdPesanan.setText("Pesanan "+kategoriBarang.idPesanan.toString());
        viewHolder.textTanggalPesanan.setText(kategoriBarang.tanggalPesanan.toString());
        viewHolder.textStatusPesanan.setText(kategoriBarang.statusPesanan.toString());
        viewHolder.textHargaPesanan.setText(kategoriBarang.hargaPesanan.toString());
    }

    @Override
    public int getItemCount() {
        return listPesananSaya.size();
    }
}
