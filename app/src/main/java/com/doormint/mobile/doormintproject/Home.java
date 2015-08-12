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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openSearchList(View view){

        if (SearchQuery!=null){
            String queryUser = SearchQuery.getText().toString();

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
                    startActivity(i);
                }else {
                    Toast.makeText(this,"No location found",Toast.LENGTH_SHORT).show();
                }
                //APImanager.getInstance().sendAsyncCall("GET","37.78","-122.39",this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this,"Please enter a location first",Toast.LENGTH_SHORT).show();
        }

    }

    private class AsynchClassHandler extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

            } catch (Exception e) {
                //Log.e("AppsmythRestApiManager", "doInBackground : Failed to process "+resource+ " "+requestMethod, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //callback.onSuccessResponse(reply);

        }
    }
    //data.sfgov.org/resource/rqzj-sfat.json
}
