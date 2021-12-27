package com.example.grocerytogo;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.grocerytogo.model.AuthClass;
import com.example.grocerytogo.model.AuthData;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.UserClass;
import com.example.grocerytogo.model.UserItem;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SettingFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SettingFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public SettingFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SettingFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SettingFragment newInstance(String param1, String param2) {
//        SettingFragment fragment = new SettingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    private CardView lihatDataDiri, ubahKataSandi, pesananSaya, keluar, cd;
    private ProgressBar progressBar;
    private ImageView foto;
    private TextView nope, username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);

        lihatDataDiri = view.findViewById(R.id.LihatProfil);
        ubahKataSandi = view.findViewById(R.id.UbahKataSandi);
        pesananSaya = view.findViewById(R.id.PesananSaya);
        keluar = view.findViewById(R.id.Keluar);
        progressBar = view.findViewById(R.id.progressBar6);
        nope = view.findViewById(R.id.textView14);
        username = view.findViewById(R.id.textView12);
        foto = view.findViewById(R.id.imageView2);
        cd = view.findViewById(R.id.cardView);

        lihatDataDiri.setVisibility(View.GONE);
        ubahKataSandi.setVisibility(View.GONE);
        pesananSaya.setVisibility(View.GONE);
        keluar.setVisibility(View.GONE);
        nope.setVisibility(View.GONE);
        username.setVisibility(View.GONE);
        foto.setVisibility(View.GONE);
        cd.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        getData();

        //Card View Lihat AuthData Diri
        lihatDataDiri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LihatDataDiriActivity.class);
                startActivity(i);
            }
        });

        //Card View Ubah Kata Sandi
        ubahKataSandi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UbahKataSandiActivity.class);
                startActivity(i);
            }
        });

        //Card View Pesanan Saya
        pesananSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PesananSayaActivity.class);
                startActivity(i);
            }
        });

        //Card View Keluar
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a = new AlertDialog.Builder((getActivity()));
                        a.setIcon(R.drawable.ic_baseline_warning_24);
                        a.setTitle("Keluar");
                        a.setMessage("Apakah anda yakin ingin keluar?");
                        ((AlertDialog.Builder) a).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        });
                        ((AlertDialog.Builder) a).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        });
                        a.create();
                        a.show();
            }
        });
        return view;
    }

    private void getData(){

        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getContext().getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");
        Integer id = Integer.valueOf(preferences.getString("id", ""));
//        Toast.makeText(getApplicationContext(), id.toString(), Toast.LENGTH_SHORT).show();

        Call<UserClass> call = gtgClient.getUser(token, id);

        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
//                Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
                UserClass userClass = response.body();
                if(userClass != null){
                    lihatDataDiri.setVisibility(View.VISIBLE);
                    ubahKataSandi.setVisibility(View.VISIBLE);
                    pesananSaya.setVisibility(View.VISIBLE);
                    keluar.setVisibility(View.VISIBLE);
                    nope.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    foto.setVisibility(View.VISIBLE);
                    cd.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    List<UserItem> userItems = userClass.getUser();
                    for (UserItem item: userItems) {
                        Picasso.get().load(api+item.getFoto()).into(foto);
                        nope.setText(item.getNoHp());
                        username.setText(item.getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserClass> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Akses Server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void logout(){
        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getContext().getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

        Call<Pesan> call = gtgClient.logout(token);
        call.enqueue(new Callback<Pesan>() {
            @Override
            public void onResponse(Call<Pesan> call, Response<Pesan> response) {
                Pesan pesan = response.body();
                if(pesan != null) {
                    Intent in = new Intent(getActivity(), MainActivity.class);
                    startActivity(in);
                }else {
                    Toast.makeText(getContext(), "Logout Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pesan> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Mengakses Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}