package com.example.grocerytogo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.adapter.KategoriBarangAdapter;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.KategoriBarang;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements KategoriBarangAdapter.KlikKategoriBarang, BarangBerdasarkanKategoriAdapter.KlikBarang{

    private BottomNavigationView navigasi;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private ImageView notifikasi, notFound;
    private RecyclerView DataKategoriBarang, DataBarang;
    private TextView pesan;
    private EditText pencarian;
    private KategoriBarangAdapter kategoriBarangAdapter;
    private BarangBerdasarkanKategoriAdapter barangBerdasarkanKategoriAdapter;
    ArrayList<BarangBerdasarKategori> listw = new ArrayList<>();
    ArrayList<BarangBerdasarKategori> listFilter = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        notifikasi = view.findViewById(R.id.notifikasi);
        pencarian = view.findViewById(R.id.pencarian);
        notFound = view.findViewById(R.id.notFound);
        pesan = view.findViewById(R.id.pesan);
        DataKategoriBarang = view.findViewById(R.id.ListKategoriBarang);
        DataBarang = view.findViewById(R.id.ListBarang);

        //Image Notifikasi
        notifikasi.setOnClickListener(view2 -> {
            Intent in = new Intent(getActivity(), NotifikasiActivity.class);
            startActivity(in);
        });

        //Set Adapter Barang dan Recycler View
        barangBerdasarkanKategoriAdapter = new BarangBerdasarkanKategoriAdapter(listw);
        barangBerdasarkanKategoriAdapter.setListBarang(getDataPencarian());
        DataBarang.setAdapter(barangBerdasarkanKategoriAdapter);
        barangBerdasarkanKategoriAdapter.setListener(this);
        GridLayoutManager layout2 = new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL, false);
        DataBarang.setLayoutManager(layout2);

        //Pencarian
        pencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listFilter.clear();
                if(editable.toString().isEmpty()){
                    DataBarang.setAdapter(new BarangBerdasarkanKategoriAdapter(listw));
                    barangBerdasarkanKategoriAdapter.notifyDataSetChanged();
                    DataBarang.setVisibility(View.GONE);
                    DataKategoriBarang.setVisibility(View.VISIBLE);
                    notFound.setVisibility(View.INVISIBLE);
                    pesan.setVisibility(View.INVISIBLE);
                }
                else{
                    Filter(editable.toString());
                    DataBarang.setVisibility(View.VISIBLE);
                    DataKategoriBarang.setVisibility(View.GONE);
                }
            }
        });

        //Set Adapter Kategori dan Recycler View
        kategoriBarangAdapter = new KategoriBarangAdapter();
        kategoriBarangAdapter.setListKategori(getDataKategoriBarang());
        kategoriBarangAdapter.setListener(this);
        DataKategoriBarang.setAdapter(kategoriBarangAdapter);
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL, false);
        DataKategoriBarang.setLayoutManager(layout);
        return view;
    }

    private void Filter(String ak) {
        for (BarangBerdasarKategori lis:listw){
            if(lis.namaProduk.toString().toLowerCase().contains(ak.toString().toLowerCase(Locale.ROOT))){
                listFilter.add(lis);
                notFound.setVisibility(View.INVISIBLE);
                pesan.setVisibility(View.INVISIBLE);
            }
            barangBerdasarkanKategoriAdapter.setListener(this);
        }
        if(listFilter.isEmpty()){
            notFound.setVisibility(View.VISIBLE);
            pesan.setVisibility(View.VISIBLE);
        }
        DataBarang.setAdapter(new BarangBerdasarkanKategoriAdapter(listFilter));
        barangBerdasarkanKategoriAdapter.notifyDataSetChanged();
        barangBerdasarkanKategoriAdapter.setListener(this);
    }

    //Inisialisasi Data
    public ArrayList<KategoriBarang> getDataKategoriBarang(){
        ArrayList<KategoriBarang> list = new ArrayList<>();
        list.add(new KategoriBarang(R.drawable.contoh1, "Buah-Buahan"));
        list.add(new KategoriBarang(R.drawable.contoh2, "Sarapan"));
        list.add(new KategoriBarang(R.drawable.contoh3, "Minuman"));
        list.add(new KategoriBarang(R.drawable.contoh4, "Daging"));
        list.add(new KategoriBarang(R.drawable.contoh5, "Makanan Ringan"));
        list.add(new KategoriBarang(R.drawable.contoh1, "Buah-Buahan"));
        list.add(new KategoriBarang(R.drawable.contoh2, "Sarapan"));
        list.add(new KategoriBarang(R.drawable.contoh3, "Minuman"));
        list.add(new KategoriBarang(R.drawable.contoh4, "Daging"));
        list.add(new KategoriBarang(R.drawable.contoh5, "Makanan Ringan"));
        return list;
    }

    public ArrayList<BarangBerdasarKategori> getDataPencarian(){
        listw.add(new BarangBerdasarKategori("Daging", "200000", R.drawable.contoh4));
        listw.add(new BarangBerdasarKategori("Ciki", "6000", R.drawable.contoh5));
        listw.add(new BarangBerdasarKategori("Buah", "40000", R.drawable.contoh1));
        listw.add(new BarangBerdasarKategori("Jus", "20000", R.drawable.contoh3));
        listw.add(new BarangBerdasarKategori("Daging Ayam", "200000", R.drawable.contoh4));
        listw.add(new BarangBerdasarKategori("Ciki", "6000", R.drawable.contoh5));
        listw.add(new BarangBerdasarKategori("Buah", "40000", R.drawable.contoh1));
        listw.add(new BarangBerdasarKategori("Jus", "20000", R.drawable.contoh3));
        listw.add(new BarangBerdasarKategori("Daging", "200000", R.drawable.contoh4));
        listw.add(new BarangBerdasarKategori("Ciki", "6000", R.drawable.contoh5));
        listw.add(new BarangBerdasarKategori("Buah", "40000", R.drawable.contoh1));
        listw.add(new BarangBerdasarKategori("Jus", "20000", R.drawable.contoh3));
        return listw;
    }

    @Override
    public void onClick(View view, KategoriBarang kategoriBarang) {
        Intent a = new Intent(getActivity(), BarangKategoriActivity.class);
        String namaKategori = kategoriBarang.kategori;
        a.putExtra("nama", namaKategori);
        startActivity(a);
    }

    @Override
    public void onClick(View view, BarangBerdasarKategori barangBerdasarKategori) {
        Intent a = new Intent(getActivity(), DetailBarangActivity.class);
        String namaBarang = barangBerdasarKategori.namaProduk;
        a.putExtra("nama", namaBarang);
        int gambarBarang = barangBerdasarKategori.logoProduk;
        a.putExtra("gambar", gambarBarang);
        String harga = barangBerdasarKategori.hargaProduk;
        a.putExtra("harga", harga);
        startActivity(a);
    }
}