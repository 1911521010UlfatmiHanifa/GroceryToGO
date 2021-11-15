package com.example.grocerytogo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.R;
import com.example.grocerytogo.model.BarangKeranjangSaya;

import java.util.ArrayList;

public class BarangKeranjangSayaAdapter
        extends RecyclerView.Adapter<BarangKeranjangSayaAdapter.BarangPesananSayaViewHolder>{

    ArrayList<BarangKeranjangSaya> listBarangKeranjangSaya = new ArrayList<>();
    public void setListBarangKeranjangSaya(ArrayList<BarangKeranjangSaya> listBarangKeranjangSaya){
        this.listBarangKeranjangSaya = listBarangKeranjangSaya;
    }

    @NonNull
    @Override
    public BarangPesananSayaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_barang_keranjang_saya, parent, false);
        BarangPesananSayaViewHolder viewHolder = new BarangPesananSayaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BarangPesananSayaViewHolder ViewHolder, int position) {
        BarangKeranjangSaya barangPesananSaya = listBarangKeranjangSaya.get(position);
        ViewHolder.textNama.setText(barangPesananSaya.nama.toString());
        ViewHolder.textHarga.setText(barangPesananSaya.harga.toString());
        ViewHolder.imageGambar.setImageResource(barangPesananSaya.gambar.intValue());
        ViewHolder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer a = Integer.parseInt(ViewHolder.textJumlah.getText().toString());
                a++;
                ViewHolder.textJumlah.setText(String.valueOf(a));
            }
        });

        ViewHolder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer a = Integer.parseInt(ViewHolder.textJumlah.getText().toString());
                a--;
                if(a <= 0) {
                    ViewHolder.tambah.setVisibility(View.GONE);
                    ViewHolder.kurang.setVisibility(View.GONE);
                    ViewHolder.textJumlah.setVisibility(View.GONE);
                    ViewHolder.card.setVisibility(View.GONE);
                    ViewHolder.imageGambar.setVisibility(View.GONE);
                    ViewHolder.textHarga.setVisibility(View.GONE);
                    ViewHolder.textNama.setVisibility(View.GONE);
                    ViewHolder.lay.setVisibility(View.GONE);
                }else{
                    ViewHolder.textJumlah.setText(String.valueOf(a));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBarangKeranjangSaya.size();
    }

    public class BarangPesananSayaViewHolder extends RecyclerView.ViewHolder{

        TextView textNama, textHarga, textJumlah;
        ImageView imageGambar;
        Button tambah, kurang;
        CardView card;
        ConstraintLayout lay;

        public BarangPesananSayaViewHolder(@NonNull View itemView) {
            super(itemView);
             textNama = itemView.findViewById(R.id.nama);
             textHarga = itemView.findViewById(R.id.harga);
             imageGambar = itemView.findViewById(R.id.gambar);
             textJumlah = itemView.findViewById(R.id.textJumlah);
             tambah = itemView.findViewById(R.id.btn_tambah1);
             kurang = itemView.findViewById(R.id.btn_kurang1);
             card = itemView.findViewById(R.id.card);
             lay = itemView.findViewById(R.id.lay);
        }
    }
}
