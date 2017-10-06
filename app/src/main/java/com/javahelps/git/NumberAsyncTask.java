package com.javahelps.git;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sneha on 03-10-2017.
 */

public class NumberAsyncTask extends AsyncTask<String , Void , ArrayList<Integer>> {

    NumberListener numberListener;

    public NumberAsyncTask(NumberListener numberListener) {
        this.numberListener = numberListener;
    }

    @Override
    protected ArrayList<Integer> doInBackground(String... params) {
        ArrayList<Integer> detail = null ;
        for (String urlString : params) {
            try {
                URL url = new URL(urlString);   //convert string to url
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  //to form the connection
                urlConnection.setRequestMethod("GET");// we wont data from server that is why "get"
                Log.i("Course response", "Connection started: ");
                urlConnection.connect();         //cant be called on main thread so we have to use asyncTask() for multithreading
                Log.i("Courses Response:", "Connection Complete");

                InputStream inputStream = urlConnection.getInputStream();      //api gives the input stream
                Scanner scanner = new Scanner(inputStream);
                String response = "";
                while (scanner.hasNext()) {
                    response += scanner.next();
                }
                Log.i("Courses Response:", response);
                detail = parseDetails(response);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return detail;
    }
    ArrayList<Integer> info = new ArrayList<>() ;
    String s = "https://api.github.com/users/snehaMittal/";
    private ArrayList<Integer> parseDetails(String response) throws JSONException{

        JSONArray array = new JSONArray(response);
        info.add(array.length());
        return info;
    }

    @Override
    protected void onPostExecute(ArrayList<Integer> integer) {
        numberListener.onNumberReceiver(integer);
    }

    static interface NumberListener{
        void onNumberReceiver(ArrayList<Integer> answer);
    }
}
