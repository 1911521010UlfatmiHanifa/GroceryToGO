package com.example.grocerytogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grocerytogo.adapter.AvatarAdapter;
import com.example.grocerytogo.adapter.BarangBerdasarkanKategoriAdapter;
import com.example.grocerytogo.model.Avatar;
import com.example.grocerytogo.model.AvatarItem;
import com.example.grocerytogo.model.BarangBerdasarKategori;
import com.example.grocerytogo.model.ListAvatar;
import com.example.grocerytogo.model.Pesan;
import com.example.grocerytogo.model.UserItem;
import com.example.grocerytogo.retrofit.GtgClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarActivity extends AppCompatActivity implements AvatarAdapter.KlikAvatar{

    private AvatarAdapter avatarAdapter;
    private RecyclerView DataAvatar;
    private ImageView back;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        DataAvatar = findViewById(R.id.ls_avatar);
        back = findViewById(R.id.imageView4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(AvatarActivity.this, EditDataDiriActivity.class);
                startActivity(a);
            }
        });

        getAvatar();
    }

    private void getAvatar(){
        String api = getString(R.string.apiGTG);
        Koneksi koneksi = new Koneksi();
        GtgClient gtgClient = koneksi.setGtgClient(api);

        SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

        Call<ListAvatar> call = gtgClient.getAvatar(token);
        call.enqueue(new Callback<ListAvatar>() {
            @Override
            public void onResponse(Call<ListAvatar> call, Response<ListAvatar> response) {
                ListAvatar listAvatar = response.body();
                ArrayList<Avatar> avatars = new ArrayList<>();
                if(listAvatar != null){
                    List<AvatarItem> avatarItems = listAvatar.getAvatar();
                    for(AvatarItem item: avatarItems){
                        Avatar avatar = new Avatar(
                            item.getGambar(),
                            api
                        );
//                        Toast.makeText(getApplicationContext(), api+item.getGambar(), Toast.LENGTH_SHORT).show();
                        avatars.add(avatar);
                    }
                    viewRecyclerView(avatars);
                }
            }

            @Override
            public void onFailure(Call<ListAvatar> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal mengakses data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewRecyclerView(ArrayList<Avatar> listAvatar){
        avatarAdapter = new AvatarAdapter();
        avatarAdapter.setListAvatar(listAvatar);
        avatarAdapter.setListener(this);
        DataAvatar.setAdapter(avatarAdapter);
//        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layout = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        DataAvatar.setLayoutManager(layout);
    }

    @Override
    public void onClick(View view, Avatar avatar) {
        foto = avatar.gambar;
//        Toast.makeText(getApplicationContext(), foto, Toast.LENGTH_SHORT).show();
        Intent a = new Intent(AvatarActivity.this, EditDataDiriActivity.class);
        a.putExtra("GR", foto);
        startActivity(a);
    }

}