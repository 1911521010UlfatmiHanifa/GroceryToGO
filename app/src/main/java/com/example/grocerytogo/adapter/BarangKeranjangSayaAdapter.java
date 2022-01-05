package com.example.grocerytogo.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.Koneksi;
import com.example.grocerytogo.R;
import com.example.grocerytogo.model.BarangKeranjangSaya;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarangKeranjangSayaAdapter
        extends RecyclerView.Adapter<BarangKeranjangSayaAdapter.BarangPesananSayaViewHolder>{

    public Context context;
    ArrayList<BarangKeranjangSaya> listBarangKeranjangSaya = new ArrayList<>();
    public void setListBarangKeranjangSaya(ArrayList<BarangKeranjangSaya> listBarangKeranjangSaya){
        this.listBarangKeranjangSaya = listBarangKeranjangSaya;
    }

    @NonNull
    @Override
    public BarangPesananSayaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_barang_keranjang_saya, parent, false);
        BarangPesananSayaViewHolder viewHolder = new BarangPesananSayaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BarangPesananSayaViewHolder ViewHolder, int position) {
        BarangKeranjangSaya barangPesananSaya = listBarangKeranjangSaya.get(position);
        ViewHolder.textNama.setText(barangPesananSaya.nama);
        ViewHolder.textHarga.setText(barangPesananSaya.harga.toString());
        ViewHolder.total.setText("Total : Rp. "+ barangPesananSaya.harga*barangPesananSaya.jumlah);
        Picasso.get().load(barangPesananSaya.gambar).into(ViewHolder.imageGambar);
        ViewHolder.textJumlah.setText("X"+barangPesananSaya.jumlah.toString());
//        ViewHolder.imageGambar.setImageResource(barangPesananSaya.gambar.intValue());
//        ViewHolder.tambah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Integer a = Integer.parseInt(ViewHolder.textJumlah.getText().toString());
//                a++;
//                ViewHolder.textJumlah.setText(String.valueOf(a));
//            }
//        });
//
        ViewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String api = context.getString(R.string.apiGTG);
                Koneksi koneksi = new Koneksi();
                GtgClient gtgClient = koneksi.setGtgClient(api);

                SharedPreferences preferences = context.getSharedPreferences("com.example.grocerytogo", Context.MODE_PRIVATE);
                String token = preferences.getString("TOKEN","");
                Integer id = Integer.valueOf(preferences.getString("id", ""));
                Integer barang = barangPesananSaya.id_barang;

                Call<Pesan> call = gtgClient.hapusBarang(token, id, barang);
                call.enqueue(new Callback<Pesan>() {
                    @Override
                    public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                        Pesan pesan = response.body();
                        if(pesan != null) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Hapus Barang Dari Keranjang")
                                    .setMessage("Apakah yakin menghapus barang ke keranjang?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            deleteItem(position);
                                            Toast.makeText(context.getApplicationContext(), "Berhasil Menghapus Barang", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
//                            listBarangKeranjangSaya.remove(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, listBarangKeranjangSaya.size());
//                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context.getApplicationContext(), "Logout Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Pesan> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "Gagal Mengakses Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void deleteItem(int index) {
        listBarangKeranjangSaya.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return listBarangKeranjangSaya.size();
    }

    public class BarangPesananSayaViewHolder extends RecyclerView.ViewHolder{

        TextView textNama, textHarga, textJumlah, total;
        ImageView imageGambar;
        Button tambah, hapus;
        CardView card;
        ConstraintLayout lay;

        public BarangPesananSayaViewHolder(@NonNull View itemView) {
            super(itemView);
             textNama = itemView.findViewById(R.id.nama);
             textHarga = itemView.findViewById(R.id.harga);
             imageGambar = itemView.findViewById(R.id.gambar);
             textJumlah = itemView.findViewById(R.id.textJumlah);
             total = itemView.findViewById(R.id.total);
//             tambah = itemView.findViewById(R.id.btn_tambah1);
             hapus = itemView.findViewById(R.id.btn_kurang1);
             card = itemView.findViewById(R.id.card);
             lay = itemView.findViewById(R.id.lay);
        }
    }
}
