package com.example.grocerytogo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

    private CardView lihatDataDiri, ubahKataSandi, pesananSaya, keluar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);

        lihatDataDiri = view.findViewById(R.id.LihatProfil);
        ubahKataSandi = view.findViewById(R.id.UbahKataSandi);
        pesananSaya = view.findViewById(R.id.PesananSaya);
        keluar = view.findViewById(R.id.Keluar);

        //Card View Lihat Data Diri
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

        //Card View Kelua
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
                                Intent in = new Intent(getActivity(), MainActivity.class);
                                startActivity(in);
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
}