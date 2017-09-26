package co.id.franknco.ui.nearby;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        ButterKnife.bind(this);

        setupToolbar();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

//    private void fetchMarkerOnMaps() {
//        String url = URL_TOP_250;   // set String url
//        // Volley's json array request object
//        JsonArrayRequest req = new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
//                        if (response.length() > 0) {
//                            // looping through json and adding to movies list
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject movieObj = response.getJSONObject(i);
//
//                                    rank = movieObj.getInt("id");
//                                    title = movieObj.getString("nama");
//                                    alamat = movieObj.getString("alamat");
//                                    detail = movieObj.getString("detail");
//                                    urlpic = movieObj.getString("link_gambar");
//                                    lat = movieObj.getDouble("lat");
//                                    lng = movieObj.getDouble("long");
//
//                                    LatLng resMark = new LatLng(lat, lng);
//                                    resspot = mMap.addMarker(new MarkerOptions()
//                                            .title(title)
//                                            .snippet(alamat)
//                                            .icon(BitmapDescriptorFactory
//                                                    .fromResource(R.drawable.marker))
//                                            .position(resMark));
//
//                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                                        @Override
//                                        public boolean onMarkerClick(Marker marker) {
//                                            return false;
//                                        }
//                                    });
//                                } catch (JSONException e) {
//                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
//                                }
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Server Error: " + error.getMessage());
//                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Error Retrieving Data\nPull Down to Refresh", Toast.LENGTH_LONG).show();
//            }
//        });
//        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(req);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng cameraBounds = new LatLng(-7.800077, 110.374611);
        CameraPosition cp = new CameraPosition.Builder()
                .target(cameraBounds)
                .zoom(13)
                .bearing(0)
                .tilt(30)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
    }
}
