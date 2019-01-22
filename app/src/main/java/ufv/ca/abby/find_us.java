package ufv.ca.abby;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class find_us extends AppCompatActivity implements OnMapReadyCallback {

        GoogleMap mGoogleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_us);

        if(googleServicesAvailable()){
            Toast.makeText(this,"Tap on Red Icon to get directions on right hand corner of the screen",Toast.LENGTH_LONG).show();
            initMap();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Find Us");


    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps_kes);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(find_us.this,homescreen.class));
        finish();
        this.overridePendingTransition(R.anim.swipe_to_right,
                R.anim.swipe_to_left);
    }

    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable,0);
            dialog.show();
        }else {
            Toast.makeText(this,"Cannot connect to playservices",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        gotoLocationZoom(49.028459,-122.283332, 15);

    }

    private void gotoLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mGoogleMap.moveCamera(update);
        MarkerOptions options = new MarkerOptions();
        options.position(ll).title("Our College!!");
        mGoogleMap.addMarker(options);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
