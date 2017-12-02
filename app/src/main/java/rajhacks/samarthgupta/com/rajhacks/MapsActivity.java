package rajhacks.samarthgupta.com.rajhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    public static String baseUrl = "http://192.168.43.189:5000";
    public static Slots[] list;
    public static double maxBid = 0.0;
    public static int startTimeFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Snackbar.make(findViewById(R.id.map), "Select a billboard", BaseTransientBottomBar.LENGTH_INDEFINITE).show();

        mapFragment.getMapAsync(this);


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
        LatLng current = new LatLng(24.5975644, 73.7383663);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(current) // Sets the center of the map to
                .zoom(10)                   // Sets the zoom
                .bearing(10) // Sets the orientation of the camera to east
                .tilt(10)    // Sets the tilt of the camera to 30 degrees
                .build();    // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                cameraPosition));
        mMap.addMarker(new MarkerOptions().position(current).title("Your Location"));

        setMarker(new LatLng(24.4354644, 73.7325463), "BB101", 1);
        setMarker(new LatLng(24.324644, 73.7324323), "BB102", 2);
        setMarker(new LatLng(24.2134644, 73.11235463), "BB103", 3);
        mMap.setOnMarkerClickListener(this);

    }

    void setMarker(LatLng latLng, String title, int i) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bbicon);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title).icon(icon)).setTag(i);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if (!marker.getTitle().equals("Your Location")) {
            Log.d("TAG","IN");

            Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("TAG",response);
                    list = new GsonBuilder().create().fromJson(response, Slots[].class);
                    startActivity(new Intent(MapsActivity.this, BillboardActivity.class));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));


        }

        return true;
    }


    public static class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            BillBoardFragment frag = new BillBoardFragment();
            startTimeFrag = list[position].getFromTime();
            bundle.putInt("from", list[position].getFromTime());
            bundle.putInt("to", list[position].getToTime());
            bundle.putString("baseUrl", list[position].getUrlImage());
            bundle.putDouble("est", list[position].getEstTraffic());
            bundle.putString("mbid",list[position].getMaxBid());
            maxBid = Double.parseDouble(list[position].getMaxBid());
            frag.setArguments(bundle);
            return frag;

        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}
