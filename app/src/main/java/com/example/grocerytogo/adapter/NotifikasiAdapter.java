package com.example.grocerytogo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.R;
import com.example.grocerytogo.model.Notifikasi;

import java.util.ArrayList;

public class NotifikasiAdapter
        extends RecyclerView.Adapter<NotifikasiAdapter.NotifikasiViewHolder> {

    ArrayList<Notifikasi> listNotifikasi = new ArrayList<>();
    public void setListNotifikasi(ArrayList<Notifikasi> listNotifikasi){
        this.listNotifikasi = listNotifikasi;
    }

    @NonNull
    @Override
    public NotifikasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_notifikasi, parent, false);
        NotifikasiViewHolder viewHolder = new NotifikasiViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiViewHolder viewHolder, int position) {
        Notifikasi notifikasi = listNotifikasi.get(position);
        viewHolder.textId.setText(notifikasi.idNotif.toString());
        viewHolder.textNotif.setText(notifikasi.notif.toString());
        viewHolder.textWaktu.setText(notifikasi.waktu.toString());
        viewHolder.imageLogo.setImageResource(notifikasi.logo.intValue());
    }

    @Override
    public int getItemCount() {
        return listNotifikasi.size();
    }

    public class NotifikasiViewHolder extends RecyclerView.ViewHolder {

        TextView textId, textNotif, textWaktu;
        ImageView imageLogo;

        public NotifikasiViewHolder(@NonNull View itemView) {

            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textNotif = itemView.findViewById(R.id.textNotif);
            textWaktu = itemView.findViewById(R.id.textNamaKategori);
            imageLogo = itemView.findViewById(R.id.imageNotif);

        }

    }

}
