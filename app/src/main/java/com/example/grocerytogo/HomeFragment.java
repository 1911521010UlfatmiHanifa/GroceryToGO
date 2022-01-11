package com.example.grocerytogo;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.adapter.KategoriBarangAdapter;
import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.AuthData;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.BarangItem;
import com.example.grocerytogo.model.KategoriBarang;
import com.example.grocerytogo.model.KategoriItem;
import com.example.grocerytogo.model.ListBarang;
import com.example.grocerytogo.model.ListKategori;
import com.example.grocerytogo.retrofit.GtgClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private ProgressBar progressBar;
    private ArrayList<BarangBerdasarKategori> barangBerdasarKategoris;

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
        progressBar = view.findViewById(R.id.progressBar2);

        notifikasi.setOnClickListener(view2 -> {
            Intent in = new Intent(getActivity(), NotifikasiActivity.class);
            startActivity(in);
        });

        kategoriBarangAdapter = new KategoriBarangAdapter();

        SharedPreferences preferences = getContext().getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListKategori> call = gtgClient.getKategori(token);
        notifikasi.setEnabled(false);
        pencarian.setEnabled(false);
        DataBarang.setVisibility(View.GONE);
        DataKategoriBarang.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ListKategori>() {
            @Override
            public void onResponse(Call<ListKategori> call, Response<ListKategori> response) {
                ListKategori listKategori = response.body();
                ArrayList<KategoriBarang> kategoriBarangs = new ArrayList<>();
                if(listKategori != null){
                    notifikasi.setEnabled(true);
                    pencarian.setEnabled(true);
                    DataKategoriBarang.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    List<KategoriItem> kategoriItems = listKategori.getKategori();
                    for (KategoriItem item: kategoriItems){
                        KategoriBarang kategoriBarang = new KategoriBarang(
                                item.getId(),
                                item.getNamaKategori(),
                                api+item.getFoto()
                        );
                        kategoriBarangs.add(kategoriBarang);
                    }
                }
                kategoriBarangAdapter.setListKategori(kategoriBarangs);
            }

            @Override
            public void onFailure(Call<ListKategori> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Mengakses Server", Toast.LENGTH_SHORT).show();
                notifikasi.setEnabled(true);
                pencarian.setEnabled(true);
                DataKategoriBarang.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        });
        kategoriBarangAdapter.setListener(this);
        DataKategoriBarang.setAdapter(kategoriBarangAdapter);
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL, false);
        DataKategoriBarang.setLayoutManager(layout);

        pencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    DataBarang.setVisibility(View.GONE);
                    DataKategoriBarang.setVisibility(View.VISIBLE);
                    notFound.setVisibility(View.INVISIBLE);
                    pesan.setVisibility(View.INVISIBLE);
                }
                else{
                    Filter(editable.toString());
                    DataBarang.setVisibility(View.VISIBLE);
                    DataKategoriBarang.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        getBarang();

        return view;
    }

    private void getBarang(){
        SharedPreferences preferences = getContext().getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        Call<ListBarang> call = gtgClient.getBarang(token, id);
        notifikasi.setEnabled(false);
        pencarian.setEnabled(false);
        DataBarang.setVisibility(View.GONE);
        DataKategoriBarang.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ListBarang>() {
            @Override
            public void onResponse(Call<ListBarang> call, Response<ListBarang> response) {
                ListBarang listBarang = response.body();
                barangBerdasarKategoris = new ArrayList<>();
                if (listBarang != null) {
                    notifikasi.setEnabled(true);
                    pencarian.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    List<BarangItem> barangItems = listBarang.getBarang();
                    for (BarangItem item: barangItems) {
                        BarangBerdasarKategori barangBerdasarKategori = new BarangBerdasarKategori(
                                item.getId(),
                                item.getNamaBarang(),
                                item.getSatuanBarang(),
                                item.getKeterangan(),
                                api+item.getGambar(),
                                item.getIdKategori(),
                                item.getHargaBarang(),
                                item.getUkuranBarang()
                        );
                        barangBerdasarKategoris.add(barangBerdasarKategori);
                    }
                    if(barangBerdasarKategoris.size() == 0){
                        notFound.setVisibility(View.VISIBLE);
                        pesan.setVisibility(View.VISIBLE);
                    }else{
                        notFound.setVisibility(View.GONE);
                        pesan.setVisibility(View.GONE);
                    }
                    viewRecyclerView(barangBerdasarKategoris);
                }
            }

            @Override
            public void onFailure(Call<ListBarang> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
                notifikasi.setEnabled(true);
                pencarian.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void viewRecyclerView(ArrayList<BarangBerdasarKategori> listBarangKategori){
        barangBerdasarkanKategoriAdapter = new BarangBerdasarkanKategoriAdapter();
        barangBerdasarkanKategoriAdapter.setListBarang(listBarangKategori);
        barangBerdasarkanKategoriAdapter.setListener(this);
        DataBarang.setAdapter(barangBerdasarkanKategoriAdapter);
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        DataBarang.setLayoutManager(layout);
    }

    private void Filter(String ak) {
        ArrayList<BarangBerdasarKategori> listFilter = new ArrayList<>();
        for (BarangBerdasarKategori lis: barangBerdasarKategoris){
            if(lis.namaProduk.toLowerCase().contains(ak.toLowerCase(Locale.ROOT))){
                listFilter.add(lis);
                notFound.setVisibility(View.INVISIBLE);
                pesan.setVisibility(View.INVISIBLE);
            }
        }
        if(listFilter.isEmpty()){
            notFound.setVisibility(View.VISIBLE);
            pesan.setVisibility(View.VISIBLE);
        }
        barangBerdasarkanKategoriAdapter.setListFilter(listFilter);
        barangBerdasarkanKategoriAdapter.setListener(this);
    }

    @Override
    public void onClick(View view, KategoriBarang kategoriBarang) {
        Intent a = new Intent(getActivity(), BarangKategoriActivity.class);
        a.putExtra("id", kategoriBarang.idKategori);
        a.putExtra("nama", kategoriBarang.kategori);
        startActivity(a);
    }

    @Override
    public void onClick(View view, BarangBerdasarKategori barangBerdasarKategori) {
        Intent a = new Intent(getActivity(), DetailBarangActivity.class);
        a.putExtra("id", barangBerdasarKategori.idProduk);
        startActivity(a);
    }
}