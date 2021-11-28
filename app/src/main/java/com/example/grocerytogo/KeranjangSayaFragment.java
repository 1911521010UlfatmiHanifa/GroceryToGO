package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.BarangKeranjangSayaAdapter;
import com.example.grocerytogo.model.BarangKeranjangSaya;

import java.util.ArrayList;

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

        //Set Adapter dan Recycler View
        barangKeranjangSayaAdapter = new BarangKeranjangSayaAdapter();
        barangKeranjangSayaAdapter.setListBarangKeranjangSaya(getDataBarangKeranjangSaya());
        DataBarangKeranjangSaya.setAdapter(barangKeranjangSayaAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        DataBarangKeranjangSaya.setLayoutManager(layout);

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

    //Inisialisasi AuthData
    public ArrayList<BarangKeranjangSaya> getDataBarangKeranjangSaya() {
        ArrayList<BarangKeranjangSaya> list = new ArrayList<>();
        list.add(new BarangKeranjangSaya("Daging", "80.000", R.drawable.contoh4));
        list.add(new BarangKeranjangSaya("Jus", "50.000", R.drawable.contoh3));
        list.add(new BarangKeranjangSaya("Buah", "20.000", R.drawable.contoh1));
        list.add(new BarangKeranjangSaya("Daging", "80.000", R.drawable.contoh4));
        list.add(new BarangKeranjangSaya("Jus", "50.000", R.drawable.contoh3));
        list.add(new BarangKeranjangSaya("Buah", "20.000", R.drawable.contoh1));
        return list;
    }
}