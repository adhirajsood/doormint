package com.doormint.mobile.doormintproject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by adhiraj on 9/8/15.
 */
public class ListSearchStores  extends Activity implements IAsyncCallback{
    private GoogleMap googleMap;
    ArrayList<Marker> mListMarkers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_store);

        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);

        }else{
            googleMap.clear();
        }
        Intent intent = getIntent();
        String latitude = intent.getStringExtra("lat");
        String longitude = intent.getStringExtra("long");
        mListMarkers = new ArrayList<Marker>();
        APImanager.getInstance().sendAsyncCall("GET", latitude, longitude, this);
        addMapMarker(Double.valueOf(latitude), Double.valueOf(longitude), "My Location", "");
        LatLng latLng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }


    public void addMapMarker(double latitude, double longitude, String truckAddress, String truckType){

        if(null != googleMap){

            if (Objects.equals(truckAddress, "My Location")){
                mListMarkers.add(googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(truckAddress)
                        .snippet("")
                        .flat(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmarker))));
            }else {
                mListMarkers.add(googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Address: " + truckAddress)
                        .snippet("Vehicle type: " + truckType)
                        .flat(true)));
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSuccessResponse(String successResponse) {

        try {
            JSONArray jsonArray = new JSONArray(successResponse);
            for (int i =0;i<jsonArray.length();i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String latitudeTruck = jsonObject.getString("latitude");
                String longitudeTruck = jsonObject.getString("longitude");
                String truckAddress = jsonObject.getString("address");
                String truckType = jsonObject.getString("facilitytype");

                if (latitudeTruck!=null && longitudeTruck!=null) {
                    addMapMarker(Double.valueOf(latitudeTruck), Double.valueOf(longitudeTruck), truckAddress, truckType);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onErrorResponse(int errorCode, String errorResponse) {

    }
}
