package sk.tuke.vmir;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.Manifest;

import java.util.ArrayList;
import java.util.List;

import sk.tuke.vmir.databinding.ActivityMainBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Marker> markers;
    private Polyline polyline;
    private Polyline shortestPolyline;
    private ActivityMainBinding binding;
    private final int PERMISSION_LOCATION_ID = 1000;
    private Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        markers = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest request = LocationRequest.create();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_ID);
            return;
        }

        locationClient.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                super.onLocationResult(result);

                myLocation = result.getLastLocation();

                LatLng position = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

//                mMap.clear();

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title("you")
                );

//                drawPolylines(position);
//                drawMarkers();

//                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            }
        }, null);
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Marker newMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                );
                markers.add(newMarker);
                if (markers.size() > 10) {
                    Marker oldestMarker = markers.get(0);
                    oldestMarker.remove();
                    markers.remove(0);
                }

                PolylineOptions polylineOptions = new PolylineOptions();

                for (Marker marker : markers) {
                    polylineOptions.add(marker.getPosition());
                }

                if (polyline != null) {
                    polyline.remove();
                }
                polylineOptions.add(newMarker.getPosition());
                polyline = mMap.addPolyline(polylineOptions);

//                if (myLocation != null) {
//                    LatLng position = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                    polylineOptions.add(position);
//
//                    polylineOptions.width(5).color(Color.RED);
//
//                }
            }
        });
    }

    public void calculate(View view) {
        if (myLocation == null) {
            return;
        }

        LatLng myPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        double min = Double.MAX_VALUE;
        Marker minMarker = null;
        for (Marker marker : markers) {
            double distanceInMeters = calculateDistanceBetweenLatLngObjects(myPosition, marker.getPosition());
            if (distanceInMeters < min) {
                minMarker = marker;
                min = distanceInMeters;
            }

        }

        if (shortestPolyline != null) {
            shortestPolyline.remove();
        }

        PolylineOptions polylineOptions = new PolylineOptions();

        polylineOptions.add(minMarker.getPosition());
        polylineOptions.color(Color.GREEN);

        polylineOptions.add(myPosition);
        shortestPolyline = mMap.addPolyline(polylineOptions);

    }

    public double calculateDistanceBetweenLatLngObjects(LatLng latLng1, LatLng latLng2) {
        // Convert the LatLng objects to radians.
        double latitude1 = latLng1.latitude * Math.PI / 180.0;
        double longitude1 = latLng1.longitude * Math.PI / 180.0;
        double latitude2 = latLng2.latitude * Math.PI / 180.0;
        double longitude2 = latLng2.longitude * Math.PI / 180.0;

        // Calculate the distance between the two points using the Haversine formula.
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((latitude2 - latitude1) / 2), 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin((longitude2 - longitude1) / 2), 2)));

        // Convert the distance from radians to meters.
        distance *= 6371000.0;

        return distance;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_LOCATION_ID: {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        } else {
                            Toast.makeText(this, "Unable to get proper permission.", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_ID);
                        }
                    }
                }
                break;
            }
        }
    }

    public void clear(View view) {
        if (markers.size() == 0) {
            return;
        }

        Marker oldestMarker = markers.get(0);
        oldestMarker.remove();
        markers.remove(0);

        PolylineOptions polylineOptions = new PolylineOptions();

        for (Marker marker : markers) {
            polylineOptions.add(marker.getPosition());
        }

        if (polyline != null) {
            polyline.remove();
        }
        polyline = mMap.addPolyline(polylineOptions);
    }
}