package com.example.grocerytogo;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.adapter.BarangKeranjangSayaAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.BarangKeranjangSaya;
import com.example.grocerytogo.model.KeranjangItem;
import com.example.grocerytogo.model.ListKeranjang;
import com.example.grocerytogo.model.ListPesanan;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.PesananItem;
import com.example.grocerytogo.model.PesananSaya;
import com.example.grocerytogo.retrofit.GtgClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KeranjangSayaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeranjangSayaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KeranjangSayaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeranjangSayaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KeranjangSayaFragment newInstance(String param1, String param2) {
        KeranjangSayaFragment fragment = new KeranjangSayaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button tambah, pesan;
    private TextView ubah_lokasi;
    private RecyclerView DataBarangKeranjangSaya;
    private BarangKeranjangSayaAdapter barangKeranjangSayaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keranjang_saya, container, false);

        tambah = view.findViewById(R.id.btn_rambahproduk);
        pesan = view.findViewById(R.id.btn_pesan);
        ubah_lokasi = view.findViewById(R.id.ubah_lokasi);
        DataBarangKeranjangSaya = view.findViewById(R.id.ListKeranjangSaya);

        getKeranjangSaya();

        //Button Tambah Produk Pesanan, Fragment ke Fragment
        tambah.setOnClickListener(view2 -> {
            getFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        });

        //Button Pesan
        pesan.setOnClickListener(view2 -> {
            Intent in = new Intent(getActivity(), PesananSayaActivity.class);
            startActivity(in);
        });

        //Text View Ubah Lokasi
        ubah_lokasi.setOnClickListener(view2 -> {
            Intent in = new Intent(getActivity(), LokasiActivity.class);
            startActivity(in);
        });
        return view;
    }

    private void getKeranjangSaya(){
        SharedPreferences preferences = getContext().getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListKeranjang> call = gtgClient.getKeranjang(token, id);
        call.enqueue(new Callback<ListKeranjang>() {
            @Override
            public void onResponse(Call<ListKeranjang> call, Response<ListKeranjang> response) {
                ListKeranjang listKeranjang = response.body();
                ArrayList<BarangKeranjangSaya> keranjangSayas = new ArrayList<>();
                if(listKeranjang != null){
                    List<KeranjangItem> keranjangItems = listKeranjang.getKeranjang();
                    for(KeranjangItem item : keranjangItems){
                        BarangKeranjangSaya barangKeranjangSaya = new BarangKeranjangSaya(
                                item.getNamaBarang(),
                                item.getHargaBarang(),
                                api+item.getGambar(),
                                item.getJumlah(),
                                item.getIdBarang()
                        );
                        keranjangSayas.add(barangKeranjangSaya);
                    }
                    viewRecyclerView(keranjangSayas);
                }
            }

            @Override
            public void onFailure(Call<ListKeranjang> call, Throwable t) {

            }
        });
    }

    private void viewRecyclerView(ArrayList<BarangKeranjangSaya> listKeranjang){
        //Set Adapter dan Recycler View
        barangKeranjangSayaAdapter = new BarangKeranjangSayaAdapter();
        barangKeranjangSayaAdapter.setListBarangKeranjangSaya(listKeranjang);
        DataBarangKeranjangSaya.setAdapter(barangKeranjangSayaAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        DataBarangKeranjangSaya.setLayoutManager(layout);
    }
}