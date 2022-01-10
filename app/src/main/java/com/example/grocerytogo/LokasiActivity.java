package com.example.grocerytogo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.grocerytogo.databinding.ActivityLokasiBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.SphericalUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;

public class LokasiActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityLokasiBinding binding;
    private FloatingActionButton fab;
    private FusedLocationProviderClient mLocationClient;
    private LocationRequest locationRequest;
    private EditText pencarian;
    private LatLng latLng;
    private Marker mark;
    private String alamat;
    private double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLokasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        lokasi = findViewById(R.id.simpan);

        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> mylist = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
                    alamat = mylist.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LatLng sydney = new LatLng(-0.3030852, 100.36723);
                distance = SphericalUtil.computeDistanceBetween(sydney, latLng);

                SharedPreferences preferences = getSharedPreferences("com.example.grocerytogo", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("LATITUDE", (float) latLng.latitude);
                editor.putFloat("LONGITUDE", (float) latLng.longitude);
                editor.putString("DISTANCE", String.valueOf(distance/1000));
                editor.putString("ADDRESS", alamat);
                editor.apply();

                // in below line we are displaying a toast
                // message with distance between two locations.
                // in our distance we are dividing it by 1000 to
                // make in km and formatting it to only 2 decimal places.
                Toast.makeText(getApplicationContext(), "Distance between Sydney and Brisbane is \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_SHORT).show();

//                Fragment fragment = new KeranjangSayaFragment();
//                FragmentManager fragmentManager= getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
//                Fragment fragment = new KeranjangSayaFragment();

                onBackPressed();
//                Toast.makeText(getApplicationContext(), (int) Llongitude, Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getApplicationContext(), KeranjangSayaFragment.class);
//                i.putExtra("latitude", Llatitude);
//                i.putExtra("longitude", Llongitude);
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new KeranjangSayaFragment()).commit();
//                getFragmentManager().beginTransaction().replace(R.id.frame, new KeranjangSayaFragment()).commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new KeranjangSayaFragment()).commit();
            }
        });

        pencarian = findViewById(R.id.pencarian);

        pencarian.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    findOnMap();

                }
                return false;
            }
        });
    }

    private CardView lokasi;
    double Llatitude, Llongitude;

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
    }

    public void findOnMap(){
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> mylist = geocoder.getFromLocationName(pencarian.getText().toString(),1);
            if(mylist.isEmpty()){
                Toast.makeText(getApplicationContext(), "Tidak Ada", Toast.LENGTH_SHORT).show();
            }else {
                Address address = mylist.get(0);
                String locality = address.getLocality();
                double lat = address.getLatitude();
                double lon = address.getLongitude();

                latLng = new LatLng(lat, lon);
//                marker.position(latLng).title(pencarian.getText().toString());
                mark.setPosition(latLng);
                mark.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.logo_gtg));
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//                mMap.addMarker(new MarkerOptions().position(latLng).title(pencarian.getText().toString()));
                mMap.animateCamera(update);
//            Toast.makeText(getApplicationContext(), (int) lat, Toast.LENGTH_SHORT).show();

                goToLocation(lat, lon, 15);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void goToLocation(double latitude,double longitude,int zoom){
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.moveCamera(update);
    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(LokasiActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(LokasiActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

//        Toast.makeText(ArahActivity.this, getKunciAPI(), Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(LokasiActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        Llatitude = locationResult.getLocations().get(index).getLatitude();
                                        Llongitude = locationResult.getLocations().get(index).getLongitude();
                                        Geocoder geocoder = new Geocoder(getApplicationContext());
                                        try {
                                            List<Address> mylist = geocoder.getFromLocation(Llatitude, Llongitude,1);
                                            alamat = mylist.get(0).getAddressLine(0);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        latLng = new LatLng(Llatitude,Llongitude);
//                                        marker.position(latLng).title("Lokasi Anda");
                                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,15);
                                        mark = mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi Anda")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_gtg)).anchor(0.5f, 1)
                                                .draggable(true));
                                        mMap.animateCamera(update);
//                                        AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
}