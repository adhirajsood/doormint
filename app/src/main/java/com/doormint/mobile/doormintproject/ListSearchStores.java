package com.doormint.mobile.doormintproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by adhiraj on 9/8/15.
 */
public class ListSearchStores  extends Activity implements IAsyncCallback{
    private GoogleMap googleMap;
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

        APImanager.getInstance().sendAsyncCall("GET", latitude, longitude, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSuccessResponse(String successResponse) {
        System.out.println("response    "+successResponse);
    }

    @Override
    public void onErrorResponse(int errorCode, String errorResponse) {

    }
}
