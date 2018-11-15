package com.example.dell.trackmydevice;

import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationListener;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dell on 11/15/2018.
 */

public class BackgroundLocation extends AsyncTask<String,Void,String> {

    Context context;
    LocationListener locationListener;

    AlertDialog alertDialog;
    HttpURLConnection httpURLConnection;

    BackgroundLocation(LocationListener ltl) {

        this.locationListener = ltl;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];
        String latitude = params[1];
        String longitude = params[2];

        String locationurl="http://192.168.56.1/TrackPHP/addLocations.php";

        if(type.equals("locations")){

            try {

                URL url=new URL(locationurl);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream= httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                //set posting data
                String post_data = URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"+
                        URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8");


                bufferedWriter.write(post_data); //post the data
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1")); //iso-8859-1 expected type

                String result="";
                String line;
                while((line=bufferedReader.readLine())!=null){
                    result = result+ line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        alertDialog.setMessage(result);
        alertDialog.show();

    }
}
