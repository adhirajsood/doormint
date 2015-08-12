package com.doormint.mobile.doormintproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class Home extends AppCompatActivity {
    EditText SearchQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SearchQuery = (EditText) findViewById(R.id.locationSearchBox);


    }


    public void openSearchList(View view){
        String queryUser = SearchQuery.getText().toString().trim();

        if (queryUser!=null &&!queryUser.equals("")){

            Geocoder geocoder = new Geocoder(this);
            List<Address> address;
            try {
                address = geocoder.getFromLocationName(queryUser, 1);
                double latitude = 0,longitude = 0;

                if(address.size() > 0) {
                    latitude = address.get(0).getLatitude();
                    longitude= address.get(0).getLongitude();


                    Intent i = new Intent(this,ListSearchStores.class);
                    i.putExtra("lat",String.valueOf(latitude));
                    i.putExtra("long",String.valueOf(longitude));
                    SearchQuery.setText("");
                    startActivity(i);
                }else {
                    Toast.makeText(this,"Location not found!",Toast.LENGTH_SHORT).show();
                }
                //APImanager.getInstance().sendAsyncCall("GET","37.78","-122.39",this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this,"Please enter a location first!",Toast.LENGTH_SHORT).show();
        }

    }


}
