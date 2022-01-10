package com.example.grocerytogo;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.grocerytogo.databinding.ActivityLihatLocBinding;

public class LihatLocActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityLihatLocBinding binding;
    private double lat, slong;
    private Marker mark;
    private CardView back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLihatLocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = getIntent().getFloatExtra("Lat", 0);
        slong = getIntent().getFloatExtra("Long", 0);
        Toast.makeText(getApplicationContext(), String.valueOf(lat), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), String.valueOf(slong), Toast.LENGTH_SHORT).show();

        back2 = findViewById(R.id.kembaliii);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(lat, slong);
        mark = mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi Pesanan"));
        mark.setPosition(latLng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(update);
    }

}