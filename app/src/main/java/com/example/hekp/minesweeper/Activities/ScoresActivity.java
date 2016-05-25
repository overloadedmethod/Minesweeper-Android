package com.example.hekp.minesweeper.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.Interfaces.IOnListItemClickListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScoresActivity extends FragmentActivity implements OnMapReadyCallback,IOnListItemClickListener {

    GoogleMap _map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        SupportMapFragment map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(_map == null)
            _map = googleMap;
        _map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        _map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onItemClick(double lat, double lng) {
        _map.clear();
        if(lat != -1 || lng != -1) {
            _map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10);
            _map.animateCamera(cameraUpdate);
        }
    }
}
