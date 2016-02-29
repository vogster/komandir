package com.korovkin.komandir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.korovkin.komandir.R;

import java.util.ArrayList;

/**
 * Created by Nik on 28.02.2016.
 */
public class ResultActivity extends AppCompatActivity implements RoutingListener, OnMapReadyCallback {

    public static final String KEY_FROMLATLNG = "fromLatLng";
    public static final String KEY_TOLATLNG = "toLatLng";

    private LatLng fromLatLng;
    private LatLng toLatLng;

    private MapView mapView;
    private GoogleMap mMap;

    private ArrayList<Polyline> polylines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        Intent intent = getIntent();
        if (intent.hasExtra(KEY_FROMLATLNG) && intent.hasExtra(KEY_TOLATLNG)) {
            fromLatLng = intent.getParcelableExtra(KEY_FROMLATLNG);
            toLatLng = intent.getParcelableExtra(KEY_TOLATLNG);
        } else {
            finish();
        }

        mapView = (MapView) findViewById(R.id.mapviewResult);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        polylines = new ArrayList<>();
        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(fromLatLng, toLatLng)
//                .key("AIzaSyBF7DydQRNXzy4WSIgYlNcqvp7-_z3Hakw")
                .build();
        routing.execute();

    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.d("LOG", e.toString());
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int shortestRouteIndex) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(fromLatLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        mMap.moveCamera(center);


        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <arrayList.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = android.R.color.black;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(colorIndex));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(arrayList.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(fromLatLng);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(toLatLng);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        mMap.addMarker(options);
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
